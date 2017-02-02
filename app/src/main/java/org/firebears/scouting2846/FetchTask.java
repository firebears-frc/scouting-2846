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
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class FetchTask extends AsyncTask<Void, Void, Void> {

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

	private final Context context;

	public FetchTask(Context ctx) {
		context = ctx;
	}

	@Override
	protected Void doInBackground(Void... v) {
		ContentResolver cr = context.getContentResolver();
		try {
			cr.insert(FRCEvent.CONTENT_URI, createPracticeEvent());
			insertEvents(cr, TBAFetcher.fetchEvents());
		}
		catch (Exception e) {
			Log.e("FetchTask", "exception " + e);
		}
		return null;
	}

	private ContentValues createPracticeEvent() throws JSONException {
		return FRCEvent.parse(new JSONObject(PRACTICE_JSON));
	}

	private void insertEvents(ContentResolver cr, String js)
		throws JSONException
	{
		JSONArray ar = new JSONArray(js);
		for (int i = 0; i < ar.length(); i++) {
			ContentValues cv = FRCEvent.parse(ar.getJSONObject(i));
			if (cv != null)
				cr.insert(FRCEvent.CONTENT_URI, cv);
		}
	}
}
