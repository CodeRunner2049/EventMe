package com.example.eventme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    String userInput;
    Bundle stringInput;

    ArrayList<EventBox> sorted_events;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

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

//        curr_loc_bundle = getIntent().getExtras();
//        curr_loc_flag = curr_loc_bundle.getBoolean("curr_loc");

        dist_bundle = intent.getExtras();
        dist_filter = date_bundle.getBoolean("dist_filter");

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
            Collections.sort(sorted_events, new Comparator<EventBox>() {
                public int compare(EventBox o1, EventBox o2) {

                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        }

        if (userInput != null)
        {

            //String[] s = {"z", "asxdf", "abasdf", "abcasdf", "b", "bc", "bcd", "c"};

            ArrayList<String> sorted_strings = new ArrayList<>();
            for(EventBox e: sorted_events){
                sorted_strings.add(e.getName());
            }

            String[] s = new String[sorted_strings.size()];
            for(int i = 0; i < sorted_events.size();i++){
                s[i] = sorted_strings.get(i);
            }

            Arrays.sort(s, new MyComparator(userInput));
            System.out.println(Arrays.toString(s));

             new RecyclerView_Config().setConfig(mRecyclerView,resultsPage.this, sorted_events, keys);
            Toast.makeText(getApplicationContext(), "Data was loaded", Toast.LENGTH_SHORT).show();

        }


        //query the database to get event by ID


    }

}