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

/**
 * An activity representing the details of an FRC team.
 */
public class TeamDetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_detail);
		setSupportActionBar((Toolbar)
			findViewById(R.id.detail_toolbar));
		ActionBar ab = getSupportActionBar();
		if (ab != null)
			ab.setDisplayHomeAsUpEnabled(true);
		if (null == savedInstanceState)
			addFragment();
	}

	/** Add team detail fragment */
	private void addFragment() {
		TeamDetailFragment f = new TeamDetailFragment();
		f.setArguments(createArguments());
		getSupportFragmentManager().beginTransaction()
			.add(R.id.team_detail_container, f)
			.commit();
	}

	/** Create arguments for fragment */
	private Bundle createArguments() {
		Bundle b = new Bundle();
		b.putString(TeamDetailFragment.ARG_TEAM_KEY, getTeamKey());
		return b;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String getTeamKey() {
		return getIntent().getStringExtra(
			TeamDetailFragment.ARG_TEAM_KEY);
	}

	public void startScouting(View v) {
		Intent intent = new Intent(this, Scouting2017Activity.class);
		intent.putExtra(Scouting2017Activity.ARG_MATCH_KEY, "");
		intent.putExtra(Scouting2017Activity.ARG_TEAM_KEY,
			getTeamKey());
		startActivity(intent);
	}

	public void startBrowse(View v) {
		Intent intent = new Intent(this, Browse2017Activity.class);
		intent.putExtra(Browse2017Activity.ARG_TEAM_KEY,
			getTeamKey());
		startActivity(intent);
	}
}
