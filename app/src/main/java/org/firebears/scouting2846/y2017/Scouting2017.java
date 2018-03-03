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
package org.firebears.scouting2846.y2017;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import org.json.JSONException;
import org.json.JSONObject;
import org.firebears.scouting2846.OurContentProvider;
import org.firebears.scouting2846.Param;
import org.firebears.scouting2846.R;
import org.firebears.scouting2846.ScoutingBool;
import org.firebears.scouting2846.ScoutingData;
import org.firebears.scouting2846.ScoutingInt;
import org.firebears.scouting2846.ScoutingStr;

/**
 * DB stuff for scouting data.
 */
public class Scouting2017 implements BaseColumns {

	static public final String TABLE_NAME = "scouting_2017";

	static public final Uri CONTENT_URI = OurContentProvider.buildUri(
		TABLE_NAME);

	static public final String COL_SCOUTER = Param.ROW_SCOUTER;
	static public final String COL_OBSERVATION = Param.ROW_OBSERVATION;
	static public final String COL_MATCH = "match_key";
	static public final String COL_TEAM_KEY = "tm_key";

	static private final ScoutingInt ID = new ScoutingInt(_ID, 0);
	static private final ScoutingInt SCOUTER = new ScoutingInt(
		Param.ROW_SCOUTER, 0);
	static private final ScoutingInt OBSERVATION = new ScoutingInt(
		Param.ROW_OBSERVATION, 0);
	static private final ScoutingStr MATCH = new ScoutingStr(
		"match_key", 0);
	static private final ScoutingStr TEAM_KEY = new ScoutingStr(
		"tm_key", 0);
	static public final ScoutingInt AUTO_HIGH_GOAL = new ScoutingInt(
		"auto_high_goal", R.id.auto_high);
	static public final ScoutingInt AUTO_LOW_GOAL = new ScoutingInt(
		"auto_low_goal", R.id.auto_low);
	static private final ScoutingBool AUTO_GEAR = new ScoutingBool(
		"auto_place_gear", R.id.auto_gear);
	static private final ScoutingBool AUTO_BASELINE = new ScoutingBool(
		"auto_baseline", R.id.baseline);
	static public final ScoutingInt HIGH_GOAL = new ScoutingInt(
		"high_goal", R.id.tele_high);
	static public final ScoutingInt LOW_GOAL = new ScoutingInt(
		"low_goal", R.id.tele_low);
	static public final ScoutingInt PLACE_GEAR = new ScoutingInt(
		"place_gear", R.id.tele_gear);
	static private final ScoutingBool CLIMB_ROPE = new ScoutingBool(
		"climb_rope", R.id.climb);
	static private final ScoutingBool TOUCH_PAD = new ScoutingBool(
		"touch_pad", R.id.touchpad);
	static private final ScoutingBool BALL_HUMAN = new ScoutingBool(
		"ball_human", R.id.ball_human);
	static private final ScoutingBool BALL_FLOOR = new ScoutingBool(
		"ball_floor", R.id.ball_floor);
	static private final ScoutingBool BALL_HOPPER = new ScoutingBool(
		"ball_hopper", R.id.ball_hopper);
	static private final ScoutingBool PILOT_EFFECTIVE = new ScoutingBool(
		"pilot_effective", R.id.effective);
	static private final ScoutingBool RELEASE_ROPE = new ScoutingBool(
		"release_rope", R.id.release_rope);
	static private final ScoutingBool LOSE_GEAR = new ScoutingBool(
		"lose_gear", R.id.lose_gear);
	static private final ScoutingStr NOTES = new ScoutingStr(
		"notes", R.id.notes);

	static public final ScoutingData[] ALL_DATA = {
		ID, SCOUTER, OBSERVATION, MATCH, TEAM_KEY,
		AUTO_HIGH_GOAL, AUTO_LOW_GOAL, AUTO_GEAR, AUTO_BASELINE,
		HIGH_GOAL, LOW_GOAL, PLACE_GEAR, CLIMB_ROPE, TOUCH_PAD,
		BALL_HUMAN, BALL_FLOOR, BALL_HOPPER, PILOT_EFFECTIVE,
		RELEASE_ROPE, LOSE_GEAR, NOTES,
	};

	static public void initContent(ContentValues cv, String team_key,
		String match_key)
	{
		for (ScoutingData sd : ALL_DATA)
			sd.init(cv);
		cv.put(MATCH.getCol(), match_key);
		cv.put(TEAM_KEY.getCol(), team_key);
	}

	static public void updateContent(ContentValues cv, Cursor c) {
		for (ScoutingData sd : ALL_DATA)
			sd.update(cv, c);
	}

	/** Get an array of all columns */
	static String[] getCols() {
		String[] cols = new String[ALL_DATA.length];
		for (int i = 0; i < cols.length; i++)
			cols[i] = ALL_DATA[i].getCol();
		return cols;
	};

	/** Parse a JSON scouting object */
	static public ContentValues parse(JSONObject jo) throws JSONException {
		ContentValues cv = new ContentValues();
		for (ScoutingData sd : ALL_DATA)
			sd.init(cv, jo);
		return cv;
	}

	static public void updateBundle(Bundle b, Cursor c) {
		for (ScoutingData sd : ALL_DATA)
			sd.update(b, c);
	}

	/** SQL statement to create scouting table */
	static public final String SQL_CREATE =
		"CREATE TABLE " + TABLE_NAME + " (" +
		_ID + " INTEGER PRIMARY KEY autoincrement, " +
		COL_SCOUTER + " INTEGER NOT NULL, " +
		COL_OBSERVATION + " INTEGER NOT NULL, " +
		COL_MATCH + " TEXT, " +
		COL_TEAM_KEY + " TEXT NOT NULL, " +
		AUTO_HIGH_GOAL.sql() + ", " +
		AUTO_LOW_GOAL.sql() + ", " +
		AUTO_GEAR.sql() + ", " +
		AUTO_BASELINE.sql() + ", " +
		HIGH_GOAL.sql() + ", " +
		LOW_GOAL.sql() + ", " +
		PLACE_GEAR.sql() + ", " +
		CLIMB_ROPE.sql() + ", " +
		TOUCH_PAD.sql() + ", " +
		BALL_HUMAN.sql() + ", " +
		BALL_FLOOR.sql() + ", " +
		BALL_HOPPER.sql() + ", " +
		PILOT_EFFECTIVE.sql() + ", " +
		RELEASE_ROPE.sql() + ", " +
		LOSE_GEAR.sql() + ", " +
		NOTES.sql() + ", " +
		"UNIQUE (" + COL_SCOUTER + ", " +
		             COL_MATCH + ", " +
		             COL_TEAM_KEY +
		        ") ON CONFLICT REPLACE)";

	/** SQL statement to drop scouting table */
	static public final String SQL_DROP =
		"DROP TABLE IF EXISTS " + TABLE_NAME;
}
