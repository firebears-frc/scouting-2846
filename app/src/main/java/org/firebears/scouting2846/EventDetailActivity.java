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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

/**
 * An activity representing the details of an FRC event.
 */
public class EventDetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail);
		setSupportActionBar((Toolbar)
			findViewById(R.id.detail_toolbar));
		ActionBar ab = getSupportActionBar();
		if (ab != null)
			ab.setDisplayHomeAsUpEnabled(true);
		if (null == savedInstanceState)
			addFragment();
	}

	/** Add event detail fragment */
	private void addFragment() {
		EventDetailFragment f = new EventDetailFragment();
		f.setArguments(createArguments());
		getSupportFragmentManager().beginTransaction()
			.add(R.id.event_detail_container, f)
			.commit();
	}

	/** Create arguments for fragment */
	private Bundle createArguments() {
		Bundle b = new Bundle();
		b.putInt(FRCEvent.COL_EVENT_ID, getIntent().getIntExtra(
			FRCEvent.COL_EVENT_ID, 0));
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

	public void startEventTeams(View v) {
		Intent intent = new Intent(this, TeamListActivity.class);
		intent.putExtra(FRCEvent.COL_EVENT_ID, getEventId());
		intent.putExtra(FRCEvent.COL_EVENT_KEY, getEventKey());
		intent.putExtra(FRCEvent.COL_SHORT, getEventShortName());
		startActivity(intent);
	}

	private int getEventId() {
		return getIntent().getIntExtra(FRCEvent.COL_EVENT_ID, 0);
	}

	private String getEventKey() {
		return getIntent().getStringExtra(FRCEvent.COL_EVENT_KEY);
	}

	private String getEventShortName() {
		return getIntent().getStringExtra(FRCEvent.COL_SHORT);
	}

	public void startEventMatches(View v) {
		Intent intent = new Intent(this, MatchListActivity.class);
		intent.putExtra(FRCEvent.COL_EVENT_ID, getEventId());
		intent.putExtra(FRCEvent.COL_EVENT_KEY, getEventKey());
		intent.putExtra(FRCEvent.COL_SHORT, getEventShortName());
		startActivity(intent);
	}
}
