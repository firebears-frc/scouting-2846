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
import org.firebears.scouting2846.R;
import org.firebears.scouting2846.ScoutingData;
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
		for (ScoutingData sd : Scouting2017.ALL_DATA)
			sd.init(args, rv);
	}
}
