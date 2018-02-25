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
package org.firebears.scouting2846.y2017;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import java.util.ArrayList;
import org.firebears.scouting2846.R;
import org.firebears.scouting2846.Team;

/**
 * Browsing activity for 2017 (Steamworks).
 */
public class Browse2017Activity extends AppCompatActivity {

	static private final String TAG = "Browse2017Activity";

	/** Activity Arguments */
	static public final String ARG_TEAM_KEY = "team_key";

	/** Scouting loader ID */
	static private final int TEAM_LOADER_ID = 50;
	static private final int SCOUTING_2017_LOADER_ID = 51;

	/** Columns to retrieve from the loader */
	static private final String[] TEAM_COLS = {
		Team.COL_TEAM_NUMBER,
		Team.COL_NICKNAME,
	};

	/** Columns to retrieve from the loader */
	static private final String[] COLS = {
		Scouting2017._ID,
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

	/** Arguments for team info */
	private final Bundle team_args = new Bundle();

	/** Arguments for observations */
	private final ArrayList<Bundle> observations = new ArrayList<Bundle>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_2017);
		LoaderManager lm = getSupportLoaderManager();
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
			if (c.getCount() == 1)
				onTeamLoaded(c);
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

	private void onTeamLoaded(Cursor c) {
		c.moveToFirst();
		int num = c.getInt(c.getColumnIndex(Team.COL_TEAM_NUMBER));
		String nick = c.getString(c.getColumnIndex(Team.COL_NICKNAME));
		Log.d(TAG, "team " + num + " " + nick);
		team_args.putInt(Team.COL_TEAM_NUMBER, num);
		team_args.putString(Team.COL_NICKNAME, nick);
		LoaderManager lm = getSupportLoaderManager();
		lm.initLoader(SCOUTING_2017_LOADER_ID, null, cb);
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
			while (c.moveToNext()) {
				Bundle b = new Bundle(team_args);
				Scouting2017.updateBundle(b, c);
				Log.d(TAG, "match " + b.getString(
					Scouting2017.COL_MATCH));
				observations.add(b);
			}
			ViewPager vp = (ViewPager) findViewById(R.id.pager);
			vp.setAdapter(new ScoutingPagerAdapter());
		}
		@Override
		public void onLoaderReset(Loader<Cursor> loader) { }
	};

	/** Create a loader for scouting details */
	private Loader<Cursor> createLoader() {
		String where = getWhere();
		return new CursorLoader(this, Scouting2017.CONTENT_URI,
			COLS, where, null, Scouting2017.COL_MATCH + ',' +
			Scouting2017._ID);
	}

	private String getWhere() {
		return Scouting2017.COL_TEAM_KEY + "='" + getTeamKey() + "'";
	}

	private class ScoutingPagerAdapter extends FragmentStatePagerAdapter {

		private ScoutingPagerAdapter() {
			super(getSupportFragmentManager());
		}

		@Override
		public Fragment getItem(int pos) {
			Fragment f = new Browse2017Fragment();
			f.setArguments(observations.get(pos));
			return f;
		}

		@Override
		public int getCount() {
			return observations.size();
		}

		@Override
		public CharSequence getPageTitle(int pos) {
			Bundle b = observations.get(pos);
			String m = b.getString(Scouting2017.COL_MATCH);
			if ("".equals(m))
				return getString(R.string.pit);
			else
				return m;
		}
	}
}
