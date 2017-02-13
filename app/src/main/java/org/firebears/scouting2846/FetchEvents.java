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
 * Task to fetch event list from TBA.
 */
public class FetchEvents extends AsyncTask<Void, Void, Void> {

	static private final String PRACTICE_JSON = "{" +
		"\"key\": \"practice\"," +
  		"\"name\": \" Practice Event\"," +
		"\"short_name\": \"Practice\"," +
		"\"official\": false," +
		"\"event_code\": 1," +
  		"\"event_type\": 1," +
  		"\"event_district\": \"DISTRICT\"," +
  		"\"year\": 2017," +
  		"\"week\": 0," +
  		"\"location\": \"LOCATION\"," +
  		"\"venue_address\": \"VENUE\"," +
		"\"timezone\": \"TZ\"," +
		"\"website\": \"WEBSITE\"" +
	"}";

	private final EventListActivity context;

	public FetchEvents(EventListActivity ctx) {
		context = ctx;
	}

	@Override
	protected Void doInBackground(Void... v) {
		ContentResolver cr = context.getContentResolver();
		try {
			insertOrUpdate(cr, createPracticeEvent());
			insertEvents(cr, TBAFetcher.fetchEvents());
		}
		catch (JSONException e) {
			Log.e("FetchEvents", e.getMessage());
		}
		return null;
	}

	private ContentValues createPracticeEvent() throws JSONException {
		return FRCEvent.parse(new JSONObject(PRACTICE_JSON));
	}

	static private final String[] COLS_KEY = {
		FRCEvent.COL_KEY,
	};

	private void insertOrUpdate(ContentResolver cr, ContentValues cv) {
		String key = FRCEvent.COL_KEY + "='" +
			cv.getAsString(FRCEvent.COL_KEY) + "'";
		Cursor c = cr.query(FRCEvent.CONTENT_URI, COLS_KEY, key, null,
			null);
		try {
			if (c != null && c.getCount() > 0)
				cr.update(FRCEvent.CONTENT_URI, cv, key, null);
			else
				cr.insert(FRCEvent.CONTENT_URI, cv);
		}
		finally {
			if (c != null)
				c.close();
		}
	}

	private void insertEvents(ContentResolver cr, String js)
		throws JSONException
	{
		JSONArray ar = new JSONArray(js);
		for (int i = 0; i < ar.length(); i++) {
			JSONObject jo = ar.getJSONObject(i);
			ContentValues cv = FRCEvent.parse(jo);
			if (cv != null)
				insertOrUpdate(cr, cv);
			else
				Log.e("insertEvents", "parse " + jo);
		}
	}

	@Override
	protected void onPostExecute(Void v) {
		context.restartLoader();
	}
}
