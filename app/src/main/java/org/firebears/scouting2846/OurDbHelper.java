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

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Our DB helper.
 */
public class OurDbHelper extends SQLiteOpenHelper {
	static public final int DATABASE_VERSION = 1;
	static public final String DATABASE_NAME = "Scouting.db";

	/** SQL statement to create event table */
	static private final String SQL_CREATE_EVENTS =
		"CREATE TABLE " + FRCEvent.TABLE_NAME + " (" +
		FRCEvent.COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
		FRCEvent.COL_KEY + " TEXT UNIQUE, " +
		FRCEvent.COL_NAME + " TEXT NOT NULL, " +
		FRCEvent.COL_SHORT + " TEXT, " +
		FRCEvent.COL_OFFICIAL + " INTEGER NOT NULL, " +
		FRCEvent.COL_EV_CODE + " TEXT NOT NULL, " +
		FRCEvent.COL_EV_TYPE + " INTEGER NOT NULL, " +
		FRCEvent.COL_DISTRICT + " INTEGER NOT NULL, " +
		FRCEvent.COL_YEAR + " INTEGER NOT NULL, " +
		FRCEvent.COL_WEEK + " INTEGER, " +
		FRCEvent.COL_LOCATION + " TEXT NOT NULL, " +
		FRCEvent.COL_VENUE_ADDRESS + " TEXT, " +
		FRCEvent.COL_TIMEZONE + " TEXT, " +
		FRCEvent.COL_WEBSITE + " TEXT)";

	/** SQL statement to drop event table */
	static private final String SQL_DROP_EVENTS =
		"DROP TABLE IF EXISTS " + FRCEvent.TABLE_NAME;

	/** SQL statement to insert practice event */
	static private final String SQL_INSERT_EVENTS =
		"INSERT INTO " + FRCEvent.TABLE_NAME + " (" +
		FRCEvent.COL_KEY + ", " +
		FRCEvent.COL_NAME + ", " +
		FRCEvent.COL_SHORT + ", " +
		FRCEvent.COL_OFFICIAL + ", " +
		FRCEvent.COL_EV_CODE + ", " +
		FRCEvent.COL_EV_TYPE + ", " +
		FRCEvent.COL_DISTRICT + ", " +
		FRCEvent.COL_YEAR + ", " +
		FRCEvent.COL_WEEK + ", " +
		FRCEvent.COL_LOCATION + ", " +
		FRCEvent.COL_VENUE_ADDRESS + ", " +
		FRCEvent.COL_TIMEZONE + ", " +
		FRCEvent.COL_WEBSITE + ") VALUES (" +
		"'practice',' Practice Event','Practice','0','1','1'," +
		"'DISTRICT','2017','0','LOCATION','VENUE','TZ','WEBSITE')";

	/** Create our DB helper */
	public OurDbHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
       	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_EVENTS);
		db.execSQL(SQL_INSERT_EVENTS);
		// FIXME: move somewhere else
		try {
			insertEvents(db, TBAFetcher.fetchEvents());
		}
		catch (Exception e) {
			Log.e("OurDbHelper", "exception " + e);
		}
	}

	static private void insertEvents(SQLiteDatabase db,
		String js) throws JSONException
	{
//		ContentResolver cr = ctx.getContentResolver();
		JSONArray ar = new JSONArray(js);
		for (int i = 0; i < ar.length(); i++) {
			ContentValues cv = FRCEvent.parse(ar.getJSONObject(i));
			if (cv != null) {
				db.insert(FRCEvent.TABLE_NAME, null, cv);
//				cr.insert(FRCEvent.CONTENT_URI, cv);
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,
		int newVersion)
	{
		db.execSQL(SQL_DROP_EVENTS);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion,
		int newVersion)
	{
		onUpgrade(db, oldVersion, newVersion);
	}
}
