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
 * An activity representing a list of FRC events.
 */
public class EventListActivity extends AppCompatActivity {

	/** Loader ID */
	static private final int EVENT_LOADER_ID = 37;

	/** Columns to retrieve from the loader */
	static private final String[] COLS = {
		FRCEvent.COL_WEEK,
		FRCEvent.COL_NAME,
		"_id",
	};

	/** Sort for loader */
	static private final String SORT =
		FRCEvent.COL_WEEK + "," + FRCEvent.COL_NAME;

	/** Cursor adapter */
	private SimpleCursorAdapter adapter;

	/** Callbacks for loader */
	private final LoaderCallbacks<Cursor> cb =
		new LoaderCallbacks<Cursor>()
	{
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			if (EVENT_LOADER_ID == id) {
				return new CursorLoader(EventListActivity.this,
					FRCEvent.CONTENT_URI, COLS, null, null,
					SORT);
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
		setContentView(R.layout.event_list_activity);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		int[] cols = new int[] { R.id.event_id, R.id.event_content };
		adapter = new SimpleCursorAdapter(this,
			R.layout.event_list_entry, null, COLS, cols, 0);
		ListView lv = (ListView) findViewById(R.id.event_list);
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
		getLoaderManager().initLoader(EVENT_LOADER_ID, null, cb);
	}

	public void restartLoader() {
		getLoaderManager().restartLoader(EVENT_LOADER_ID, null, cb);
	}

	/** Start event detail activity */
	private void startDetailActivity(int _id) {
		Intent intent = new Intent(this, EventDetailActivity.class);
		intent.putExtra(EventDetailFragment.ARG_EVENT_ID, _id);
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
		if (R.id.action_refresh == item.getItemId()) {
			new FetchEvents(this).execute();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
