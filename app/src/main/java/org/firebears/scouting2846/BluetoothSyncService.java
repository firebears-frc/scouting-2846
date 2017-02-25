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

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Service for accepting bluetooth connections to sync data.
 */
public class BluetoothSyncService extends Service {

	static private final String TAG = "BluetoothSyncService";

	static private final String SDP_NAME = "BluetoothSync";

	static public final UUID OUR_UUID =
		UUID.fromString("b9a8fc97-8a4b-4481-b634-856140ed1b7d");

	private final BluetoothAdapter adapter =
		BluetoothAdapter.getDefaultAdapter();

	private final BroadcastReceiver receiver =
		new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context ctx, Intent intent) {
			final String a = intent.getAction();
			if (a.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				final int state = intent.getIntExtra(
					BluetoothAdapter.EXTRA_STATE,
					BluetoothAdapter.ERROR);
				Log.d(TAG, "bt state: " + state);
				if (BluetoothAdapter.STATE_ON == state)
					notifyEnable();
			}
		}
	};

	private final Thread thread = new Thread() {
		public void run() {
			doRun();
		}
	};

	private boolean running = true;

	private void doRun() {
		Log.d(TAG, "enabled: " + adapter.isEnabled());
		while (running) {
			if (adapter.isEnabled())
				doConnection();
			else
				waitForEnable();
		}
	}

	private synchronized void waitForEnable() {
		try {
			wait();
		}
		catch (InterruptedException e) { }
	}

	private synchronized void notifyEnable() {
		notify();
	}

	private void doConnection() {
		try {
			doCreateAndAccept();
		}
		catch (IOException e) {
			Log.e(TAG, "exception: " + e.getMessage());
		}
	}

	private void doCreateAndAccept() throws IOException {
		BluetoothServerSocket server_sock =
			adapter.listenUsingRfcommWithServiceRecord(
			SDP_NAME, OUR_UUID);
		Log.d(TAG, "got bluetooth server socket");
		try {
			BluetoothSocket s = server_sock.accept();
			Log.d(TAG, "accepted bluetooth connection");
			try {
				doHandleConnection(s);
			}
			finally {
				s.close();
			}
		}
		finally {
			server_sock.close();
		}
	}

	private void doHandleConnection(BluetoothSocket s) throws IOException {
		InputStream is = s.getInputStream();
		OutputStream os = s.getOutputStream();
		Log.d(TAG, "recv: " + Marshaller.readMsg(is));
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "created");
		registerReceiver(receiver, new IntentFilter(
			BluetoothAdapter.ACTION_STATE_CHANGED));
		if (adapter != null)
			thread.start();
		else {
			Log.e(TAG, "bluetooth adapter not found!");
			stopSelf();
		}
	}

	@Override
	public int onStartCommand(Intent i, int flags, int startId) {
		Log.d(TAG, "start command");
		return Service.START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "destroy");
		unregisterReceiver(receiver);
		running = false;
		notifyEnable();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
