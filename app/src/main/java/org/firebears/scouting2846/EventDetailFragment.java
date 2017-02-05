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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A fragment representing a single Event detail screen.
 */
public class EventDetailFragment extends Fragment {

	/** Argument for event id */
	static public final String ARG_EVENT_ID = "event_id";
	static public final String ARG_EVENT_KEY = "event_key";
	static public final String ARG_EVENT_SHORT = "event_short";

	/** Event detail loader ID */
	static private final int EVENT_DETAIL_LOADER_ID = 38;

	/** Columns to retrieve from the loader */
	static private final String[] COLS = {
		FRCEvent.COL_SHORT,
		FRCEvent.COL_NAME,
		FRCEvent.COL_START_DATE,
		FRCEvent.COL_END_DATE,
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
			setViewDates(R.id.event_dates, c);
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

	private void setViewDates(int id, Cursor c) {
		Date start = parseDate(c.getString(c.getColumnIndex(
			FRCEvent.COL_START_DATE)));
		Date end = parseDate(c.getString(c.getColumnIndex(
			FRCEvent.COL_END_DATE)));
		TextView tv = (TextView) root_view.findViewById(id);
		tv.setText(dateRange(start, end));
	}

	private Date parseDate(String dt) {
		try {
			if (dt != null && dt.length() == 10) {
				SimpleDateFormat f = new SimpleDateFormat(
					"yyyy-MM-dd");
				return f.parse(dt);
			}
		}
		catch (ParseException e) { }
		return null;
	}

	private String dateRange(Date st, Date en) {
		if (null == en)
			return formatDate(st);
		else if (null == st)
			return formatDate(en);
		else {
			String sst = formatDate(st);
			String sen = formatDate(en);
			if (sst.equals(sen))
				return sst;
			if (sst.substring(0, 4).equals(sen.substring(0, 4)))
				return sst + " to " + sen.substring(4);
			else
				return sst + " to " + sen;
		}
	}

	private String formatDate(Date dt) {
		if (dt != null) {
			SimpleDateFormat f = new SimpleDateFormat(
				"MMM d (EEE)");
			return f.format(dt);
		} else
			return "";
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
