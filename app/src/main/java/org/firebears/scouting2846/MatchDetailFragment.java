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
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A fragment representing a single Match detail screen.
 */
public class MatchDetailFragment extends Fragment {

	/** Argument for match key */
	static public final String ARG_MATCH_KEY = "match_key";

	/** Match detail loader ID */
	static private final int MATCH_DETAIL_LOADER_ID = 43;

	/** Columns to retrieve from the loader */
	static private final String[] COLS = {
		Match.COL_COMP_LEVEL,
		Match.COL_SET_NUMBER,
		Match.COL_MATCH_NUMBER,
		Match.COL_RED_0,
		Match.COL_RED_1,
		Match.COL_RED_2,
		Match.COL_BLUE_0,
		Match.COL_BLUE_1,
		Match.COL_BLUE_2,
		Match._ID,
	};

	/** Required constructor */
	public MatchDetailFragment() { }

	/** Root view */
	private View root_view;

	/** Callbacks for loader */
	private final LoaderCallbacks<Cursor> cb =
		new LoaderCallbacks<Cursor>()
	{
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle b) {
			return (MATCH_DETAIL_LOADER_ID == id)
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
			if (bar != null)
				bar.setTitle(Match.description(c));
			setButtonText(R.id.red_0, c, Match.COL_RED_0);
			setButtonText(R.id.red_1, c, Match.COL_RED_1);
			setButtonText(R.id.red_2, c, Match.COL_RED_2);
			setButtonText(R.id.blue_0, c, Match.COL_BLUE_0);
			setButtonText(R.id.blue_1, c, Match.COL_BLUE_1);
			setButtonText(R.id.blue_2, c, Match.COL_BLUE_2);
		}
		@Override
		public void onLoaderReset(Loader<Cursor> loader) { }
	};

	/** Set text for a Button from a Cursor column */
	private void setButtonText(int id, Cursor c, String col) {
		Button b = (Button) root_view.findViewById(id);
		String t = c.getString(c.getColumnIndex(col));
		if (t != null && !t.equals("null"))
			b.setText(t);
	}

	/** Create a loader for Match details */
	private Loader<Cursor> createLoader(Bundle b) {
		if (b.containsKey(ARG_MATCH_KEY)) {
			String key = b.getString(ARG_MATCH_KEY);
			return new CursorLoader(getContext(),
				Match.CONTENT_URI, COLS, Match.COL_KEY + "='"
				+ key + "'", null, null);
		} else
			return null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLoaderManager().initLoader(MATCH_DETAIL_LOADER_ID,
			getArguments(), cb);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg,
				 Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.match_detail, vg, false);
		root_view = v;
		return v;
	}
}
