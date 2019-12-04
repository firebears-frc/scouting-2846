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
		b.putInt(Team.COL_TEAM_NUMBER, getTeamNumber());
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

	private int getTeamNumber() {
		return getIntent().getIntExtra(Team.COL_TEAM_NUMBER, 0);
	}

	public void startScouting(View v) {
		Intent intent = new Intent(this, ScoutingActivity.class);
		intent.putExtra(ScoutingRec.COL_MATCH_KEY, getMatch());
		intent.putExtra(Team.COL_TEAM_NUMBER, getTeamNumber());
		startActivity(intent);
	}

	private String getMatch() {
		TeamDetailFragment f = getFrag();
		return (f != null) ? f.getMatch() : getString(R.string.pit);
	}

	private TeamDetailFragment getFrag() {
		Object f = getSupportFragmentManager().findFragmentById(
			R.id.team_detail_container);
		if (f instanceof TeamDetailFragment)
			return (TeamDetailFragment) f;
		else
			return null;
	}

	public void startBrowse(View v) {
		Intent intent = new Intent(this, BrowseActivity.class);
		intent.putExtra(Team.COL_TEAM_NUMBER, getTeamNumber());
		startActivity(intent);
	}
}
