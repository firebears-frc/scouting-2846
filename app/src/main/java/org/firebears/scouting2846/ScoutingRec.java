/*
 * Copyright  2017-2019  Douglas P Lau
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

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.os.Bundle;
import android.provider.BaseColumns;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * DB stuff for scouting data.
 */
public class ScoutingRec implements BaseColumns {

	static private final String TAG = "ScoutingRec";

	static public final String COL_SCOUTER = Param.ROW_SCOUTER;
	static public final String COL_OBSERVATION = Param.ROW_OBSERVATION;
	static public final String COL_MATCH_KEY = "match_key";
	static public final String COL_TEAM_NUMBER = "team_number";

	static private final ScoutingRec Y2017 = new ScoutingRec("2017",
		R.string.scouting_2017, R.layout.activity_scouting_2017,
		R.layout.browse_2017_detail);
	static {
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

	static private final ScoutingRec Y2018 = new ScoutingRec("2018",
		R.string.scouting_2018, R.layout.activity_scouting_2018,
		R.layout.browse_2018_detail);
	static {
		Y2018.add(new ScoutingBool("auto_baseline", R.id.baseline));
		Y2018.add(new ScoutingInt("auto_switch", R.id.auto_switch,
			R.id.asw_minus, R.id.asw_plus));
		Y2018.add(new ScoutingInt("auto_scale", R.id.auto_scale,
			R.id.asc_minus, R.id.asc_plus));
		Y2018.add(new ScoutingInt("tele_switch", R.id.tele_switch,
			R.id.tsw_minus, R.id.tsw_plus));
		Y2018.add(new ScoutingInt("opp_switch", R.id.opp_switch,
			R.id.opp_minus, R.id.opp_plus));
		Y2018.add(new ScoutingInt("tele_scale", R.id.tele_scale,
			R.id.tsc_minus, R.id.tsc_plus));
		Y2018.add(new ScoutingInt("fell_scale", R.id.fell_scale,
			R.id.fell_minus, R.id.fell_plus));
		Y2018.add(new ScoutingInt("exchange", R.id.exchange,
			R.id.ex_minus, R.id.ex_plus));
		Y2018.add(new ScoutingBool("cube_from_human",
			R.id.cube_from_human));
		Y2018.add(new ScoutingBool("cube_floor", R.id.cube_floor));
		Y2018.add(new ScoutingBool("defense", R.id.defense));
		Y2018.add(new ScoutingBool("climb", R.id.climb));
		Y2018.add(new ScoutingBool("rider", R.id.rider));
		Y2018.add(new ScoutingBool("park", R.id.park));
		Y2018.add(new ScoutingStr("notes", R.id.notes));
	}

	static private final ScoutingRec Y2019 = new ScoutingRec("2019",
		R.string.scouting_2019, R.layout.activity_scouting_2019,
		R.layout.browse_2019_detail);
	static {
		Y2019.add(new ScoutingBool("autonomous", R.id.autonomous));
		Y2019.add(new ScoutingBool("vision_driver", R.id.vision_driver));
		Y2019.add(new ScoutingBool("auto_lvl_2", R.id.auto_lvl_2));
		Y2019.add(new ScoutingBool("auto_hatch_lo",
			R.id.auto_hatch_lo));
		Y2019.add(new ScoutingBool("auto_hatch_md",
			R.id.auto_hatch_md));
		Y2019.add(new ScoutingBool("auto_hatch_hi",
			R.id.auto_hatch_hi));
		Y2019.add(new ScoutingBool("auto_cargo_lo",
			R.id.auto_cargo_lo));
		Y2019.add(new ScoutingBool("auto_cargo_md",
			R.id.auto_cargo_md));
		Y2019.add(new ScoutingBool("auto_cargo_hi",
			R.id.auto_cargo_hi));
		Y2019.add(new ScoutingBool("defense", R.id.defense));
		Y2019.add(new ScoutingBool("hatch_human", R.id.hatch_human));
		Y2019.add(new ScoutingBool("hatch_floor", R.id.hatch_floor));
		Y2019.add(new ScoutingInt("tele_hatch_lo", R.id.tele_hatch_lo,
			R.id.thl_minus, R.id.thl_plus));
		Y2019.add(new ScoutingInt("tele_hatch_md", R.id.tele_hatch_md,
			R.id.thm_minus, R.id.thm_plus));
		Y2019.add(new ScoutingInt("tele_hatch_hi", R.id.tele_hatch_hi,
			R.id.thh_minus, R.id.thh_plus));
		Y2019.add(new ScoutingBool("cargo_human", R.id.cargo_human));
		Y2019.add(new ScoutingBool("cargo_floor", R.id.cargo_floor));
		Y2019.add(new ScoutingInt("cargo_ship", R.id.cargo_ship,
			R.id.tcs_minus, R.id.tcs_plus));
		Y2019.add(new ScoutingInt("tele_cargo_lo", R.id.tele_cargo_lo,
			R.id.tcl_minus, R.id.tcl_plus));
		Y2019.add(new ScoutingInt("tele_cargo_md", R.id.tele_cargo_md,
			R.id.tcm_minus, R.id.tcm_plus));
		Y2019.add(new ScoutingInt("tele_cargo_hi", R.id.tele_cargo_hi,
			R.id.tch_minus, R.id.tch_plus));
		Y2019.add(new ScoutingBool("climb_lvl_1", R.id.climb_lvl_1));
		Y2019.add(new ScoutingBool("climb_lvl_2", R.id.climb_lvl_2));
		Y2019.add(new ScoutingBool("climb_lvl_3", R.id.climb_lvl_3));
		Y2019.add(new ScoutingBool("assist_lvl_2", R.id.assist_lvl_2));
		Y2019.add(new ScoutingBool("assist_lvl_3", R.id.assist_lvl_3));
		Y2019.add(new ScoutingStr("notes", R.id.notes));
	}

	static public final ScoutingRec REC = Y2019;

	public final String year;

	public final String table_name;

	public final int title_res;

	public final int scouting_activity_res;

	public final int browse_detail_res;

	private final ArrayList<ScoutingData> data;

	private ScoutingRec(String y, int t_res, int sa_res, int bd_res) {
		year = y;
		table_name = "scouting_" + y;
		title_res = t_res;
		scouting_activity_res = sa_res;
		browse_detail_res = bd_res;
		data = new ArrayList<ScoutingData>();
		data.add(new ScoutingInt(_ID, 0));
		data.add(new ScoutingInt(COL_SCOUTER, 0));
		data.add(new ScoutingInt(COL_OBSERVATION, 0));
		data.add(new ScoutingStr(COL_MATCH_KEY, 0));
		data.add(new ScoutingInt(COL_TEAM_NUMBER, 0));
	}

	static public boolean isMeta(String c) {
		return c.equals(COL_MATCH_KEY) || c.equals(COL_TEAM_NUMBER);
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
		// Skip _ID column
		for (int i = 1; i < data.size(); i++) {
			ScoutingData sd = data.get(i);
			sd.init(cv, jo);
		}
		return cv;
	}

	/** SQL statement to create scouting table */
	public String sqlCreate() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + table_name + " (" +
		          _ID + " INTEGER PRIMARY KEY autoincrement, " +
		          COL_SCOUTER + " INTEGER NOT NULL, " +
		          COL_OBSERVATION + " INTEGER NOT NULL, " +
		          COL_MATCH_KEY + " TEXT, " +
		          COL_TEAM_NUMBER + " INTEGER NOT NULL, ");
		for (int i = 5; i < data.size(); i++) {
			ScoutingData sd = data.get(i);
			sb.append(sd.sql());
			sb.append(", ");
		}
		// FIXME: maybe add event ID somehow...
		sb.append("UNIQUE (" + COL_SCOUTER + ", " +
		                       COL_MATCH_KEY + ", " +
		                       COL_TEAM_NUMBER +
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

	public Bundle summarize(int team_num, ArrayList<Bundle> v) {
		Bundle b = new Bundle();
		for (ScoutingData sd : data)
			sd.summarize(b, v);
		b.putInt(COL_TEAM_NUMBER, team_num);
		String title = "" + v.size() + " observation";
		if (v.size() != 1)
			title = title + "s";
		b.putString("title", title);
		return b;
	}

	public void initContent(ContentValues cv, int team_num,
		String match_key)
	{
		// Skip _ID column
		for (int i = 1; i < data.size(); i++) {
			ScoutingData sd = data.get(i);
			sd.init(cv);
		}
		cv.put(COL_MATCH_KEY, match_key);
		cv.put(COL_TEAM_NUMBER, team_num);
	}

	public void updateContent(ContentValues cv, Cursor c) {
		for (int i = 5; i < data.size(); i++) {
			ScoutingData sd = data.get(i);
			sd.update(cv, c);
		}
	}

	public boolean updateContent(ContentValues cv, Activity a) {
		boolean r = false;
		for (int i = 5; i < data.size(); i++) {
			ScoutingData sd = data.get(i);
			sd.update(cv, a);
			boolean d = sd.hasData(cv);
			r |= d;
			String c = sd.getCol();
			Log.d(TAG, "update: " + c + " " + cv.get(c) + " " + d);
		}
		return r;
	}
}
