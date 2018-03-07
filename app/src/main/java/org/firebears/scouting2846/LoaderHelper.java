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

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

/**
 * Loader helper.
 */
abstract public class LoaderHelper implements LoaderCallbacks<Cursor> {

	protected final Context context;

	/** Create loader helper */
	protected LoaderHelper(Context c) {
		context = c;
	}

	abstract public int getId();

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle b) {
		return (getId() == id) ? createLoader(b) : null;
	}

	/** Create a loader */
	abstract protected Loader<Cursor> createLoader(Bundle b);

	/** Called when fully loaded */
	abstract protected void onLoaded(Cursor c);

	@Override
	public void onLoaderReset(Loader<Cursor> loader) { }
}
