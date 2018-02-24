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
package org.firebears.scouting2846.y2017;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.AsyncTask;
import org.firebears.scouting2846.Param;

/**
 * Task to store scouting observation.
 */
public class StoreObservation extends AsyncTask<Void, Void, Void> {

	/** WHERE clause for updating observation number */
	static private final String WHERE_OBS =
		Param.COL_NAME + "='" + Param.ROW_OBSERVATION + "'";

	private final Scouting2017Activity context;

	private final ContentValues content;

	public StoreObservation(Scouting2017Activity ctx, ContentValues cv) {
		context = ctx;
		content = cv;
	}

	@Override
	protected Void doInBackground(Void... v) {
		insert(context.getContentResolver());
		return null;
	}

	private void insert(ContentResolver cr) {
		updateObservation(cr, content.getAsInteger(
			Param.ROW_OBSERVATION));
		cr.insert(Scouting2017.CONTENT_URI, content);
	}

	private void updateObservation(ContentResolver cr, Integer obs) {
		ContentValues pv = new ContentValues();
		pv.put(Param.COL_VALUE, obs);
		cr.update(Param.CONTENT_URI, pv, WHERE_OBS, null);
	}

	@Override
	protected void onPostExecute(Void v) { }
}
