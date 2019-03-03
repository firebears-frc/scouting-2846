/*
 * Copyright  2017-2019  Douglas P Lau
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
import static org.firebears.scouting2846.ScoutingRec.REC;

/**
 * Browsing activity.
 */
public class BrowseActivity extends AppCompatActivity {

	static private final String TAG = "BrowseActivity";

	/** Scouting loader ID */
	static private final int SCOUTING_LOADER_ID = 51;

	/** Default arguments */
	private final Bundle def_args = new Bundle();

	/** Arguments for observations */
	private final ArrayList<Bundle> observations = new ArrayList<Bundle>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
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
				def_args.putInt(BrowseFragment.THIS_SCOUTER,v);
		}
	}

	private void onTeamLoaded(Cursor c) {
		int num = c.getInt(c.getColumnIndex(Team.COL_TEAM_NUMBER));
		String nick = c.getString(c.getColumnIndex(Team.COL_NICKNAME));
		Log.d(TAG, "team " + num + " " + nick);
		def_args.putInt(Team.COL_TEAM_NUMBER, num);
		def_args.putString(Team.COL_NICKNAME, nick);
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

	private int getTeamNumber() {
		return getIntent().getIntExtra(Team.COL_TEAM_NUMBER, 0);
	}

	private Bundle createArguments() {
		Bundle b = new Bundle();
		b.putInt(Team.COL_TEAM_NUMBER, getTeamNumber());
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
				Bundle b = new Bundle(def_args);
				REC.updateBundle(b, c);
				Log.d(TAG, "match " + b.getString(
					REC.COL_MATCH_KEY));
				observations.add(b);
			}
			// FIXME: if no observations, use blank fragment
			ViewPager vp = (ViewPager) findViewById(R.id.pager);
			vp.setAdapter(new ScoutingPagerAdapter());
		}
		@Override
		public void onLoaderReset(Loader<Cursor> loader) { }
	};

	/** Create a loader for scouting details */
	private Loader<Cursor> createLoader() {
		String where = getWhere();
		return new CursorLoader(this, REC.getContentUri(),
			REC.getCols(), where, null,
			REC.COL_MATCH_KEY + ',' + REC._ID);
	}

	private String getWhere() {
		return REC.COL_TEAM_NUMBER + "=" + getTeamNumber();
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
			String m = b.getString(REC.COL_MATCH_KEY);
			if ("".equals(m))
				return getString(R.string.pit);
			else
				return m;
		}
	}
}
