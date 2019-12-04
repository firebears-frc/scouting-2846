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

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Scouting integer data.
 */
public class ScoutingBool implements ScoutingData {

	static private final String TAG = "ScoutingBool";

	/** Column name */
	private final String col;

	@Override
	public String getCol() {
		return col;
	}

	/** Resource ID */
	private final int res;

	/** Create scouting integer */
	public ScoutingBool(String c, int r) {
		col = c;
		res = r;
	}

	public String sql() {
		return col + " INTEGER NOT NULL";
	}

	@Override
	public void init(ContentValues cv) {
		cv.put(col, 0);
	}

	/** Update ContentValues with data from a cursor */
	public void update(ContentValues cv, Cursor c) {
		int i = c.getColumnIndex(col);
		if (i >= 0)
			cv.put(col, c.getInt(i));
	}

	@Override
	public void update(ContentValues cv, Activity rv) {
		if (res > 0) {
			Object o = rv.findViewById(res);
			if (o instanceof SwitchCompat) {
				SwitchCompat sw = (SwitchCompat) o;
				boolean c = sw.isChecked();
				cv.put(col, (c) ? 1 : 0);
			}
		}
	}

	@Override
	public boolean hasData(ContentValues cv) {
		Integer v = cv.getAsInteger(col);
		return (v != null) && (v > 0);
	}

	/** Update a bundle with data from a cursor */
	@Override
	public void update(Bundle b, Cursor c) {
		int i = c.getColumnIndex(col);
		if (i >= 0)
			b.putInt(col, c.getInt(i));
	}

	/** Update a JSON object with data from a cursor */
	@Override
	public void update(JSONObject jo, Cursor c) throws JSONException {
		int i = c.getColumnIndex(col);
		if (i >= 0)
			jo.put(col, c.getInt(i));
	}

	/** Init content values with JSON data */
	@Override
	public void init(ContentValues cv, JSONObject jo) throws JSONException{
		Object o = jo.get(col);
		if (o instanceof Integer)
			cv.put(col, (Integer) o);
	}

	/** Init a view with data from a bundle */
	@Override
	public void init(Bundle b, View rv) {
		if (res > 0) {
			Object o = rv.findViewById(res);
			if (o instanceof SwitchCompat) {
				int v = b.getInt(col, 0);
				SwitchCompat sw = (SwitchCompat) o;
				sw.setChecked(v != 0);
			} else if (o instanceof TextView) {
				Object ov = b.get(col);
				TextView tv = (TextView) o;
				if (ov instanceof Integer) {
					int vi = (Integer) ov;
					tv.setText((vi > 0) ? "Yes" : "No");
				} else
					tv.setText(ov.toString());
			} else
				Log.d(TAG, "missing resource for:" + col);
		}
	}

	/** Init a view with data from content values */
	@Override
	public void init(ContentValues cv, Activity rv) {
		if (res > 0) {
			Integer v = cv.getAsInteger(col);
			boolean b = (v != null) && (v != 0);
			Object o = rv.findViewById(res);
			if (o instanceof SwitchCompat) {
				SwitchCompat sw = (SwitchCompat) o;
				sw.setChecked(b);
			} else if (o instanceof TextView) {
				TextView tv = (TextView) o;
				tv.setText(b ? "Yes" : "No");
			}
		}
	}

	/** Summarize data */
	@Override
	public void summarize(Bundle b, ArrayList<Bundle> v) {
		int total = 0;
		for (Bundle vb : v) {
			if (vb.getInt(col, 0) != 0)
				total++;
		}
		int pct = Math.round(100f * total / v.size());
		b.putString(col, "" + pct + "%");
	}
}
