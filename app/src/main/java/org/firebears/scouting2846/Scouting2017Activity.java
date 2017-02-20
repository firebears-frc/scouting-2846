/*
 * Copyright  2017  Douglas P Lau
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
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Scouting activity for 2017 (Steamworks).
 */
public class Scouting2017Activity extends AppCompatActivity {

	/** Activity Arguments */
	static public final String ARG_MATCH_KEY = "match_key";
	static public final String ARG_TEAM_KEY = "team_key";

	/** Scouting loader ID */
	static private final int SCOUTING_2017_LOADER_ID = 44;
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

	/** Columns to retrieve from the loader */
	static private final String[] COLS = {
		Scouting2017.COL_ID,
		Scouting2017.COL_MATCH,
		Scouting2017.COL_TEAM_KEY,
		Scouting2017.COL_AUTO_HIGH_GOAL,
		Scouting2017.COL_AUTO_LOW_GOAL,
		Scouting2017.COL_AUTO_GEAR,
		Scouting2017.COL_AUTO_BASELINE,
		Scouting2017.COL_HIGH_GOAL,
		Scouting2017.COL_LOW_GOAL,
		Scouting2017.COL_PLACE_GEAR,
		Scouting2017.COL_CLIMB_ROPE,
		Scouting2017.COL_TOUCH_PAD,
		Scouting2017.COL_BALL_HUMAN,
		Scouting2017.COL_BALL_FLOOR,
		Scouting2017.COL_BALL_HOPPER,
		Scouting2017.COL_PILOT_EFFECTIVE,
		Scouting2017.COL_RELEASE_ROPE,
		Scouting2017.COL_LOSE_GEAR,
		Scouting2017.COL_NOTES,
	};

	/** Content values */
	private final ContentValues content = new ContentValues();

