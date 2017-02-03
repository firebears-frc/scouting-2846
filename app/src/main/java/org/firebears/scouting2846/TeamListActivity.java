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

/**
 * An activity representing a list of FRC teams.
 */
public class TeamListActivity extends AppCompatActivity {

	/** Loader ID */
	static private final int TEAM_LOADER_ID = 39;

	/** Columns to retrieve from the loader */
	static private final String[] COLS = {
		Team.COL_TEAM_NUMBER,
		Team.COL_NICKNAME,
		"_id",
	};

	/** Cursor adapter */
	private SimpleCursorAdapter adapter;

	/** Callbacks for loader */
	private final LoaderCallbacks<Cursor> cb =
		new LoaderCallbacks<Cursor>()
	{
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			if (TEAM_LOADER_ID == id) {
				return new CursorLoader(TeamListActivity.this,
					Team.CONTENT_URI, COLS, null, null,
					Team.COL_TEAM_NUMBER);
			} else
				return null;
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

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.team_list_activity);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		ActionBar ab = getSupportActionBar();
		if (ab != null)
			ab.setDisplayHomeAsUpEnabled(true);
		int[] cols = new int[] { R.id.team_id, R.id.team_content };
		adapter = new SimpleCursorAdapter(this,
			R.layout.team_list_entry, null, COLS, cols, 0);
		ListView lv = (ListView) findViewById(R.id.team_list);
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
		getLoaderManager().initLoader(TEAM_LOADER_ID, null, cb);
	}

	public void restartLoader() {
		getLoaderManager().restartLoader(TEAM_LOADER_ID, null, cb);
	}

	/** Start team detail activity */
	private void startDetailActivity(int _id) {
/*		Intent intent = new Intent(this, EventDetailActivity.class);
		intent.putExtra(EventDetailFragment.ARG_EVENT_ID, _id);
		startActivity(intent);*/
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
/*		View v = findViewById(R.id.team_list);
		Snackbar.make(v, R.string.fetch_teams, Snackbar.LENGTH_LONG)
		        .show();
		new FetchTeams(this).execute();*/
		return true;
	}
}
