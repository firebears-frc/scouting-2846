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

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Event detail screen.
 */
public class EventDetailFragment extends Fragment {

	/** Argument for event id */
	static public final String ARG_EVENT_ID = "event_id";
	static public final String ARG_EVENT_KEY = "event_key";

	/** Event detail loader ID */
	static private final int EVENT_DETAIL_LOADER_ID = 38;

	/** Columns to retrieve from the loader */
	static private final String[] COLS = {
		FRCEvent.COL_SHORT,
		FRCEvent.COL_NAME,
		FRCEvent.COL_LOCATION,
		FRCEvent.COL_VENUE_ADDRESS,
		FRCEvent.COL_WEBSITE,
	};

	/** Required constructor */
	public EventDetailFragment() { }

	/** Root view */
	private View root_view;

	/** Callbacks for loader */
	private final LoaderCallbacks<Cursor> cb =
		new LoaderCallbacks<Cursor>()
	{
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle b) {
			return (EVENT_DETAIL_LOADER_ID == id)
			      ? createLoader(b)
			      : null;
		}
		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
			if (null == root_view || c.getCount() != 1)
				return;
			c.moveToFirst();
			Toolbar bar = (Toolbar) getActivity().findViewById(
				R.id.detail_toolbar);
			if (bar != null) {
				bar.setTitle(c.getString(
					c.getColumnIndex(FRCEvent.COL_SHORT)));
			}
			setViewText(R.id.event_name, c,
				FRCEvent.COL_NAME);
			setViewText(R.id.event_location, c,
				FRCEvent.COL_LOCATION);
			setViewText(R.id.event_address, c,
				FRCEvent.COL_VENUE_ADDRESS);
			TextView tv = setViewText(R.id.event_website, c,
				FRCEvent.COL_WEBSITE);
			Linkify.addLinks(tv, Linkify.WEB_URLS);
		}
		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			// FIXME
		}
	};

	/** Set text for a TextView from a Cursor column */
	private TextView setViewText(int id, Cursor c, String col) {
		TextView tv = (TextView) root_view.findViewById(id);
		tv.setText(c.getString(c.getColumnIndex(col)));
		return tv;
	}

	/** Create a loader for Event details */
	private Loader<Cursor> createLoader(Bundle b) {
		if (b.containsKey(ARG_EVENT_ID)) {
			int _id = b.getInt(ARG_EVENT_ID);
			return new CursorLoader(getContext(),
				FRCEvent.CONTENT_URI, COLS, "_id=" + _id,
				null, null);
		} else
			return null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLoaderManager().initLoader(EVENT_DETAIL_LOADER_ID,
			getArguments(), cb);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg,
				 Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.event_detail, vg, false);
		root_view = v;
		return v;
	}
}
