//old explore page
package com.example.eventme;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
// * Use the {@link ExplorePage#} factory method to
 * create an instance of this fragment.
 */
public class ExplorePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Spinner spinner;
    Button searchButton;

    Button priceButton;
    Button distButton;
//    Button event_typeButton;
    Button nameButton;
    Button dateButton;


    private static final String[] paths = {"item 1", "item 2", "item 3"};

    // TODO: Rename and change types of parameters
    Spinner dropdown;

    //for Action Search
    ListView listView;
//    ArrayList<String> cars = new ArrayList<String>();
    ArrayList<String> event_id = new ArrayList<String>();



//
//    "James", "Joe", "Alex", "Tony"};

    ArrayAdapter<String> arrayAdapter;

    public ExplorePage() {
        // Required empty public constructor

    }

//    SearchView searchView;
//    RecyclerView recyclerView;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        FirebaseDatabaseHelper fb = new FirebaseDatabaseHelper();
        fb.readEvents(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                Toast.makeText(getActivity().getApplicationContext(), "Data was loaded", Toast.LENGTH_SHORT).show();

                for (EventBox e: events)
                {
                    event_id.add(e.getId());
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_explore_page, container, false);

        searchButton = rootview.findViewById(R.id.query);

        SearchView searchView = (SearchView) rootview.findViewById(R.id.searchView);
        searchView.setQueryHint("Search Data here...");

        listView = rootview.findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, event_id);
        listView.setAdapter(arrayAdapter);

        priceButton = rootview.findViewById(R.id.cost);

        nameButton = rootview.findViewById(R.id.name);
        dateButton = rootview.findViewById(R.id.date);
        distButton = rootview.findViewById(R.id.Destination);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cars.add("Volvo");
//                cars.add("BMW");
//                cars.add("Ford");
//                cars.add("Mazda");

//                for (int i= 0; i< cars.size(); ++i)
//                {
                Intent intent = new Intent(getContext(), resultsPage.class);
                intent.putExtra("name_filter", event_id);
                startActivity(intent);
//                }

            }
        });

        priceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), resultsPage.class);
                intent.putExtra("price_filter",true);
                startActivity(intent);

            }
        });

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), resultsPage.class);
                intent.putExtra("name_filter",true);
                startActivity(intent);

            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), resultsPage.class);
                intent.putExtra("date_filter",true);
                startActivity(intent);

            }
        });

        distButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), resultsPage.class);
                intent.putExtra("dist_filter",true);
                startActivity(intent);

            }
        });



//        ExpandableListView expandableListView = rootview.findViewById(R.id.expandableListView);
//        HashMap<String, List<String>> item = new HashMap<>();
//        ArrayList<String> Groups = new ArrayList<>();
//        Groups.add("Lowest Price");
//        Groups.add("Highest Price");
//        Groups.add("Earliest Date");
//        Groups.add("Latest Date");
//        Groups.add("Alphabetically");
//        Groups.add("Type of Event");
//        item.put("Filter", Groups);
//        MyExpandableListAdapter adapter = new MyExpandableListAdapter(item);
//        expandableListView.setAdapter(adapter);

//        View view = inflater.inflate(R.layout.fragment_explore_page, container, false);
//        Spinner spinner = view.findViewById(R.id.spinner1);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, courses);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);


        return rootview;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // TODO Add your menu entries here
//
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.layout.fragment_explore_page, menu);
//
//        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem menuItem) {
////                Toast.makeText(ExplorePage.this, "",Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), "Search is Expanded", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
//                Toast.makeText(getContext(), "Search is Collapsed", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        };
//        menu.findItem(R.layout.fragment_explore_page).setOnActionExpandListener(onActionExpandListener);
//
//    }





}