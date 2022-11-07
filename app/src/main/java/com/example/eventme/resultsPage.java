package com.example.eventme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class resultsPage extends AppCompatActivity {

    ArrayList<String> valid_events = new ArrayList<String>();
    Boolean price_filter;
    Bundle price_bundle;
    List<EventBox> sorted_events;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        Intent intent = getIntent();

        //read in array of string (event ID)
        ArrayList<String> read_events = new ArrayList<String>();
        read_events = getIntent().getStringArrayListExtra("name_filter");
//        price_filter = getIntent().getBooleanExtra("price_filter",true);
        price_bundle = getIntent().getExtras();
        price_filter = price_bundle.getBoolean("price_filter");




        //query the database to get event by ID
        FirebaseDatabaseHelper fb = new FirebaseDatabaseHelper();
        fb.readEvents(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView,resultsPage.this, events,keys);
                Toast.makeText(getApplicationContext(), "Data was loaded", Toast.LENGTH_SHORT).show();

                //loop through events
                    //if price filter is true
                        //put into temp array
                //sort temp array
                //display
                //style in xml
                sorted_events = events;

                if (price_filter)
                {
                    Collections.sort(sorted_events,new Comparator<EventBox>() {
                        public int compare(EventBox s1, EventBox s2) {
                            return s1.getCost() - s2.getCost();
                        }
                    });
                }







            }

            @Override
            public void DataIsInserted() {
            }

            @Override
            public void DataIsUpdated() {
            }

            @Override
            public void DataIsDeleted() {
            }
        });






        //if true
        //then show on dropdown

    }
}