	/** Loaded content values */
	private ContentValues content_loaded;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Scouting2017.initContent(content, getTeamKey(), getMatchKey());
		setContentView(R.layout.activity_scouting_2017);
		setSupportActionBar((Toolbar)
			findViewById(R.id.detail_toolbar));
		ActionBar ab = getSupportActionBar();
		if (ab != null) {
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setTitle(R.string.scouting_2017);
		}
		LoaderManager lm = getSupportLoaderManager();
		lm.initLoader(PARAM_LOADER_ID, null, param_cb);
		lm.initLoader(TEAM_LOADER_ID, null, team_cb);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
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
				Log.e("onLoadFinished", n + ": " + v);
				if (Param.ROW_SCOUTER.equals(n)) {
					content.put(Scouting2017.COL_SCOUTER,
						v);
				}
				if (Param.ROW_OBSERVATION.equals(n)) {
					content.put(Scouting2017.COL_OBSERVATION,
						v + 1);
				}
			}
			LoaderManager lm = getSupportLoaderManager();
			lm.initLoader(SCOUTING_2017_LOADER_ID, null, cb);
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
			return (SCOUTING_2017_LOADER_ID == id)
			      ? createLoader()
			      : null;
		}
		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
			if (c.getCount() == 1) {
				c.moveToFirst();
				Scouting2017.updateContent(content, c);
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
		return new CursorLoader(this, Scouting2017.CONTENT_URI,
			COLS, where, null, null);
	}

	private String getWhere() {
		return Scouting2017.COL_SCOUTER + "=" + getScouter() + " AND "+
		       Scouting2017.COL_MATCH + "='" + getMatchKey() +"' AND "+
		       Scouting2017.COL_TEAM_KEY + "='" + getTeamKey() + "'";
	}

	private int getScouter() {
		Integer i = content.getAsInteger(Scouting2017.COL_SCOUTER);
		return (i != null) ? i : 0;
	}

	private void initView() {
		initString(R.id.ah_txt, Scouting2017.COL_AUTO_HIGH_GOAL);
		initString(R.id.al_txt, Scouting2017.COL_AUTO_LOW_GOAL);
		initSwitch(R.id.auto_gear, Scouting2017.COL_AUTO_GEAR);
		initSwitch(R.id.baseline, Scouting2017.COL_AUTO_BASELINE);
		initString(R.id.th_txt, Scouting2017.COL_HIGH_GOAL);
		initString(R.id.tl_txt, Scouting2017.COL_LOW_GOAL);
		initString(R.id.tg_txt, Scouting2017.COL_PLACE_GEAR);
		initSwitch(R.id.climb, Scouting2017.COL_CLIMB_ROPE);
		initSwitch(R.id.touchpad, Scouting2017.COL_TOUCH_PAD);
		initSwitch(R.id.ball_human, Scouting2017.COL_BALL_HUMAN);
		initSwitch(R.id.ball_floor, Scouting2017.COL_BALL_FLOOR);
		initSwitch(R.id.ball_hopper, Scouting2017.COL_BALL_HOPPER);
		initSwitch(R.id.effective, Scouting2017.COL_PILOT_EFFECTIVE);
		initSwitch(R.id.release_rope, Scouting2017.COL_RELEASE_ROPE);
		initSwitch(R.id.lose_gear, Scouting2017.COL_LOSE_GEAR);
		initText(R.id.notes, Scouting2017.COL_NOTES);
	}

	private void initString(int res, String col) {
		Integer i = content.getAsInteger(col);
		TextView tv = (TextView) findViewById(res);
		tv.setText((i != null) ? Integer.toString(i) : "");
	}

	private void initSwitch(int res, String col) {
		Integer i = content.getAsInteger(col);
		SwitchCompat sw = (SwitchCompat) findViewById(res);
		sw.setChecked((i != null) && (i != 0));
	}

	private void initText(int res, String col) {
		String t = content.getAsString(col);
		EditText et = (EditText) findViewById(res);
		et.setText((t != null) ? t : "");
	}

	private String getMatchKey() {
		return getIntent().getStringExtra(ARG_MATCH_KEY);
	}

	private String addInt(String col, int p) {
		if (content.containsKey(col)) {
			int v = content.getAsInteger(col) + p;
			if (v < 0)
				v = 0;
			content.put(col, v);
			return Integer.toString(v);
		} else
			return "";
	}

	public void minusAutoHigh(View v) {
		TextView tv = (TextView) findViewById(R.id.ah_txt);
		tv.setText(addInt(Scouting2017.COL_AUTO_HIGH_GOAL, -1));
	}

	public void plusAutoHigh(View v) {
		TextView tv = (TextView) findViewById(R.id.ah_txt);
		tv.setText(addInt(Scouting2017.COL_AUTO_HIGH_GOAL, 1));
	}

	public void minusAutoLow(View v) {
		TextView tv = (TextView) findViewById(R.id.al_txt);
		tv.setText(addInt(Scouting2017.COL_AUTO_LOW_GOAL, -1));
	}

	public void plusAutoLow(View v) {
		TextView tv = (TextView) findViewById(R.id.al_txt);
		tv.setText(addInt(Scouting2017.COL_AUTO_LOW_GOAL, 1));
	}

	public void minusTeleHigh(View v) {
		TextView tv = (TextView) findViewById(R.id.th_txt);
		tv.setText(addInt(Scouting2017.COL_HIGH_GOAL, -1));
	}

	public void plusTeleHigh(View v) {
		TextView tv = (TextView) findViewById(R.id.th_txt);
		tv.setText(addInt(Scouting2017.COL_HIGH_GOAL, 1));
	}

	public void minusTeleLow(View v) {
		TextView tv = (TextView) findViewById(R.id.tl_txt);
		tv.setText(addInt(Scouting2017.COL_LOW_GOAL, -1));
	}

	public void plusTeleLow(View v) {
		TextView tv = (TextView) findViewById(R.id.tl_txt);
		tv.setText(addInt(Scouting2017.COL_LOW_GOAL, 1));
	}

	public void minusTeleGear(View v) {
		TextView tv = (TextView) findViewById(R.id.tg_txt);
		tv.setText(addInt(Scouting2017.COL_PLACE_GEAR, -1));
	}

	public void plusTeleGear(View v) {
		TextView tv = (TextView) findViewById(R.id.tg_txt);
		tv.setText(addInt(Scouting2017.COL_PLACE_GEAR, 1));
	}

	@Override
	public void onPause() {
		updateBoolean(R.id.auto_gear, Scouting2017.COL_AUTO_GEAR);
		updateBoolean(R.id.baseline, Scouting2017.COL_AUTO_BASELINE);
		updateBoolean(R.id.climb, Scouting2017.COL_CLIMB_ROPE);
		updateBoolean(R.id.touchpad, Scouting2017.COL_TOUCH_PAD);
		updateBoolean(R.id.ball_human, Scouting2017.COL_BALL_HUMAN);
		updateBoolean(R.id.ball_floor, Scouting2017.COL_BALL_FLOOR);
		updateBoolean(R.id.ball_hopper, Scouting2017.COL_BALL_HOPPER);
		updateBoolean(R.id.effective,Scouting2017.COL_PILOT_EFFECTIVE);
		updateBoolean(R.id.release_rope,Scouting2017.COL_RELEASE_ROPE);
		updateBoolean(R.id.lose_gear, Scouting2017.COL_LOSE_GEAR);
		updateString(R.id.notes, Scouting2017.COL_NOTES);
		if (!content.equals(content_loaded))
			new StoreObservation(this, content).execute();
		super.onPause();
	}

	private void updateBoolean(int res, String col) {
		SwitchCompat sw = (SwitchCompat) findViewById(res);
		boolean c = sw.isChecked();
		content.put(col, (c) ? 1 : 0);
	}

	private void updateString(int res, String col) {
		EditText et = (EditText) findViewById(res);
		content.put(col, et.getText().toString());
	}
}
