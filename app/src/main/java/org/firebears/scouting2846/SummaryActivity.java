/*
 * Copyright  2018  Douglas P Lau
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
import java.util.HashMap;
import static org.firebears.scouting2846.ScoutingRec.REC;

/**
 * Summary activity.
 */
public class SummaryActivity extends AppCompatActivity {

	static private final String TAG = "SummaryActivity";

	/** Observation loader ID */
	static private final int OBS_LOADER_ID = 52;

	/** Arguments for observations */
	private final HashMap<String, ArrayList<Bundle>> observations =
		new HashMap<String, ArrayList<Bundle>>();

	/** Summarized observation data */
	private final ArrayList<Bundle> summary = new ArrayList<Bundle>();

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
		LoaderManager lm = getSupportLoaderManager();
		lm.initLoader(OBS_LOADER_ID, null, cb);
	}

	private int getEventId() {
		return getIntent().getIntExtra(FRCEvent.COL_EVENT_ID, 0);
	}

	private String getEventKey() {
		return getIntent().getStringExtra(FRCEvent.COL_EVENT_KEY);
	}

	/** Callbacks for loader */
	private final LoaderCallbacks<Cursor> cb =
		new LoaderCallbacks<Cursor>()
	{
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle b) {
			return (OBS_LOADER_ID == id)
			      ? createLoader()
			      : null;
		}
		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
			while (c.moveToNext()) {
				Bundle b = new Bundle();
				REC.updateBundle(b, c);
				putObservation(b);
			}
			buildSummary();
			ViewPager vp = (ViewPager) findViewById(R.id.pager);
			vp.setAdapter(new SummaryPagerAdapter());
		}
		@Override
		public void onLoaderReset(Loader<Cursor> loader) { }
	};

	private void putObservation(Bundle b) {
		String team_key = b.getString(REC.COL_TEAM_KEY);
		ArrayList<Bundle> al = observations.get(team_key);
		if (null == al) {
			al = new ArrayList<Bundle>();
			observations.put(team_key, al);
		}
		al.add(b);
		Log.d(TAG, "team " + team_key + " count: " + al.size());
	}

	private void buildSummary() {
		for (ArrayList<Bundle> v : observations.values()) {
			Log.d(TAG, "summary " + v.size());
			Bundle b = REC.summarize(v);
			summary.add(b);
		}
	}

	/** Create a loader for observation details */
	private Loader<Cursor> createLoader() {
		return new CursorLoader(this, REC.getContentUri(),
			REC.getCols(), null, null, null);
	}

	private class SummaryPagerAdapter extends FragmentStatePagerAdapter {

		private SummaryPagerAdapter() {
			super(getSupportFragmentManager());
		}

		@Override
		public Fragment getItem(int pos) {
			Fragment f = new SummaryFragment();
			f.setArguments(summary.get(pos));
			return f;
		}

		@Override
		public int getCount() {
			return summary.size();
		}

		@Override
		public CharSequence getPageTitle(int pos) {
			Bundle b = summary.get(pos);
			return b.getString(REC.COL_TEAM_KEY);
		}
	}
}
