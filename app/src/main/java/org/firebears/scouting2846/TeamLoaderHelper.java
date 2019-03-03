/*
 * Copyright  2018-2019  Douglas P Lau
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
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

/**
 * Team loader helper.
 */
abstract public class TeamLoaderHelper extends LoaderHelper {

	static private final String TAG = "TeamLoaderHelper";

	/** Columns to retrieve from the loader */
	static private final String[] COLS = {
		Team.COL_TEAM_NUMBER,
		Team.COL_NICKNAME,
		Team.COL_LOCALITY, // city
		Team.COL_REGION,
		Team.COL_COUNTRY,
		Team.COL_LOCATION,
		Team.COL_WEBSITE,
		Team.COL_ROOKIE_YEAR,
		Team.COL_MOTTO,
	};

	/** Create team loader helper */
	public TeamLoaderHelper(Context c) {
		super(c);
	}

	/** Get loader ID */
	@Override
	public int getId() {
		return 45;
	}

	/** Create a team loader */
	@Override
	protected Loader<Cursor> createLoader(Bundle b) {
		if (b.containsKey(Team.COL_TEAM_NUMBER)) {
			int team_num = b.getInt(Team.COL_TEAM_NUMBER);
			Log.d(TAG, "team #" + team_num);
			return new CursorLoader(context, Team.CONTENT_URI,
				COLS, Team.COL_TEAM_NUMBER + "=" + team_num,
				null, null);
		} else
			return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		Log.d(TAG, "load finished: " + c.getCount());
		if (c.getCount() == 1) {
			c.moveToFirst();
			onLoaded(c);
		}
	}
}
