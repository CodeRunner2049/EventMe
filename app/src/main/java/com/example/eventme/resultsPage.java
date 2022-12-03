package com.example.eventme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

    Boolean name_filter;
    Bundle name_bundle;

    Boolean date_filter;
    Bundle date_bundle;

    Boolean curr_loc_flag;
    Bundle curr_loc_bundle;

    Boolean dist_filter;
    Bundle dist_bundle;

    String search_type;
    Bundle search_bundle;

    String userInput;
    Bundle stringInput;

    List<EventBox> sorted_events;
    private RecyclerView mRecyclerView;
    FirebaseDatabaseHelper fb = new FirebaseDatabaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);


        Intent intent = getIntent();

        //read in array of string (event ID)
//        price_filter = getIntent().getBooleanExtra("price_filter",true);
        price_bundle = intent.getExtras();
        price_filter = price_bundle.getBoolean("price_filter");

        name_bundle = intent.getExtras();
        name_filter = name_bundle.getBoolean("name_filter");

        date_bundle = intent.getExtras();
        date_filter = date_bundle.getBoolean("date_filter");

        dist_bundle = intent.getExtras();
        dist_filter = date_bundle.getBoolean("dist_filter");

        search_bundle = intent.getExtras();
        search_type = search_bundle.getString("searchType");

        stringInput = getIntent().getExtras();
        userInput = stringInput.getString("usersinput");

        Bundle args = intent.getBundleExtra("events+keys");
        sorted_events = (List<EventBox>) args.getSerializable("events");
        ArrayList<String> keys = (ArrayList<String>) args.getSerializable("keys");

        new RecyclerView_Config().setConfig(mRecyclerView,resultsPage.this, sorted_events, keys);
        Toast.makeText(getApplicationContext(), "Data was loaded", Toast.LENGTH_SHORT).show();

        if (price_filter)
        {
            sorted_events = fb.sortEventsByPrice(sorted_events);
            new RecyclerView_Config().setConfig(mRecyclerView,resultsPage.this, sorted_events, keys);
        }

        if (name_filter)
        {
            if (sorted_events.size() > 0) {
                sorted_events = fb.sortEventsByName(sorted_events);
                new RecyclerView_Config().setConfig(mRecyclerView,resultsPage.this, sorted_events, keys);
            }
        }

        if (date_filter)
        {
            sorted_events = fb.sortEventsByDate(sorted_events);
            new RecyclerView_Config().setConfig(mRecyclerView,resultsPage.this, sorted_events, keys);
        }

        if (userInput != null)
        {
            List<EventBox> newEvents = new ArrayList<>();

            Toast.makeText(getApplicationContext(), "search type is " + search_type, Toast.LENGTH_SHORT).show();

            if (search_type.equals("name"))
            {
                newEvents = fb.searchEventsByName(sorted_events, userInput);
            }
            else if (search_type.equals( "event_type"))
            {
                for(EventBox e : sorted_events){
                    if(e.getName() != null && e.getEvent_Type().toLowerCase().contains(userInput.toLowerCase()))
                    {
                        newEvents.add(e);
                    }
                }
            }
            else if (search_type.equals("location"))
            {
                for(EventBox e : sorted_events){
                    if(e.getName() != null && e.getAddress().toLowerCase().contains(userInput.toLowerCase()))
                    {
                        newEvents.add(e);
                    }
                }
            }
            else if (search_type.equals("sponsor"))
            {
                for(EventBox e : sorted_events){
                    if(e.getName() != null && e.getSponsor().toLowerCase().contains(userInput.toLowerCase()))
                    {
                        newEvents.add(e);
                    }
                }
            }

            new RecyclerView_Config().setConfig(mRecyclerView,resultsPage.this, newEvents, keys);
            Toast.makeText(getApplicationContext(), "Applied Query", Toast.LENGTH_SHORT).show();
        }


        //query the database to get event by ID
    }

}