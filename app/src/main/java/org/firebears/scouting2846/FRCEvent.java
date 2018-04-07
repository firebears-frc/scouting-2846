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

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;
import org.json.JSONObject;

/**
 * DB stuff for FRC Events.
 */
public class FRCEvent implements BaseColumns {

	static private final String TAG = "FRCEvent";

	static public final String TABLE_NAME = "event";
	static public final String COL_KEY = "ev_key";
	static public final String COL_NAME = "ev_name";
	static public final String COL_SHORT = "short_name";
	static public final String COL_OFFICIAL = "official";
	static public final String COL_EV_CODE = "ev_code";
	static public final String COL_EV_TYPE = "ev_type";
	static public final String COL_DISTRICT = "district";
	static public final String COL_YEAR = "year";
	static public final String COL_WEEK = "week";
	static public final String COL_START_DATE = "start_date";
	static public final String COL_END_DATE = "end_date";
	static public final String COL_LOCATION = "location";
	static public final String COL_VENUE_ADDRESS = "venue_address";
	static public final String COL_TIMEZONE = "timezone";
	static public final String COL_WEBSITE = "website";

	// Foreign key for FRCEvent._id
	static public final String COL_EVENT_ID = "event_id";

	static public final Uri CONTENT_URI = OurContentProvider.buildUri(
		TABLE_NAME);

	/** Columns required in event table */
	static private final String[] EVENT_REQ = {
		"key",
		"name",
		"event_code",
		"event_type",
		"event_district",
		"year",
		"timezone",
		"location",
	};

	/** Parse a JSON event object from TBA */
	static public ContentValues parse(JSONObject ev) {
		for (String col: EVENT_REQ) {
			if (ev.isNull(col)) {
				Log.e(TAG, col + " is null");
				return null;
			}
		}
		ContentValues cv = new ContentValues();
		cv.put(COL_KEY, ev.optString("key"));
		cv.put(COL_NAME, ev.optString("name"));
		putString(cv, ev, COL_SHORT, "short_name");
		cv.put(COL_OFFICIAL, ev.optBoolean("official", false));
		cv.put(COL_EV_CODE, ev.optString("event_code"));
		cv.put(COL_EV_TYPE, ev.optInt("event_type"));
		cv.put(COL_DISTRICT, ev.optInt("event_district"));
		cv.put(COL_YEAR, ev.optInt("year"));
		if (ev.has("week"))
			cv.put(COL_WEEK, ev.optInt("week"));
		putString(cv, ev, COL_START_DATE, "start_date");
		putString(cv, ev, COL_END_DATE, "end_date");
		putString(cv, ev, COL_LOCATION, "location");
		putString(cv, ev, COL_VENUE_ADDRESS, "venue_address");
		putString(cv, ev, COL_TIMEZONE, "timezone");
		putString(cv, ev, COL_WEBSITE, "website");
		return cv;
	}

	static private void putString(ContentValues cv, JSONObject jo,
		String col, String a)
	{
		if (jo.has(a) && !jo.isNull(a))
			cv.put(col, jo.optString(a));
	}
}
