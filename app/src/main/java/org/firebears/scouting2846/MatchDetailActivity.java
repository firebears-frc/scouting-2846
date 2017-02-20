/*
 * Copyright  2017  Douglas P Lau
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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * An activity representing the details of an FRC match.
 */
public class MatchDetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_detail);
		setSupportActionBar((Toolbar)
			findViewById(R.id.detail_toolbar));
		ActionBar ab = getSupportActionBar();
		if (ab != null)
			ab.setDisplayHomeAsUpEnabled(true);
		if (null == savedInstanceState)
			addFragment();
	}

	/** Add match detail fragment */
	private void addFragment() {
		MatchDetailFragment f = new MatchDetailFragment();
		f.setArguments(createArguments());
		getSupportFragmentManager().beginTransaction()
			.add(R.id.match_detail_container, f)
			.commit();
	}

	/** Create arguments for fragment */
	private Bundle createArguments() {
		Bundle b = new Bundle();
		b.putString(MatchDetailFragment.ARG_MATCH_KEY, getMatchKey());
		return b;
	}

	private String getMatchKey() {
		return getIntent().getStringExtra(
			MatchDetailFragment.ARG_MATCH_KEY);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void startScouting(View v) {
		Button b = (Button) v;
		String team_key = b.getText().toString();
		Intent intent = new Intent(this, Scouting2017Activity.class);
		intent.putExtra(Scouting2017Activity.ARG_MATCH_KEY,
			getMatchKey());
		intent.putExtra(Scouting2017Activity.ARG_TEAM_KEY, team_key);
		startActivity(intent);
	}
}
