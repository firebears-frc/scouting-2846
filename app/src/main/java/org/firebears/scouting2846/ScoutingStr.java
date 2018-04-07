/*
 * Copyright  2018  Douglas P Lau
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
import android.view.View;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Scouting string data.
 */
public class ScoutingStr implements ScoutingData {

	/** Column name */
	private final String col;

	@Override
	public String getCol() {
		return col;
	}

	/** Resource ID */
	private final int res;

	/** Create scouting data */
	public ScoutingStr(String c, int r) {
		col = c;
		res = r;
	}

	public String sql() {
		return col + " TEXT NOT NULL";
	}

	public void init(ContentValues cv) {
		cv.put(col, "");
	}

	/** Update ContentValues with data from a cursor */
	@Override
	public void update(ContentValues cv, Cursor c) {
		int i = c.getColumnIndex(col);
		if (i >= 0)
			cv.put(col, c.getString(i));
	}

	/** Update ContentValues with data from view */
	@Override
	public void update(ContentValues cv, Activity rv) {
		if (res > 0) {
			TextView tv = (TextView) rv.findViewById(res);
			cv.put(col, tv.getText().toString().trim());
		}
	}

	@Override
	public boolean hasData(ContentValues cv) {
		String v = cv.getAsString(col);
		return (v != null) && (v.trim().length() > 0);
	}

	/** Update a bundle with data from a cursor */
	@Override
	public void update(Bundle b, Cursor c) {
		int i = c.getColumnIndex(col);
		if (i >= 0)
			b.putString(col, c.getString(i));
	}

	/** Update a JSON object with data from a cursor */
	@Override
	public void update(JSONObject jo, Cursor c) throws JSONException {
		int i = c.getColumnIndex(col);
		if (i >= 0)
			jo.put(col, c.getString(i));
	}

	/** Init content values with JSON data */
	@Override
	public void init(ContentValues cv, JSONObject jo) throws JSONException{
		Object o = jo.get(col);
		if (o != null)
			cv.put(col, o.toString());
	}

	/** Init a view with data from a bundle */
	@Override
	public void init(Bundle b, View rv) {
		if (res > 0) {
			Object v = b.get(col);
			TextView tv = (TextView) rv.findViewById(res);
			tv.setText((v != null) ? v.toString() : "");
		}
	}

	/** Init a view with data from content values */
	@Override
	public void init(ContentValues cv, Activity rv) {
		if (res > 0) {
			String v = cv.getAsString(col);
			TextView tv = (TextView) rv.findViewById(res);
			tv.setText((v != null) ? v : "");
		}
	}

	/** Summarize data */
	@Override
	public void summarize(Bundle b, ArrayList<Bundle> v) {
		StringBuilder sb = new StringBuilder();
		for (Bundle vb : v) {
			String s = vb.getString(col).trim();
			if (ScoutingRec.isMeta(col)) {
				sb.append(s);
				break;
			}
			if (s.length() > 0) {
				if (sb.length() > 0)
					sb.append('\n');
				sb.append("- ");
				sb.append(s);
			}
		}
		b.putString(col, sb.toString());
	}
}
