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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * DB stuff for scouting data.
 */
public class Scouting2017 implements BaseColumns {

	static public final String TABLE_NAME = "scouting_2017";
	static public final String COL_ID = "_id";
	static public final String COL_SCOUTER = Param.ROW_SCOUTER;
	static public final String COL_OBSERVATION = Param.ROW_OBSERVATION;
	static public final String COL_MATCH = "match_key";
	static public final String COL_TEAM_KEY = "tm_key";
	static public final String COL_AUTO_HIGH_GOAL = "auto_high_goal";
	static public final String COL_AUTO_LOW_GOAL = "auto_low_goal";
	static public final String COL_AUTO_GEAR = "auto_place_gear";
	static public final String COL_AUTO_BASELINE = "auto_baseline";
	static public final String COL_HIGH_GOAL = "high_goal";
	static public final String COL_LOW_GOAL = "low_goal";
	static public final String COL_PLACE_GEAR = "place_gear";
	static public final String COL_CLIMB_ROPE = "climb_rope";
	static public final String COL_TOUCH_PAD = "touch_pad";
	static public final String COL_BALL_HUMAN = "ball_human";
	static public final String COL_BALL_FLOOR = "ball_floor";
	static public final String COL_BALL_HOPPER = "ball_hopper";
	static public final String COL_PILOT_EFFECTIVE = "pilot_effective";
	static public final String COL_RELEASE_ROPE = "release_rope";
	static public final String COL_LOSE_GEAR = "lose_gear";
	static public final String COL_NOTES = "notes";

	static public final Uri CONTENT_URI = OurContentProvider.buildUri(
		TABLE_NAME);

	static public void initContent(ContentValues cv, String team_key,
		String match_key)
	{
		cv.put(COL_SCOUTER, 0);
		cv.put(COL_OBSERVATION, 0);
		cv.put(COL_MATCH, match_key);
		cv.put(COL_TEAM_KEY, team_key);
		cv.put(COL_AUTO_HIGH_GOAL, 0);
		cv.put(COL_AUTO_LOW_GOAL, 0);
		cv.put(COL_AUTO_GEAR, 0);
		cv.put(COL_AUTO_BASELINE, 0);
		cv.put(COL_HIGH_GOAL, 0);
		cv.put(COL_LOW_GOAL, 0);
		cv.put(COL_PLACE_GEAR, 0);
		cv.put(COL_CLIMB_ROPE, 0);
		cv.put(COL_TOUCH_PAD, 0);
		cv.put(COL_BALL_HUMAN, 0);
		cv.put(COL_BALL_FLOOR, 0);
		cv.put(COL_BALL_HOPPER, 0);
		cv.put(COL_PILOT_EFFECTIVE, 0);
		cv.put(COL_RELEASE_ROPE, 0);
		cv.put(COL_LOSE_GEAR, 0);
		cv.put(COL_NOTES, "");
	}

	static public void updateContent(ContentValues cv, Cursor c) {
		updateInt(cv, c, COL_AUTO_HIGH_GOAL);
		updateInt(cv, c, COL_AUTO_LOW_GOAL);
		updateInt(cv, c, COL_AUTO_GEAR);
		updateInt(cv, c, COL_AUTO_BASELINE);
		updateInt(cv, c, COL_HIGH_GOAL);
		updateInt(cv, c, COL_LOW_GOAL);
		updateInt(cv, c, COL_PLACE_GEAR);
		updateInt(cv, c, COL_CLIMB_ROPE);
		updateInt(cv, c, COL_TOUCH_PAD);
		updateInt(cv, c, COL_BALL_HUMAN);
		updateInt(cv, c, COL_BALL_FLOOR);
		updateInt(cv, c, COL_BALL_HOPPER);
		updateInt(cv, c, COL_PILOT_EFFECTIVE);
		updateInt(cv, c, COL_RELEASE_ROPE);
		updateInt(cv, c, COL_LOSE_GEAR);
		updateStr(cv, c, COL_NOTES);
	}

	static private void updateInt(ContentValues cv, Cursor c, String col) {
		int i = c.getColumnIndex(col);
		if (i >= 0) {
			int v = c.getInt(i);
			cv.put(col, v);
		}
	}

	static private void updateStr(ContentValues cv, Cursor c, String col) {
		int i = c.getColumnIndex(col);
		if (i >= 0) {
			String v = c.getString(i);
			cv.put(col, v);
		}
	}

	static private final String[] COLS_ALL = {
		COL_SCOUTER, COL_OBSERVATION, COL_MATCH, COL_TEAM_KEY,
		COL_AUTO_HIGH_GOAL, COL_AUTO_LOW_GOAL, COL_AUTO_GEAR,
		COL_AUTO_BASELINE, COL_HIGH_GOAL, COL_LOW_GOAL, COL_PLACE_GEAR,
		COL_CLIMB_ROPE, COL_TOUCH_PAD, COL_BALL_HUMAN, COL_BALL_FLOOR,
		COL_BALL_HOPPER, COL_PILOT_EFFECTIVE, COL_RELEASE_ROPE,
		COL_LOSE_GEAR, COL_NOTES,
	};

	/** Parse a JSON scouting object */
	static public ContentValues parse(JSONObject jo) throws JSONException {
		ContentValues cv = new ContentValues();
		for (String v : COLS_ALL) {
			Object o = jo.get(v);
			if (o instanceof Integer)
				cv.put(v, (Integer) o);
			else
				cv.put(v, o.toString());
		}
		return cv;
	}
}
