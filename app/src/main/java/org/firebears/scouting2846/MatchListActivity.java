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

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * An activity representing a list of FRC matches.
 */
public class MatchListActivity extends AppCompatActivity {

	/** Argument for event id */
	static public final String ARG_EVENT_ID = "event_id";
	static public final String ARG_EVENT_KEY = "event_key";
	static public final String ARG_EVENT_SHORT = "event_short";

	/** Loader ID */
	static private final int MATCH_LOADER_ID = 42;

	/** Columns to retrieve from the loader */
	static private final String[] COLS = {
		Match.COL_COMP_LEVEL,
		Match.COL_SET_NUMBER,
		Match.COL_MATCH_NUMBER,
		Match.COL_ID,
	};

	/** Sort for loader */
	static private final String SORT =
		Match.COL_COMP_LEVEL + "," + Match.COL_SET_NUMBER + "," +
		Match.COL_MATCH_NUMBER;

	/** Cursor adapter */
	private SimpleCursorAdapter adapter;

	/** Callbacks for loader */
	private final LoaderCallbacks<Cursor> cb =
		new LoaderCallbacks<Cursor>()
	{
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle b) {
			return (MATCH_LOADER_ID == id)
			      ? createLoader(b)
			      :	null;
		}
		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			adapter.swapCursor(data);
		}
		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			adapter.swapCursor(null);
		}
	};

	/** Create a loader for matches */
	private Loader<Cursor> createLoader(Bundle b) {
		return new CursorLoader(MatchListActivity.this,
			Match.CONTENT_URI, COLS, getSelectionClause(),
			null, SORT);
	}

	private String getSelectionClause() {
		int event_id = getEventId();
		return (event_id > 0) ? ("event_id=" + event_id) : null;
	}

	private int getEventId() {
		return getIntent().getIntExtra(ARG_EVENT_ID, 0);
	}

	private String getEventKey() {
		return getIntent().getStringExtra(ARG_EVENT_KEY);
	}

	private String getEventShortName() {
		return getIntent().getStringExtra(ARG_EVENT_SHORT);
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.match_list_activity);
		Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(bar);
		if (bar != null) {
			bar.setTitle(getEventShortName() + ' ' + getText(
				R.string.title_match_list));
		}
		ActionBar ab = getSupportActionBar();
		if (ab != null)
			ab.setDisplayHomeAsUpEnabled(true);
		int[] cols = new int[] { R.id.match_desc };
		adapter = new SimpleCursorAdapter(this,
			R.layout.match_list_entry, null, COLS, cols, 0);
		adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
			public boolean setViewValue(View v, Cursor c, int i) {
				TextView tv = (TextView) v;
				tv.setText(Match.description(c));
				return true;
			}
		});
		ListView lv = (ListView) findViewById(R.id.match_list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent,
				View view, int position, long id)
			{
				Cursor c = (Cursor) parent.getAdapter()
					.getItem(position);
				startDetailActivity(c.getInt(c.getColumnIndex(
					"_id")));
			}
		});
		getLoaderManager().initLoader(MATCH_LOADER_ID, null, cb);
	}

	public void restartLoader() {
		getLoaderManager().restartLoader(MATCH_LOADER_ID, null, cb);
	}

	/** Start match detail activity */
	private void startDetailActivity(int _id) {
		Intent intent = new Intent(this, MatchDetailActivity.class);
		intent.putExtra(MatchDetailFragment.ARG_MATCH_ID, _id);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			onBackPressed();
			return true;
		} else if (R.id.action_refresh == item.getItemId())
			return onRefreshSelected();
		else
			return super.onOptionsItemSelected(item);
	}

	private boolean onRefreshSelected() {
		View v = findViewById(R.id.match_list);
		Snackbar.make(v, R.string.fetch_matches, Snackbar.LENGTH_LONG)
		        .show();
		new FetchEventMatches(this, getEventId(),
			getEventKey()).execute();
		return true;
	}
}
