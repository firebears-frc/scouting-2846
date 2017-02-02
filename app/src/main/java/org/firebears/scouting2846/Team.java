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

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import org.json.JSONObject;

/**
 * DB stuff for a Team.
 */
public class Team implements BaseColumns {

	static public final String TABLE_NAME = "team";
	static public final String COL_ID = "_id";
	static public final String COL_KEY = "key";
	static public final String COL_TEAM_NUMBER = "team_number";
	static public final String COL_NAME = "name";
	static public final String COL_NICKNAME = "nickname";
	static public final String COL_WEBSITE = "website";
	static public final String COL_LOCALITY = "locality";
	static public final String COL_REGION = "region";
	static public final String COL_COUNTRY = "country";
	static public final String COL_LOCATION = "location";
	static public final String COL_ROOKIE_YEAR = "rookie_year";
	static public final String COL_MOTTO = "motto";

	static public final Uri CONTENT_URI = OurContentProvider.buildUri(
		TABLE_NAME);

	/** Columns required in team table */
	static private final String[] TEAM_REQ = {
		"key",
		"team_number",
		"name",
		"locality",
		"location",
		"rookie_year",
	};

	/** Parse a JSON event object from TBA */
	static public ContentValues parse(JSONObject ev) {
		for (String col: TEAM_REQ) {
			if (ev.isNull(col))
				return null;
		}
		ContentValues cv = new ContentValues();
		cv.put(COL_KEY, ev.optString("key"));
		cv.put(COL_TEAM_NUMBER, ev.optInt("team_number"));
		cv.put(COL_NAME, ev.optString("name"));
		if (ev.has("nickname"))
			cv.put(COL_NICKNAME, ev.optString("nickname"));
		if (ev.has("website"))
			cv.put(COL_WEBSITE, ev.optString("website"));
		cv.put(COL_LOCALITY, ev.optString("locality"));
		if (ev.has("region"))
			cv.put(COL_REGION, ev.optString("region"));
		if (ev.has("country_name"))
			cv.put(COL_COUNTRY, ev.optString("country_name"));
		cv.put(COL_LOCATION, ev.optString("location"));
		cv.put(COL_ROOKIE_YEAR, ev.optInt("rookie_year"));
		if (ev.has("motto"))
			cv.put(COL_MOTTO, ev.optString("motto"));
		return cv;
	}
}
