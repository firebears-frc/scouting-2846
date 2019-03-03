/*
 * Copyright  2018-2019  Douglas P Lau
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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Summary fragment.
 */
public class SummaryFragment extends Fragment {

	static private final String TAG = "SummaryFragment";

	/** Scouting rec */
	private final ScoutingRec rec = ScoutingRec.REC;

	private Bundle args;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg,
		Bundle savedInstanceState)
	{
		args = getArguments();
		View rv = inflater.inflate(rec.browse_detail_res, vg, false);
		Toolbar tb = (Toolbar) rv.findViewById(R.id.detail_toolbar);
		AppCompatActivity act = (AppCompatActivity) getActivity();
		act.setSupportActionBar(tb);
		int team_num = args.getInt(rec.COL_TEAM_NUMBER);
		tb.setTitle("#" + team_num);
		Log.d(TAG, "team #" + team_num);
		initView(rv);
		return rv;
	}

	private void initView(View rv) {
		TextView tv = (TextView) rv.findViewById(R.id.my_obs);
		tv.setText(args.getString("title"));
		for (ScoutingData sd : rec.getAllData())
			sd.init(args, rv);
	}
}
