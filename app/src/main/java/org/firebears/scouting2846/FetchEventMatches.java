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

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Task to fetch event match list from TBA.
 */
public class FetchEventMatches extends AsyncTask<Void, Void, Void> {

	static private final String TAG = "FetchEventMatches";

	private final MatchListActivity context;

	private final int event_id;
	private final String event_key;

	public FetchEventMatches(MatchListActivity ctx, int eid, String ek) {
		context = ctx;
		event_id = eid;
		event_key = ek;
	}

	@Override
	protected Void doInBackground(Void... v) {
		ContentResolver cr = context.getContentResolver();
		try {
			insertEventMatches(cr, TBAFetcher.fetchMatches(
				event_key));
		}
		catch (JSONException e) {
			Log.e(TAG, "exception " + e.getMessage());
		}
		return null;
	}

	static private final String[] COLS_KEY = { Match.COL_KEY, };
	static private final String[] COLS_ID = { Match.COL_ID, };

	private void insertEventMatches(ContentResolver cr, String js)
		throws JSONException
	{
		JSONArray ar = new JSONArray(js);
		for (int i = 0; i < ar.length(); i++) {
			JSONObject jo = ar.getJSONObject(i);
			ContentValues cv = Match.parse(jo, event_id);
			if (cv != null)
				insertOrUpdate(cr, cv);
			else
				Log.e(TAG, "parse error " + jo);
		}
	}

	private void insertOrUpdate(ContentResolver cr, ContentValues cv) {
		String key = Match.COL_KEY + "='" +
			cv.getAsString(Match.COL_KEY) + "'";
		Cursor c = cr.query(Match.CONTENT_URI, COLS_KEY, key, null,
			null);
		try {
			if (c != null && c.getCount() > 0)
				cr.update(Match.CONTENT_URI, cv, key, null);
			else
				cr.insert(Match.CONTENT_URI, cv);
		}
		finally {
			if (c != null)
				c.close();
		}
	}

	private int lookupMatchId(ContentResolver cr, ContentValues cv) {
		String key = Match.COL_KEY + "='" +
			cv.getAsString(Match.COL_KEY) + "'";
		Cursor c = cr.query(Match.CONTENT_URI, COLS_ID, key, null,
			null);
		return (c != null) ? lookupMatchId(c) : 0;
	}

	private int lookupMatchId(Cursor c) {
		try {
			c.moveToFirst();
			return c.getInt(c.getColumnIndex(Match.COL_ID));
		}
		finally {
			c.close();
		}
	}

	@Override
	protected void onPostExecute(Void v) {
		context.restartLoader();
	}
}
