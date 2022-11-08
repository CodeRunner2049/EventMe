package com.example.eventme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        name_bundle = getIntent().getExtras();
        name_filter = name_bundle.getBoolean("name_filter");

        date_bundle = getIntent().getExtras();
        date_filter = date_bundle.getBoolean("date_filter");

//        curr_loc_bundle = getIntent().getExtras();
//        curr_loc_flag = curr_loc_bundle.getBoolean("curr_loc");

        dist_bundle = getIntent().getExtras();
        dist_filter = date_bundle.getBoolean("dist_filter");




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

                if (name_filter)
                {
                    if (sorted_events.size() > 0) {
                        Collections.sort(sorted_events, new Comparator<EventBox>() {
                            @Override
                            public int compare(EventBox object1, EventBox object2) {
                                return object1.getName().compareTo(object2.getName());
                            }
                        });
                    }
                }

                if (date_filter)
                {
//                    Collections.sort(sorted_events, new Comparator<EventBox>() {
//                        @Override
//                        public int compare(EventBox o1, EventBox o2) {
//                            try {
//                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                                return Long.valueOf(format.parse(o1.getDate()).getTime()).compareTo(format.parse(o2.getDate()).getTime());
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                return 0;
//                            }
//                        }
//                    });

                    Collections.sort(sorted_events, new Comparator<EventBox>() {
                        public int compare(EventBox o1, EventBox o2) {

                            return o1.getDate().compareTo(o2.getDate());
                        }
                    });
                }

//                if (curr_loc_flag && dist_filter) //current location and destination location found
//                {
//
//                }







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