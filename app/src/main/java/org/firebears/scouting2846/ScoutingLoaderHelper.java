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
import static org.firebears.scouting2846.ScoutingRec.REC;

/**
 * Scouting loader helper.
 */
abstract public class ScoutingLoaderHelper extends LoaderHelper {

	static private final String TAG = "ScoutingLoaderHelper";

	/** Create scouting loader helper */
	public ScoutingLoaderHelper(Context c) {
		super(c);
	}

	/** Get loader ID */
	@Override
	public int getId() {
		return 44;
	}

	/** Create a loader */
	@Override
	protected Loader<Cursor> createLoader(Bundle b) {
		String where = getWhere(b);
		Log.d(TAG, "createLoader where " + where);
		return new CursorLoader(context, REC.getContentUri(),
			REC.getCols(), where, null, null);
	}

	/** Get where clause */
	private String getWhere(Bundle b) {
		return REC.COL_SCOUTER + "=" + b.getInt(REC.COL_SCOUTER) + " AND "
		     + REC.COL_MATCH_KEY + "='" + b.getString(REC.COL_MATCH_KEY) + "' AND "
		     + Team.COL_KEY + "='" + b.getString(Team.COL_KEY) + "'";
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		Log.d(TAG, "onLoadFinished: " + c.getCount());
		if (c.getCount() == 1) {
			c.moveToFirst();
			onLoaded(c);
		} else
			onLoaded(null);
	}
}
