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

public class DetailsActivity extends AppCompatActivity {
    private static final String NAME = "Scouting2846Firebears";
    public static String team_name = "0000 - Dean";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(team_name);

        ListView elv = (ListView) findViewById(R.id.elistv);
        ArrayList<String> arrayList = new ArrayList<String>();
        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.custom_list, arrayList);
        elv.setAdapter(adapter);

        arrayList.add("From: Roseville MN US" + null);
        arrayList.add("Additional Info: " + null);
    }
}
