package com.example.eventme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    Boolean search_type;
    Bundle search_bundle;

    String userInput;
    Bundle stringInput;

    ArrayList<EventBox> sorted_events;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        class MyComparator implements Comparator<String> {

            private final String keyWord;

            MyComparator(String keyWord) {
                this.keyWord = keyWord;
            }

            @Override
            public int compare(String o1, String o2) {

                if(o1.startsWith(keyWord)) {
                    return o2.startsWith(keyWord)? o1.compareTo(o2): -1;
                } else {
                    return o2.startsWith(keyWord)? 1: o1.compareTo(o2);
                }
            }
        }

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
        search_type = search_bundle.getBoolean("searchType");

        stringInput = getIntent().getExtras();
        userInput = stringInput.getString("usersinput");

        Bundle args = intent.getBundleExtra("events+keys");
        sorted_events = (ArrayList<EventBox>) args.getSerializable("events");
        ArrayList<String> keys = (ArrayList<String>) args.getSerializable("keys");

        new RecyclerView_Config().setConfig(mRecyclerView,resultsPage.this, sorted_events, keys);
        Toast.makeText(getApplicationContext(), "Data was loaded", Toast.LENGTH_SHORT).show();

        if (price_filter)
        {
            Collections.sort(sorted_events,new Comparator<EventBox>() {
                public int compare(EventBox s1, EventBox s2) {
                    return s1.getCost() - s2.getCost();
                }
            });
            new RecyclerView_Config().setConfig(mRecyclerView,resultsPage.this, sorted_events, keys);
        }

        if (name_filter)
        {
            if (sorted_events.size() > 0) {
                Collections.sort(sorted_events, new Comparator<EventBox>() {
                    @Override
                    public int compare(EventBox object1, EventBox object2) {
                        return object1.getName().compareTo(object2.getName());
                    }
                });
                new RecyclerView_Config().setConfig(mRecyclerView,resultsPage.this, sorted_events, keys);
            }
        }

        if (date_filter)
        {
            Collections.sort(sorted_events, new Comparator<EventBox>() {
                public int compare(EventBox o1, EventBox o2) {

                    return o1.getDate().compareTo(o2.getDate());
                }
            });
            new RecyclerView_Config().setConfig(mRecyclerView,resultsPage.this, sorted_events, keys);
        }

        if (userInput != null)
        {
            ArrayList<EventBox> newEvents = new ArrayList<>();

            if (!search_type)
            {
                for(EventBox e : sorted_events){
                    if(e.getName() != null && e.getName().toLowerCase().contains(userInput.toLowerCase()))
                    {
                        newEvents.add(e);
                    }
                }

            }
            else
            {
                for(EventBox e : sorted_events){
                    if(e.getName() != null && e.getEvent_Type().toLowerCase().contains(userInput.toLowerCase()))
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