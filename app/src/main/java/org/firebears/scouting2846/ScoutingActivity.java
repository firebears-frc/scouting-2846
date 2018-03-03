/*
 * Copyright  2017-2018  Douglas P Lau
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package org.firebears.scouting2846;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import static org.firebears.scouting2846.ScoutingRec.REC;

/**
 * Scouting activity.
 */
public class ScoutingActivity extends AppCompatActivity {

	static private final String TAG = "ScoutingActivity";

	/** Activity Arguments */
	static public final String ARG_MATCH_KEY = "match_key";
	static public final String ARG_TEAM_KEY = "team_key";

	/** Scouting loader ID */
	static private final int SCOUTING_LOADER_ID = 44;
	static private final int TEAM_LOADER_ID = 45;
	static private final int PARAM_LOADER_ID = 46;

	/** Columns to retrieve from the loader */
	static private final String[] PARAM_COLS = {
		Param.COL_NAME,
		Param.COL_VALUE,
	};

	/** Columns to retrieve from the loader */
	static private final String[] TEAM_COLS = {
		Team.COL_TEAM_NUMBER,
		Team.COL_NICKNAME,
	};

	/** Content values */
	private final ContentValues content = new ContentValues();

	/** Loaded content values */
	private ContentValues content_loaded;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void init(ScoutingData sd) {
		sd.init(content, this);
	}

	protected void update(ScoutingData sd) {
		sd.update(content, this);
	}

	public Uri getContentUri() {
		return REC.getContentUri();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		REC.initContent(content, getTeamKey(), getMatchKey());
		setContentView(REC.scouting_activity_res);
		setSupportActionBar((Toolbar)
			findViewById(R.id.detail_toolbar));
		ActionBar ab = getSupportActionBar();
		if (ab != null) {
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setTitle(REC.title_res);
		}
		LoaderManager lm = getSupportLoaderManager();
		lm.initLoader(PARAM_LOADER_ID, null, param_cb);
		lm.initLoader(TEAM_LOADER_ID, null, team_cb);
	}

	/** Callbacks for param loader */
	private final LoaderCallbacks<Cursor> param_cb =
		new LoaderCallbacks<Cursor>()
	{
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle b) {
			return (PARAM_LOADER_ID == id)
			      ? createParamLoader()
			      : null;
		}
		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
			int ni = c.getColumnIndex(Param.COL_NAME);
			int vi = c.getColumnIndex(Param.COL_VALUE);
			while (c.moveToNext()) {
				String n = c.getString(ni);
				int v = c.getInt(vi);
				Log.d(TAG, n + ": " + v);
				if (Param.ROW_SCOUTER.equals(n))
					content.put(REC.COL_SCOUTER, v);
				if (Param.ROW_OBSERVATION.equals(n))
					content.put(REC.COL_OBSERVATION, v+1);
			}
			LoaderManager lm = getSupportLoaderManager();
			lm.initLoader(SCOUTING_LOADER_ID, null, cb);
		}
		@Override
		public void onLoaderReset(Loader<Cursor> loader) { }
	};

	/** Create a loader for parameters */
	private Loader<Cursor> createParamLoader() {
		return new CursorLoader(this, Param.CONTENT_URI, PARAM_COLS,
			null, null, null);
	}

	/** Callbacks for team loader */
	private final LoaderCallbacks<Cursor> team_cb =
		new LoaderCallbacks<Cursor>()
	{
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle b) {
			return (TEAM_LOADER_ID == id)
			      ? createTeamLoader()
			      : null;
		}
		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
			if (c.getCount() != 1)
				return;
			c.moveToFirst();
			TextView tv = (TextView) findViewById(R.id.team_lbl);
			int num = c.getInt(c.getColumnIndex(
				Team.COL_TEAM_NUMBER));
			String nick = c.getString(c.getColumnIndex(
				Team.COL_NICKNAME));
			tv.setText("" + num + ' ' + nick);
		}
		@Override
		public void onLoaderReset(Loader<Cursor> loader) { }
	};

	/** Create a loader for team name */
	private Loader<Cursor> createTeamLoader() {
		String key = getTeamKey();
		return new CursorLoader(this, Team.CONTENT_URI,
			TEAM_COLS, Team.COL_KEY + "='" + key + "'", null,null);
	}

	private String getTeamKey() {
		return getIntent().getStringExtra(ARG_TEAM_KEY);
	}

	/** Callbacks for loader */
	private final LoaderCallbacks<Cursor> cb =
		new LoaderCallbacks<Cursor>()
	{
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle b) {
			return (SCOUTING_LOADER_ID == id)
			      ? createLoader()
			      : null;
		}
		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
			if (c.getCount() == 1) {
				c.moveToFirst();
				REC.updateContent(content, c);
				content_loaded = new ContentValues(content);
			}
			initView();
		}
		@Override
		public void onLoaderReset(Loader<Cursor> loader) { }
	};

	/** Create a loader for scouting details */
	private Loader<Cursor> createLoader() {
		String where = getWhere();
		return new CursorLoader(this, REC.getContentUri(),
			REC.getCols(), where, null, null);
	}

	private String getWhere() {
		return REC.COL_SCOUTER + "=" + getScouter() + " AND "+
		       REC.COL_MATCH + "='" + getMatchKey() +"' AND "+
		       REC.COL_TEAM_KEY + "='" + getTeamKey() + "'";
	}

	private int getScouter() {
		Integer i = content.getAsInteger(REC.COL_SCOUTER);
		return (i != null) ? i : 0;
	}

	private void initView() {
		for (ScoutingData sd : REC.getAllData())
			init(sd);
	}

	private String getMatchKey() {
		return getIntent().getStringExtra(ARG_MATCH_KEY);
	}

	@Override
	public void onPause() {
		for (ScoutingData sd : REC.getAllData())
			update(sd);
		content.remove(REC._ID);
		if (!content.equals(content_loaded))
			new StoreObservation(this, content).execute();
		super.onPause();
	}
}
