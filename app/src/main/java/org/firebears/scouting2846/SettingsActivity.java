package org.firebears.scouting2846;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class SettingsActivity extends AppCompatActivity {

    private BluetoothSocket mmSocket = null;
    private static final String NAME = "Scouting2846Firebears";
    private static final UUID UUID_A = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private static final int REQUEST_ENABLE_BT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView elv = (ListView) findViewById(R.id.elistv);
        ArrayList<String> arrayList = new ArrayList<String>();
        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.custom_list, arrayList);
        elv.setAdapter(adapter);

        arrayList.add("Scouting Username = Firebears");
        arrayList.add("Show Sync = True");
        arrayList.add("Show Pairing in Sync = True");
    }
}
