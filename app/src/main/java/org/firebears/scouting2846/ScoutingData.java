/*
 * Copyright  2018  Douglas P Lau
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
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Scouting data.
 */
public interface ScoutingData {

	/** Column name */
	String getCol();

	/** Get SQL for column */
	String sql();

	/** Init content values */
	void init(ContentValues cv);

	/** Init a view with data from content values */
	void init(ContentValues cv, Activity rv);

	/** Init a view with data from a bundle */
	void init(Bundle b, View rv);

	/** Init content values with JSON data */
	void init(ContentValues cv, JSONObject jo) throws JSONException;

	/** Check if content values contains changed data */
	boolean hasData(ContentValues cv);

	/** Update ContentValues with data from a cursor */
	void update(ContentValues cv, Cursor c);

	/** Update ContentValues with data from view */
	void update(ContentValues cv, Activity rv);

	/** Update a bundle with data from a cursor */
	void update(Bundle b, Cursor c);

	/** Update a JSON object with data from a cursor */
	void update(JSONObject jo, Cursor c) throws JSONException;

	/** Summarize data */
	void summarize(Bundle b, ArrayList<Bundle> v);
}
