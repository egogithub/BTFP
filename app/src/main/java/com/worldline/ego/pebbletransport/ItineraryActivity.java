package com.worldline.ego.pebbletransport;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;


import com.worldline.ego.pebbletransport.adapters.ItineraryListAdapter;
import com.worldline.ego.pebbletransport.async.GetItineraryAsyncTask;
import com.worldline.ego.pebbletransport.helpers.ItineraryStop;
import com.worldline.ego.pebbletransport.interfaces.ItineraryUpdateListener;

import java.util.ArrayList;
import java.util.List;

public class ItineraryActivity extends AppCompatActivity implements ItineraryUpdateListener, AdapterView.OnItemClickListener {
    String lineNumber = "4";
    String direction = "1"; // 1 or 2
    String mode = "B";
    private ListView mainListView;
    private ItineraryListAdapter listAdapter;
    public static final long REFRESH_TIME = 10 * 1000; // 10 seconds
    Context context = ItineraryActivity.this;
    private Handler taskHandler;
    ItineraryUpdateListener itineraryListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        final Intent intent = getIntent();
        if (null != intent) {
            final Bundle extras = intent.getExtras();
            if (null != extras) {
                if (extras.containsKey("id"))
                    this.lineNumber = extras.getString("id");
                if (extras.containsKey("mode")) {
                    this.mode = extras.getString("mode");
                }
                if (extras.containsKey("direction")) {
                    this.direction = String.valueOf(extras.getInt("direction"));
                    Log.d("ItineraryAct", "direction = "+direction);
                }
            }
        }
        List<ItineraryStop> stopslist = new ArrayList();
        mainListView = (ListView) findViewById(R.id.list);
        listAdapter = new ItineraryListAdapter(context, stopslist);
        mainListView.setAdapter(listAdapter);
        mainListView.setOnItemClickListener(this);
        ImageButton refreshButton = (ImageButton)findViewById(R.id.imageButton);
        taskHandler = new Handler();
        refreshButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onRefreshClick(view);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskHandler.postDelayed(refreshTask, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        taskHandler.removeCallbacks(refreshTask);
    }

    public Runnable refreshTask = new Runnable() {
        @Override
        public void run() {
            Log.d("ItineraryActivity", "refreshing with direction = "+direction);
            new GetItineraryAsyncTask(itineraryListener).execute(lineNumber, direction);
            taskHandler.postDelayed(this, REFRESH_TIME);
        }
    };

    public void onRefreshClick(View view) {
        Log.d("ItineraryActivity", "Refreshing list");
        new GetItineraryAsyncTask(this).execute(lineNumber, direction);
    }

    public void onItineraryUpdate(List<ItineraryStop> stopslist) {
        Log.d("ItineraryActivity", "List contains " + stopslist.size() + " entries");
        //dumpList(stopslist);
        listAdapter.updateResults(stopslist);
    }

    private void dumpList(List<ItineraryStop> stops) {
        for (ItineraryStop stop : stops) {
            Log.d(stop.getName(), "is "+stop.isPresent());
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ItineraryActivity", "Clicked on position "+position+", id "+id);
        ItineraryStop stop = (ItineraryStop) parent.getItemAtPosition(position);
        Log.d("ItineraryActivity", "id = "+stop.getId()+", Name = "+stop.getName());
        //TODO: Launch waiting time activity with Line number, Destination sop as title and current stop name
        final Bundle extras = new Bundle();
        extras.putString("line", ""+lineNumber);
        extras.putString("mode", mode);
        extras.putString("dir", direction);
        extras.putInt("stopid", stop.getId());
        final Intent intent = new Intent(this, WaitingTimeActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}