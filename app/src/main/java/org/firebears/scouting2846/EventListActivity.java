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
		FRCEvent.COL_ID,
		FRCEvent.COL_KEY,
		FRCEvent.COL_SHORT,
		FRCEvent.COL_START_DATE,
	};

	/** Sort for loader */
	static private final String SORT =
		FRCEvent.COL_START_DATE + "," + FRCEvent.COL_NAME;

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
		int[] cols = new int[] { R.id.event_week, R.id.event_name };
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
				startDetailActivity(c);
			}
		});
		getLoaderManager().initLoader(EVENT_LOADER_ID, null, cb);
		startService(new Intent(this, BluetoothSyncService.class));
	}

	public void restartLoader() {
		getLoaderManager().restartLoader(EVENT_LOADER_ID, null, cb);
	}

	/** Start event detail activity */
	private void startDetailActivity(Cursor c) {
		int _id = c.getInt(c.getColumnIndex(FRCEvent.COL_ID));
		String key = c.getString(c.getColumnIndex(FRCEvent.COL_KEY));
		String short_name = c.getString(c.getColumnIndex(
			FRCEvent.COL_SHORT));
		Intent intent = new Intent(this, EventDetailActivity.class);
		intent.putExtra(EventDetailFragment.ARG_EVENT_ID, _id);
		intent.putExtra(EventDetailFragment.ARG_EVENT_KEY, key);
		intent.putExtra(EventDetailFragment.ARG_EVENT_SHORT,
			short_name);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.root_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (R.id.action_refresh == item.getItemId())
			return onRefreshSelected();
		else if (R.id.action_bt_sync == item.getItemId())
			return onBluetoothSyncSelected();
		else
			return super.onOptionsItemSelected(item);
	}

	private boolean onRefreshSelected() {
		showSnack(R.string.fetch_events);
		new FetchEvents(this).execute();
		return true;
	}

	private boolean onBluetoothSyncSelected() {
		Intent intent = new Intent(this, SelectDeviceActivity.class);
		startActivityForResult(intent, 1);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
		Intent data)
	{
		switch (resultCode) {
		case RESULT_OK:
			String address = data.getStringExtra(
				SelectDeviceActivity.DEVICE_ADDRESS);
			new BluetoothSyncTask(this, address).execute();
			break;
		case RESULT_CANCELED:
			int res = data.getIntExtra(
				SelectDeviceActivity.ERROR_CODE, 0);
			showSnack(res);
			break;
		}
	}

	/** Show a snackbar */
	public void showSnack(int res) {
		View v = findViewById(R.id.event_list);
		Snackbar.make(v, res, Snackbar.LENGTH_LONG).show();
	}
}
