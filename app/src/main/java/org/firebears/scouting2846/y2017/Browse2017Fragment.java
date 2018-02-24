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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.firebears.scouting2846.R;
import org.firebears.scouting2846.Team;

/**
 * Browsing fragment for 2017 (Steamworks).
 */
public class Browse2017Fragment extends Fragment {

	static private final String TAG = "Browse2017Fragment";

	private Bundle args;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg,
		Bundle savedInstanceState)
	{
		args = getArguments();
		View rv = inflater.inflate(R.layout.browse_2017_detail,
			vg, false);
		Toolbar tb = (Toolbar) rv.findViewById(R.id.detail_toolbar);
		AppCompatActivity act = (AppCompatActivity) getActivity();
		act.setSupportActionBar(tb);
		int num = args.getInt(Team.COL_TEAM_NUMBER);
		String nick = args.getString(Team.COL_NICKNAME);
		tb.setTitle("" + num + ' ' + nick);
		Log.d(TAG, "team " + num + " " + nick);
		initView(rv);
		return rv;
	}

	private void initView(View rv) {
		initString(rv, R.id.ah_txt, Scouting2017.COL_AUTO_HIGH_GOAL);
		initString(rv, R.id.al_txt, Scouting2017.COL_AUTO_LOW_GOAL);
		initBool(rv, R.id.ag_txt, Scouting2017.COL_AUTO_GEAR);
		initBool(rv, R.id.bl_txt, Scouting2017.COL_AUTO_BASELINE);
		initString(rv, R.id.th_txt, Scouting2017.COL_HIGH_GOAL);
		initString(rv, R.id.tl_txt, Scouting2017.COL_LOW_GOAL);
		initString(rv, R.id.tg_txt, Scouting2017.COL_PLACE_GEAR);
		initBool(rv, R.id.cl_txt, Scouting2017.COL_CLIMB_ROPE);
		initBool(rv, R.id.tp_txt, Scouting2017.COL_TOUCH_PAD);
		initBool(rv, R.id.bh_txt, Scouting2017.COL_BALL_HUMAN);
		initBool(rv, R.id.bf_txt, Scouting2017.COL_BALL_FLOOR);
		initBool(rv, R.id.bp_txt, Scouting2017.COL_BALL_HOPPER);
		initBool(rv, R.id.pe_txt, Scouting2017.COL_PILOT_EFFECTIVE);
		initBool(rv, R.id.rr_txt, Scouting2017.COL_RELEASE_ROPE);
		initBool(rv, R.id.lg_txt, Scouting2017.COL_LOSE_GEAR);
		initString(rv, R.id.notes, Scouting2017.COL_NOTES);
	}

	private void initString(View rv, int res, String col) {
		String v = (args.containsKey(col))
		         ? args.get(col).toString()
		         : "";
		TextView tv = (TextView) rv.findViewById(res);
		tv.setText(v);
	}

	private void initBool(View rv, int res, String col) {
		int v = (args.containsKey(col))
		      ? args.getInt(col)
		      : 0;
		TextView tv = (TextView) rv.findViewById(res);
		tv.setText(v != 0 ? "Yes" : "No");
	}
}
