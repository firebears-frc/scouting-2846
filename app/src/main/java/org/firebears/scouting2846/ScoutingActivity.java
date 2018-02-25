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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Scouting activity.
 */
abstract public class ScoutingActivity extends AppCompatActivity {

	/** Content values */
	protected final ContentValues content = new ContentValues();

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void initString(int res, String col) {
		Integer i = content.getAsInteger(col);
		TextView tv = (TextView) findViewById(res);
		tv.setText((i != null) ? Integer.toString(i) : "");
	}

	protected void initSwitch(int res, String col) {
		Integer i = content.getAsInteger(col);
		SwitchCompat sw = (SwitchCompat) findViewById(res);
		sw.setChecked((i != null) && (i != 0));
	}

	protected void initText(int res, String col) {
		String t = content.getAsString(col);
		EditText et = (EditText) findViewById(res);
		et.setText((t != null) ? t : "");
	}

	protected void updateBoolean(int res, String col) {
		SwitchCompat sw = (SwitchCompat) findViewById(res);
		boolean c = sw.isChecked();
		content.put(col, (c) ? 1 : 0);
	}

	protected void updateString(int res, String col) {
		EditText et = (EditText) findViewById(res);
		content.put(col, et.getText().toString());
	}

	protected void updateInt(int res, String col, int p) {
		TextView tv = (TextView) findViewById(res);
		tv.setText(addInt(col, p));
	}

	private String addInt(String col, int p) {
		if (content.containsKey(col)) {
			int v = content.getAsInteger(col) + p;
			if (v < 0)
				v = 0;
			content.put(col, v);
			return Integer.toString(v);
		} else
			return "";
	}
}
