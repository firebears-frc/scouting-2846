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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Our DB helper.
 */
public class OurDbHelper extends SQLiteOpenHelper {
	static public final int DATABASE_VERSION = 1;
	static public final String DATABASE_NAME = "Scouting.db";

	/** SQL statement to create event table */
	static private final String SQL_CREATE_EVENTS =
		"CREATE TABLE " + FRCEvent.TABLE_NAME + " (" +
		FRCEvent.COL_ID +	" INTEGER PRIMARY KEY autoincrement, "+
		FRCEvent.COL_KEY +	" TEXT UNIQUE NOT NULL, " +
		FRCEvent.COL_NAME +	" TEXT NOT NULL, " +
		FRCEvent.COL_SHORT +	" TEXT, " +
		FRCEvent.COL_OFFICIAL +	" INTEGER NOT NULL, " +
		FRCEvent.COL_EV_CODE +	" TEXT NOT NULL, " +
		FRCEvent.COL_EV_TYPE +	" INTEGER NOT NULL, " +
		FRCEvent.COL_DISTRICT +	" INTEGER NOT NULL, " +
		FRCEvent.COL_YEAR +	" INTEGER NOT NULL, " +
		FRCEvent.COL_WEEK +	" INTEGER, " +
		FRCEvent.COL_LOCATION +	" TEXT NOT NULL, " +
		FRCEvent.COL_VENUE_ADDRESS + " TEXT, " +
		FRCEvent.COL_TIMEZONE +	" TEXT, " +
		FRCEvent.COL_WEBSITE +	" TEXT)";

	/** SQL statement to drop event table */
	static private final String SQL_DROP_EVENTS =
		"DROP TABLE IF EXISTS " + FRCEvent.TABLE_NAME;

	/** SQL statement to create team table */
	static private final String SQL_CREATE_TEAMS =
		"CREATE TABLE " + Team.TABLE_NAME + " (" +
		Team.COL_ID +		" INTEGER PRIMARY KEY autoincrement, "+
		Team.COL_KEY +		" TEXT UNIQUE NOT NULL, " +
		Team.COL_TEAM_NUMBER +	" INTEGER NOT NULL, " +
		Team.COL_NAME +		" TEXT NOT NULL, " +
		Team.COL_NICKNAME +	" TEXT, " +
		Team.COL_WEBSITE +	" TEXT, " +
		Team.COL_LOCALITY +	" TEXT NOT NULL, " +
		Team.COL_REGION +	" TEXT, " +
		Team.COL_COUNTRY +	" TEXT, " +
		Team.COL_LOCATION +	" TEXT NOT NULL, " +
		Team.COL_ROOKIE_YEAR +	" INTEGER NOT NULL, " +
		Team.COL_MOTTO +	" TEXT)";

	/** SQL statement to drop team table */
	static private final String SQL_DROP_TEAMS =
		"DROP TABLE IF EXISTS " + Team.TABLE_NAME;

	/** Create our DB helper */
	public OurDbHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
       	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_EVENTS);
		db.execSQL(SQL_CREATE_TEAMS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,
		int newVersion)
	{
		db.execSQL(SQL_DROP_TEAMS);
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
