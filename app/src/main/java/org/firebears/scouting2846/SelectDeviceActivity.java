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

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Set;

/**
 * An activity to select a paired bluetooth device.
 */
public class SelectDeviceActivity extends Activity {

	static public final String DEVICE_ADDRESS = "device_address";
	static public final String ERROR_CODE = "error_code";

	/** Map of device names to addresses */
	private final HashMap<String, BluetoothDevice> devices =
		new HashMap<String, BluetoothDevice>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setResult(Activity.RESULT_CANCELED);
		setContentView(R.layout.activity_select_device);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			R.layout.device_name);
		ListView lv = (ListView) findViewById(R.id.device_list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(item_listener);

		BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
		if (null == ba) {
			cancelActivity(R.string.bt_required);
			return;
		}
		if (ba.isEnabled()) {
			Set<BluetoothDevice> bonded = ba.getBondedDevices();
			if (bonded.isEmpty()) {
				cancelActivity(R.string.bt_no_devices);
				return;
			}
			for (BluetoothDevice device : bonded) {
				String n = device.getName();
				adapter.add(n);
				devices.put(n, device);
			}
		} else
			cancelActivity(R.string.bt_disabled);
	}

	private void cancelActivity(int res) {
		Intent intent = new Intent();
		intent.putExtra(ERROR_CODE, res);
		setResult(RESULT_CANCELED, intent);
		finish();
	}

	private AdapterView.OnItemClickListener item_listener =
		new AdapterView.OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> av, View v, int arg2,
			long arg3)
		{
			String name = ((TextView) v).getText().toString();
			BluetoothDevice d = devices.get(name);
			if (d != null) {
				Intent intent = new Intent();
				intent.putExtra(DEVICE_ADDRESS,d.getAddress());
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		}
	};
}
