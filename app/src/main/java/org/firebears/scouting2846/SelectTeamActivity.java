/*
 * Copyright  2019  Douglas P Lau
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
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * An activity to select a team.
 */
public class SelectTeamActivity extends Activity {

	static public final String TEAM_NUMBER = "team_number";

	private final EditText.OnEditorActionListener listener =
		new EditText.OnEditorActionListener()
	{
		@Override
		public boolean onEditorAction(TextView tv, int action,
			KeyEvent ke)
		{
			if (action == EditorInfo.IME_ACTION_SEARCH ||
			    action == EditorInfo.IME_ACTION_DONE ||
			    ke != null &&
			    ke.getAction() == KeyEvent.ACTION_DOWN &&
			    ke.getKeyCode() == KeyEvent.KEYCODE_ENTER)
			{
				if (ke == null || !ke.isShiftPressed()) {
					onSelected(tv.getText().toString());
					return true;
				}
			}
			return false;
		}
	};

	private void onSelected(String team) {
		try {
			int team_number = Integer.parseInt(team);
			Intent intent = new Intent();
			intent.putExtra(TEAM_NUMBER, team_number);
			setResult(Activity.RESULT_OK, intent);
		} catch (NumberFormatException e) {
			Intent intent = new Intent();
			intent.putExtra(TeamListActivity.ERROR_CODE,
				R.string.invalid_number);
			setResult(RESULT_CANCELED, intent);
		}
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_team);
		EditText et = (EditText) findViewById(R.id.team_number);
		et.setOnEditorActionListener(listener);
	}
}
