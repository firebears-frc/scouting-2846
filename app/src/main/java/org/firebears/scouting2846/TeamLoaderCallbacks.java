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

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

/**
 * Callbacks for team loader.
 */
abstract public class TeamLoaderCallbacks implements LoaderCallbacks<Cursor> {

	/** Team loader ID */
	static public final int LOADER_ID = 45;

	/** Columns to retrieve from the loader */
	static private final String[] COLS = {
		Team.COL_NAME,
		Team.COL_TEAM_NUMBER,
		Team.COL_NICKNAME,
		Team.COL_WEBSITE,
		Team.COL_ROOKIE_YEAR,
		Team.COL_MOTTO,
	};

	private final Context context;

	/** Create team loader callbacks */
	public TeamLoaderCallbacks(Context c) {
		context = c;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle b) {
		return (LOADER_ID == id)
		      ? createLoader(b)
		      : null;
	}

	/** Create a team loader */
	private Loader<Cursor> createLoader(Bundle b) {
		if (b.containsKey(Team.COL_KEY)) {
			String key = b.getString(Team.COL_KEY);
			return new CursorLoader(context, Team.CONTENT_URI,
				COLS, Team.COL_KEY + "='" + key + "'", null,
				null);
		} else
			return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		if (c.getCount() == 1) {
			c.moveToFirst();
			onTeamLoaded(c);
		}
	}

	/** Called when team has been loaded */
	abstract protected void onTeamLoaded(Cursor c);

	@Override
	public void onLoaderReset(Loader<Cursor> loader) { }
}
