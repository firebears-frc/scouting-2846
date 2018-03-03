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
import android.os.Bundle;
import android.provider.BaseColumns;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * DB stuff for scouting data.
 */
public class ScoutingRec implements BaseColumns {

	static public final String COL_SCOUTER = Param.ROW_SCOUTER;
	static public final String COL_OBSERVATION = Param.ROW_OBSERVATION;
	static public final String COL_MATCH = "match_key";
	static public final String COL_TEAM_KEY = "tm_key";

	static private final ScoutingRec Y2017 = new ScoutingRec(
		"scouting_2017", R.string.scouting_2017,
		R.layout.activity_scouting_2017,
 		R.layout.activity_browse_2017, R.layout.browse_2017_detail);
	static {
		Y2017.add(new ScoutingInt(_ID, 0));
		Y2017.add(new ScoutingInt(COL_SCOUTER, 0));
		Y2017.add(new ScoutingInt(COL_OBSERVATION, 0));
		Y2017.add(new ScoutingStr(COL_MATCH, 0));
		Y2017.add(new ScoutingStr(COL_TEAM_KEY, 0));
		Y2017.add(new ScoutingInt("auto_high_goal", R.id.auto_high,
			R.id.ah_minus, R.id.ah_plus));
		Y2017.add(new ScoutingInt("auto_low_goal", R.id.auto_low,
			R.id.al_minus, R.id.al_plus));
		Y2017.add(new ScoutingBool("auto_place_gear", R.id.auto_gear));
		Y2017.add(new ScoutingBool("auto_baseline", R.id.baseline));
		Y2017.add(new ScoutingInt("high_goal", R.id.tele_high,
			R.id.th_minus, R.id.th_plus));
		Y2017.add(new ScoutingInt("low_goal", R.id.tele_low,
			R.id.tl_minus, R.id.tl_plus));
		Y2017.add(new ScoutingInt("place_gear", R.id.tele_gear,
			R.id.tg_minus, R.id.tg_plus));
		Y2017.add(new ScoutingBool("climb_rope", R.id.climb));
		Y2017.add(new ScoutingBool("touch_pad", R.id.touchpad));
		Y2017.add(new ScoutingBool("ball_human", R.id.ball_human));
		Y2017.add(new ScoutingBool("ball_floor", R.id.ball_floor));
		Y2017.add(new ScoutingBool("ball_hopper", R.id.ball_hopper));
		Y2017.add(new ScoutingBool("pilot_effective", R.id.effective));
		Y2017.add(new ScoutingBool("release_rope", R.id.release_rope));
		Y2017.add(new ScoutingBool("lose_gear", R.id.lose_gear));
		Y2017.add(new ScoutingStr("notes", R.id.notes));
	}
	static public final ScoutingRec REC = Y2017;

	public final String table_name;

	public final int title_res;

	public final int scouting_activity_res;

	public final int browse_activity_res;

	public final int browse_detail_res;

	private final ArrayList<ScoutingData> data;

	private ScoutingRec(String n, int t_res, int sa_res, int ba_res,
		int bd_res)
	{
		table_name = n;
		title_res = t_res;
		scouting_activity_res = sa_res;
		browse_activity_res = ba_res;
		browse_detail_res = bd_res;
		data = new ArrayList<ScoutingData>();
	}

	private void add(ScoutingData sd) {
		data.add(sd);
	}

	public ScoutingData[] getAllData() {
		return data.toArray(new ScoutingData[0]);
	}

	/** Get an array of all columns */
	public String[] getCols() {
		String[] cols = new String[data.size()];
		for (int i = 0; i < cols.length; i++)
			cols[i] = data.get(i).getCol();
		return cols;
	};

	public Uri getContentUri() {
		return OurContentProvider.buildUri(table_name);
	}

	/** Parse a JSON scouting object */
	public ContentValues parse(JSONObject jo) throws JSONException {
		ContentValues cv = new ContentValues();
		for (ScoutingData sd : data)
			sd.init(cv, jo);
		return cv;
	}

	/** SQL statement to create scouting table */
	public String sqlCreate() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + table_name + " (" +
		          _ID + " INTEGER PRIMARY KEY autoincrement, " +
		          COL_SCOUTER + " INTEGER NOT NULL, " +
		          COL_OBSERVATION + " INTEGER NOT NULL, " +
		          COL_MATCH + " TEXT, " +
		          COL_TEAM_KEY + " TEXT NOT NULL, ");
		for (int i = 5; i < data.size(); i++) {
			ScoutingData sd = data.get(i);
			sb.append(sd.sql());
			sb.append(", ");
		}
		sb.append("UNIQUE (" + COL_SCOUTER + ", " +
		                       COL_MATCH + ", " +
		                       COL_TEAM_KEY +
		                 ") ON CONFLICT REPLACE)");
		return sb.toString();
	}

	/** SQL statement to drop scouting table */
	public String sqlDrop() {
		return "DROP TABLE IF EXISTS " + table_name;
	}

	public void updateBundle(Bundle b, Cursor c) {
		for (ScoutingData sd : data)
			sd.update(b, c);
	}

	public void initContent(ContentValues cv, String team_key,
		String match_key)
	{
		for (ScoutingData sd : data)
			sd.init(cv);
		cv.put(COL_MATCH, match_key);
		cv.put(COL_TEAM_KEY, team_key);
	}

	public void updateContent(ContentValues cv, Cursor c) {
		for (ScoutingData sd : data)
			sd.update(cv, c);
	}
}
