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
		FRCEvent.COL_START_DATE+" TEXT, " +
		FRCEvent.COL_END_DATE + " TEXT, " +
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
		Team.COL_TEAM_NUMBER +	" INTEGER UNIQUE NOT NULL, " +
		Team.COL_NAME +		" TEXT NOT NULL, " +
		Team.COL_NICKNAME +	" TEXT NOT NULL, " +
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

	/** SQL statement to create event/team relation table */
	static private final String SQL_CREATE_EVENT_TEAMS =
		"CREATE TABLE " + EventTeam.TABLE_NAME + " (" +
		EventTeam.COL_ID +	" INTEGER PRIMARY KEY autoincrement, "+
		EventTeam.COL_EVENT +	" INTEGER NOT NULL, " +
		EventTeam.COL_TEAM +	" INTEGER NOT NULL, " +
		"UNIQUE (" + EventTeam.COL_EVENT + ", " +
		             EventTeam.COL_TEAM + ") ON CONFLICT REPLACE, " +
		"FOREIGN KEY (" + EventTeam.COL_EVENT + ") REFERENCES " +
			FRCEvent.TABLE_NAME + ", " +
		"FOREIGN KEY (" + EventTeam.COL_TEAM + ") REFERENCES " +
			Team.TABLE_NAME + ")";

	/** SQL statement to drop event/teams relation table */
	static private final String SQL_DROP_EVENT_TEAMS =
		"DROP TABLE IF EXISTS " + EventTeam.TABLE_NAME;

	/** SQL statement to create event/team view */
	static private final String SQL_CREATE_ET_VIEW =
		"CREATE VIEW " + EventTeam.VIEW_NAME + " AS SELECT " +
		Team.TABLE_NAME + "." + Team.COL_ID + ", " +
		EventTeam.COL_EVENT + ", " +
		Team.COL_KEY + ", " +
		Team.COL_TEAM_NUMBER + ", " +
		Team.COL_NAME + ", " +
		Team.COL_NICKNAME + ", " +
		Team.COL_WEBSITE + ", " +
		Team.COL_LOCALITY + ", " +
		Team.COL_REGION + ", " +
		Team.COL_COUNTRY + ", " +
		Team.COL_LOCATION + ", " +
		Team.COL_ROOKIE_YEAR + ", " +
		Team.COL_MOTTO +
		" FROM " + Team.TABLE_NAME +
		" JOIN " + EventTeam.TABLE_NAME +
		" ON " + Team.TABLE_NAME + "." + Team.COL_ID +
		" = " + EventTeam.TABLE_NAME + "." + EventTeam.COL_TEAM;

	/** SQL statement to drop event/teams relation view */
	static private final String SQL_DROP_ET_VIEW =
		"DROP VIEW IF EXISTS " + EventTeam.VIEW_NAME;

	/** SQL statement to create match table */
	static private final String SQL_CREATE_MATCHES =
		"CREATE TABLE " + Match.TABLE_NAME + " (" +
		Match.COL_ID +		" INTEGER PRIMARY KEY autoincrement, "+
		Match.COL_KEY +		" TEXT UNIQUE NOT NULL, " +
		Match.COL_EVENT +	" INTEGER NOT NULL, " +
		Match.COL_EVENT_KEY +	" TEXT NOT NULL, " +
		Match.COL_COMP_LEVEL +	" INTEGER NOT NULL, " +
		Match.COL_SET_NUMBER +	" INTEGER, " +
		Match.COL_MATCH_NUMBER +" INTEGER, " +
		Match.COL_ALLIANCES +	" TEXT, " +
		Match.COL_SCORE_BREAKDOWN + " TEXT, " +
		Match.COL_VIDEOS +	" TEXT, " +
		Match.COL_TIME +	" INTEGER, " +
		Match.COL_RED_0 +	" TEXT NOT NULL, " +
		Match.COL_RED_1 +	" TEXT NOT NULL, " +
		Match.COL_RED_2 +	" TEXT NOT NULL, " +
		Match.COL_BLUE_0 +	" TEXT NOT NULL, " +
		Match.COL_BLUE_1 +	" TEXT NOT NULL, " +
		Match.COL_BLUE_2 +	" TEXT NOT NULL, " +
		"FOREIGN KEY (" + Match.COL_EVENT + ") REFERENCES " +
			FRCEvent.TABLE_NAME + ")";

	/** SQL statement to drop match table */
	static private final String SQL_DROP_MATCHES =
		"DROP TABLE IF EXISTS " + Match.TABLE_NAME;

	/** Create our DB helper */
	public OurDbHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onConfigure(SQLiteDatabase db) {
		db.setForeignKeyConstraintsEnabled(true);
	}

	@Override
       	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_EVENTS);
		db.execSQL(SQL_CREATE_TEAMS);
		db.execSQL(SQL_CREATE_EVENT_TEAMS);
		db.execSQL(SQL_CREATE_ET_VIEW);
		db.execSQL(SQL_CREATE_MATCHES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,
		int newVersion)
	{
		db.execSQL(SQL_DROP_MATCHES);
		db.execSQL(SQL_DROP_ET_VIEW);
		db.execSQL(SQL_DROP_EVENT_TEAMS);
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
