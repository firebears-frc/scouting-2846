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

/**
 * Browsing activity.
 */
public class BrowseActivity extends AppCompatActivity {

	static private final String TAG = "BrowseActivity";

	/** Scouting loader ID */
	static private final int SCOUTING_LOADER_ID = 51;

	/** Scouting rec */
	private final ScoutingRec rec = ScoutingRec.REC;

	/** Arguments for team info */
	private final Bundle team_args = new Bundle();

	/** Arguments for observations */
	private final ArrayList<Bundle> observations = new ArrayList<Bundle>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
		String team = getTeamKey();
		LoaderManager lm = getSupportLoaderManager();
		TeamLoaderCallbacks team_cb = new TeamLoaderCallbacks(this) {
			protected void onTeamLoaded(Cursor c) {
				BrowseActivity.this.onTeamLoaded(c);
			}
		};
		lm.initLoader(team_cb.LOADER_ID, createArguments(), team_cb);
	}

	private void onTeamLoaded(Cursor c) {
		int num = c.getInt(c.getColumnIndex(Team.COL_TEAM_NUMBER));
		String nick = c.getString(c.getColumnIndex(Team.COL_NICKNAME));
		Log.d(TAG, "team " + num + " " + nick);
		team_args.putInt(Team.COL_TEAM_NUMBER, num);
		team_args.putString(Team.COL_NICKNAME, nick);
		LoaderManager lm = getSupportLoaderManager();
		lm.initLoader(SCOUTING_LOADER_ID, null, cb);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String getTeamKey() {
		return getIntent().getStringExtra(Team.COL_KEY);
	}

	private Bundle createArguments() {
		Bundle b = new Bundle();
		b.putString(Team.COL_KEY, getTeamKey());
		return b;
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
			while (c.moveToNext()) {
				Bundle b = new Bundle(team_args);
				rec.updateBundle(b, c);
				Log.d(TAG, "match " + b.getString(
					rec.COL_MATCH));
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
		return new CursorLoader(this, rec.getContentUri(),
			rec.getCols(), where, null,
			rec.COL_MATCH + ',' + rec._ID);
	}

	private String getWhere() {
		return rec.COL_TEAM_KEY + "='" + getTeamKey() + "'";
	}

	private class ScoutingPagerAdapter extends FragmentStatePagerAdapter {

		private ScoutingPagerAdapter() {
			super(getSupportFragmentManager());
		}

		@Override
		public Fragment getItem(int pos) {
			Fragment f = new BrowseFragment();
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
			String m = b.getString(rec.COL_MATCH);
			if ("".equals(m))
				return getString(R.string.pit);
			else
				return m;
		}
	}
}
