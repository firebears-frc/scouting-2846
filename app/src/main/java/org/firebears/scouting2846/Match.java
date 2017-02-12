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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * DB stuff for a Match.
 */
public class Match implements BaseColumns {

	static public final String TABLE_NAME = "match";
	static public final String COL_ID = "_id";
	static public final String COL_KEY = "mt_key";
	static public final String COL_EVENT = "event_id";
	static public final String COL_EVENT_KEY = "event_key";
	static public final String COL_COMP_LEVEL = "comp_level";
	static public final String COL_SET_NUMBER = "set_number";
	static public final String COL_MATCH_NUMBER = "match_number";
	static public final String COL_ALLIANCES = "alliances";
	static public final String COL_SCORE_BREAKDOWN = "score_breakdown";
	static public final String COL_VIDEOS = "videos";
	static public final String COL_TIME = "time";
	static public final String COL_RED_0 = "red_0";
	static public final String COL_RED_1 = "red_1";
	static public final String COL_RED_2 = "red_2";
	static public final String COL_BLUE_0 = "blue_0";
	static public final String COL_BLUE_1 = "blue_1";
	static public final String COL_BLUE_2 = "blue_2";

	static public final Uri CONTENT_URI = OurContentProvider.buildUri(
		TABLE_NAME);

	static private final String[] COMP_LVL = {
		"qm", "ef", "qf", "sf", "f"
	};
	static public final String[] COMP_LEVEL = {
		"Qualifiers", "Eighth-finals", "Quarter-finals", "Semi-finals",
		"Finals"
	};

	static private Integer parseCompLvl(String cl) {
		for (int i = 0; i < COMP_LVL.length; i++) {
			if (COMP_LVL[i].equals(cl))
				return i;
		}
		return null;
	}

	static public String description(int lvl, int sn, int mn) {
		if (lvl >= 0 && lvl < COMP_LEVEL.length) {
			StringBuilder sb = new StringBuilder();
			sb.append(COMP_LEVEL[lvl]);
			sb.append(' ');
			if (lvl > 0) {
				sb.append(sn);
				sb.append('-');
			}
			sb.append(mn);
			return sb.toString();
		} else
			return null;
	}

	/** Parse a JSON match object from TBA */
	static public ContentValues parse(JSONObject mt, int event_id) {
		ContentValues cv = new ContentValues();
		cv.put(COL_KEY, mt.optString("key"));
		Integer cl = parseCompLvl(mt.optString("comp_level"));
		if (cl != null)
			cv.put(COL_COMP_LEVEL, cl);
		cv.put(COL_SET_NUMBER, mt.optInt("set_number"));
		cv.put(COL_MATCH_NUMBER, mt.optInt("match_number"));
		cv.put(COL_ALLIANCES, mt.optString("alliances"));
		cv.put(COL_SCORE_BREAKDOWN, mt.optString("score_breakdown"));
		cv.put(COL_EVENT, event_id);
		cv.put(COL_EVENT_KEY, mt.optString("event_key"));
		cv.put(COL_VIDEOS, mt.optString("videos"));
		cv.put(COL_TIME, mt.optInt("time"));
		JSONObject all = mt.optJSONObject("alliances");
		cv.put(COL_RED_0, parseTeam(all, "red", 0));
		cv.put(COL_RED_1, parseTeam(all, "red", 1));
		cv.put(COL_RED_2, parseTeam(all, "red", 2));
		cv.put(COL_BLUE_0, parseTeam(all, "blue", 0));
		cv.put(COL_BLUE_1, parseTeam(all, "blue", 1));
		cv.put(COL_BLUE_2, parseTeam(all, "blue", 2));
		return cv;
	}

	/** Parse team from alliances */
	static private String parseTeam(JSONObject all, String rb, int n) {
		if (all != null) {
			JSONObject a = all.optJSONObject(rb);
			if (a != null) {
				JSONArray t = a.optJSONArray("teams");
				if (t != null)
					return t.optString(n, null);
			}
		}
		return null;
	}
}
