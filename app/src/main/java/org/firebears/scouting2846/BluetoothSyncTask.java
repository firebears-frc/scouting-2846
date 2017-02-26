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
import android.os.AsyncTask;
import android.util.Log;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.json.JSONException;

/**
 * Task to sync via bluetooth.
 */
public class BluetoothSyncTask extends AsyncTask<Void, Void, Void> {

	static private final String TAG = "BluetoothSyncTask";

	private final BluetoothAdapter adapter =
		BluetoothAdapter.getDefaultAdapter();

	private final EventListActivity context;

	private final String address;

	private boolean failed = false;

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
				failed = true;
			}
		}
		return null;
	}

	private void syncWithPeer(ContentResolver cr) throws IOException,
		JSONException
	{
		String msg = Marshaller.lookupFinalObservations(cr);
		BluetoothDevice d = adapter.getRemoteDevice(address);
		BluetoothSocket s = d.createRfcommSocketToServiceRecord(
			BluetoothSyncService.OUR_UUID);
		try {
			Log.e(TAG, "connect: " + address);
			s.connect();
			sendRequest(s, msg);
		}
		finally {
			s.close();
		}
	}

	private void sendRequest(BluetoothSocket s, String msg)
		throws IOException
	{
		InputStream is = s.getInputStream();
		OutputStream os = s.getOutputStream();
		Marshaller.writeMsg(os, msg);
		String obs = Marshaller.readMsg(is, 10000);
		Log.d(TAG, "recv: (" + obs.length() + ") " + obs);
	}

	@Override
	protected void onPostExecute(Void v) {
		if (failed)
			context.showSnack(R.string.sync_failed);
		else
			context.showSnack(R.string.sync_complete);
	}
}
