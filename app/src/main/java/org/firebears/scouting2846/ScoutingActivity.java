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

	/** Content values */
	private final ContentValues content = new ContentValues();

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
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
		addLoaderCallbacks(new ParamLoaderHelper(this) {
			protected void onLoaded(Cursor c) {
				onParamLoaded(c);
			}
		});
		addLoaderCallbacks(new TeamLoaderHelper(this) {
			protected void onLoaded(Cursor c) {
				onTeamLoaded(c);
			}
		});
	}

	private void addLoaderCallbacks(LoaderHelper helper) {
		LoaderManager lm = getSupportLoaderManager();
		lm.initLoader(helper.getId(), createArguments(), helper);
	}

	private void onParamLoaded(Cursor c) {
		int ni = c.getColumnIndex(Param.COL_NAME);
		int vi = c.getColumnIndex(Param.COL_VALUE);
		while (c.moveToNext()) {
			String n = c.getString(ni);
			int v = c.getInt(vi);
			Log.d(TAG, n + ": " + v);
			if (Param.ROW_SCOUTER.equals(n))
				content.put(REC.COL_SCOUTER, v);
			if (Param.ROW_OBSERVATION.equals(n))
				content.put(REC.COL_OBSERVATION, v + 1);
		}
		addLoaderCallbacks(new ScoutingLoaderHelper(this) {
			protected void onLoaded(Cursor c) {
				onScoutingLoaded(c);
			}
		});
	}

	private void onScoutingLoaded(Cursor c) {
		REC.updateContent(content, c);
		for (ScoutingData sd : REC.getAllData())
			sd.init(content, this);
	}

	private void onTeamLoaded(Cursor c) {
		int num = c.getInt(c.getColumnIndex(Team.COL_TEAM_NUMBER));
		String nick = c.getString(c.getColumnIndex(Team.COL_NICKNAME));
		TextView tv = (TextView) findViewById(R.id.team_lbl);
		tv.setText("" + num + ' ' + nick);
	}

	private Bundle createArguments() {
		Bundle b = new Bundle();
		b.putInt(REC.COL_SCOUTER, getScouter());
		b.putString(Match.COL_KEY, getMatchKey());
		b.putString(Team.COL_KEY, getTeamKey());
		return b;
	}

	private Integer getScouter() {
		return content.getAsInteger(REC.COL_SCOUTER);
	}

	private String getMatchKey() {
		return getIntent().getStringExtra(Match.COL_KEY);
	}

	private String getTeamKey() {
		return getIntent().getStringExtra(Team.COL_KEY);
	}

	@Override
	public void onPause() {
		if (REC.updateContent(content, this))
			new StoreObservation(this, content).execute();
		super.onPause();
	}
}
