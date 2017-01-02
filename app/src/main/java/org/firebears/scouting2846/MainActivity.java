package org.firebears.scouting2846;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public void detailer() {
        startActivity( new Intent(this, DetailsActivity.class) );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ListView elv = (ListView) findViewById(R.id.mlistv);
        ArrayList<String> arrayList = new ArrayList<String>();
        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.custom_list, arrayList);
        elv.setAdapter(adapter);

        arrayList.add("2846 - Firebears");
        elv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailsActivity.team_name = (String) parent.getItemAtPosition(position);
                detailer();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            startActivity( new Intent(this, SettingsActivity.class) );
            return true;
        }else if(id == R.id.sync) {
            startActivity( new Intent(this, SyncActivity.class) );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
