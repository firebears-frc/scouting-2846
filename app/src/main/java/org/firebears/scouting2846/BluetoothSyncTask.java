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

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Task to sync via bluetooth.
 */
public class BluetoothSyncTask extends AsyncTask<Void, Void, Void> {

	static private final String TAG = "BluetoothSyncTask";

	private final BluetoothAdapter adapter =
		BluetoothAdapter.getDefaultAdapter();

	private final EventListActivity context;

	private final String address;

	public BluetoothSyncTask(EventListActivity ctx, String a) {
		context = ctx;
		address = a;
	}

	@Override
	protected Void doInBackground(Void... v) {
		if (adapter != null) {
			try {
				syncWithPeer(context.getContentResolver());
			}
			catch (Exception e) {
				Log.e(TAG, "exception: " + e.getMessage());
			}
		}
		return null;
	}

	private void syncWithPeer(ContentResolver cr) throws IOException,
		JSONException
	{
		JSONArray ja = lookupFinalObservations(cr);
		BluetoothDevice d = adapter.getRemoteDevice(address);
		BluetoothSocket s = d.createRfcommSocketToServiceRecord(
			BluetoothSyncService.OUR_UUID);
		try {
			Log.e(TAG, "connect: " + address);
			s.connect();
			sendRequest(s, ja.toString());
		}
		finally {
			s.close();
		}
	}

	static private final String[] COLS = {
		Scouting2017.COL_SCOUTER, Scouting2017.COL_OBSERVATION,
	};

	private JSONArray lookupFinalObservations(ContentResolver cr)
		throws IOException, JSONException
	{
		Cursor c = cr.query(Scouting2017.CONTENT_URI, COLS, null, null,
			null);
		try {
			if (c != null)
				return lookupFinalObservations(c);
			else
				throw new IOException("No cursor");
		}
		finally {
			if (c != null)
				c.close();
		}
	}

	private JSONArray lookupFinalObservations(Cursor c)
		throws JSONException
	{
		HashMap<Integer, Integer> map =
			new HashMap<Integer, Integer>();
		int cs = c.getColumnIndex(Scouting2017.COL_SCOUTER);
		int co = c.getColumnIndex(Scouting2017.COL_OBSERVATION);
		while (c.moveToNext()) {
			int s = c.getInt(cs);
			int o = c.getInt(co);
			Integer v = map.get(s);
			if (null == v || v < o)
				map.put(s, o);
		}
		return buildArray(map);
	}

	private JSONArray buildArray(HashMap<Integer, Integer> map)
		throws JSONException
	{
		JSONArray ja = new JSONArray();
		for (Integer s : map.keySet()) {
			JSONObject jo = new JSONObject();
			Integer o = map.get(s);
			jo.put(Scouting2017.COL_SCOUTER, s);
			jo.put(Scouting2017.COL_OBSERVATION, o);
			Log.e(TAG, "jo: " + jo);
			ja.put(jo);
		}
		return ja;
	}

	private void sendRequest(BluetoothSocket s, String msg)
		throws IOException
	{
		InputStream is = s.getInputStream();
		OutputStream os = s.getOutputStream();
		Marshaller.writeMsg(os, msg);
	}

	@Override
	protected void onPostExecute(Void v) { }
}
