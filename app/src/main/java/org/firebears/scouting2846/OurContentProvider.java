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

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Content provider for DB stuff.
 */
public class OurContentProvider extends ContentProvider {

	/** Base content URI */
	static private final String BASE_URI =
		"content://org.firebears.scouting2846/";

	/** Build a URI */
	static public Uri buildUri(String path) {
		return Uri.parse(BASE_URI + path);
	}

	/** Our DB helper */
	private OurDbHelper dbHelper;

	@Override
	public boolean onCreate() {
		dbHelper = new OurDbHelper(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
		String[] selectionArgs, String sortOrder)
	{
		String suri = uri.toString();
		if (suri.startsWith(FRCEvent.CONTENT_URI.toString())) {
			return queryEvent(projection, selection,
				selectionArgs, sortOrder);
		} else
			return null;
	}

	private Cursor queryEvent(String[] projection, String selection,
		String[] selectionArgs, String sortOrder)
	{
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(FRCEvent.TABLE_NAME);
		return qb.query(dbHelper.getReadableDatabase(), projection,
			selection, selectionArgs, null, null, sortOrder);
	}

	@Override
	public Uri insert(Uri uri, ContentValues cv) {
		String suri = uri.toString();
		if (suri.startsWith(FRCEvent.CONTENT_URI.toString())) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();	
			long _id = db.insert(FRCEvent.TABLE_NAME, null, cv);
			return ContentUris.withAppendedId(uri, _id);
		} else
			return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
		String[] selectionArgs)
	{
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}
}
