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

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Task to fetch event team list from TBA.
 */
public class FetchEventTeams extends AsyncTask<Void, Void, Void> {

	static private final String TAG = "FetchEventTeams";

	private final TeamListActivity context;

	private final int event_id;
	private final String event_key;

	public FetchEventTeams(TeamListActivity ctx, int eid, String ek) {
		context = ctx;
		event_id = eid;
		event_key = ek;
	}

	@Override
	protected Void doInBackground(Void... v) {
		ContentResolver cr = context.getContentResolver();
		try {
			insertEventTeams(cr, TBAFetcher.fetchTeams(event_key));
		}
		catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
	}

	static private final String[] COLS_KEY = { Team.COL_KEY, };
	static private final String[] COLS_ID = { Team.COL_ID, };

	private void insertEventTeams(ContentResolver cr, String js)
		throws JSONException
	{
		JSONArray ar = new JSONArray(js);
		for (int i = 0; i < ar.length(); i++) {
			JSONObject jo = ar.getJSONObject(i);
			ContentValues cv = Team.parse(jo);
			if (cv != null) {
				insertOrUpdate(cr, cv);
				insertRelation(cr, createRelationVals(cr, cv));
			} else
				Log.e(TAG, "parse " + jo);
		}
	}

	private void insertOrUpdate(ContentResolver cr, ContentValues cv) {
		String key = Team.COL_KEY + "='" +
			cv.getAsString(Team.COL_KEY) + "'";
		Cursor c = cr.query(Team.CONTENT_URI, COLS_KEY, key, null,
			null);
		try {
			if (c != null && c.getCount() > 0)
				cr.update(Team.CONTENT_URI, cv, key, null);
			else
				cr.insert(Team.CONTENT_URI, cv);
		}
		finally {
			if (c != null)
				c.close();
		}
	}

	private int lookupTeamId(ContentResolver cr, ContentValues cv) {
		String key = Team.COL_KEY + "='" +
			cv.getAsString(Team.COL_KEY) + "'";
		Cursor c = cr.query(Team.CONTENT_URI, COLS_ID, key, null,
			null);
		return (c != null) ? lookupTeamId(c) : 0;
	}

	private int lookupTeamId(Cursor c) {
		try {
			c.moveToFirst();
			return c.getInt(c.getColumnIndex(Team.COL_ID));
		}
		finally {
			c.close();
		}
	}

	private ContentValues createRelationVals(ContentResolver cr,
		ContentValues tv)
	{
		int team_id = lookupTeamId(cr, tv);
		ContentValues cv = new ContentValues();
		cv.put(EventTeam.COL_EVENT, event_id);
		cv.put(EventTeam.COL_TEAM, team_id);
		return cv;
	}

	private void insertRelation(ContentResolver cr, ContentValues cv) {
		cr.insert(EventTeam.CONTENT_URI, cv);
	}

	@Override
	protected void onPostExecute(Void v) {
		context.restartLoader();
	}
}
