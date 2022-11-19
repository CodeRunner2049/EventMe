//old explore page
package com.example.eventme;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CompoundButton;
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
import android.widget.Switch;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
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
    String userInput;
    Switch searchType;
    boolean type_or_search = false;
    //MapPageHelper currentLocation = new MapPageHelper();


    private static final String[] paths = {"item 1", "item 2", "item 3"};

    // TODO: Rename and change types of parameters
    Spinner dropdown;

    //for Action Search
    ListView listView;
//    ArrayList<String> cars = new ArrayList<String>();
    ArrayList<String> event_id = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    public ExplorePage() {
        // Required empty public constructor

    }



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

        SearchView searchView = (SearchView) rootview.findViewById(R.id.searchView);
        searchView.setQueryHint("Search Data here...");

        listView = rootview.findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, event_id);
        listView.setAdapter(arrayAdapter);

        priceButton = rootview.findViewById(R.id.cost);
        searchType = rootview.findViewById(R.id.searchType);
        nameButton = rootview.findViewById(R.id.name);
        dateButton = rootview.findViewById(R.id.date);
        distButton = rootview.findViewById(R.id.Destination);


        FirebaseDatabaseHelper fb = new FirebaseDatabaseHelper();
        fb.readEvents(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {

                Intent intent = new Intent(getContext(), resultsPage.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("events",(Serializable)events);
                bundle.putSerializable("keys",(Serializable)keys);
                intent.putExtra("events+keys", bundle);

                Toast.makeText(getContext(), "Data is uploaded", Toast.LENGTH_SHORT).show();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        userInput = s;
                        intent.putExtra("searchType",type_or_search);
                        intent.putExtra("usersinput", userInput);
                        startActivity(intent);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        arrayAdapter.getFilter().filter(s);
                        return false;
                    }
                });

                searchType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                      @Override
                      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                          type_or_search = isChecked;
                      }
                  });

                priceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        intent.putExtra("price_filter",true);
                        startActivity(intent);

                    }
                });

                nameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        intent.putExtra("name_filter",true);
                        startActivity(intent);

                    }
                });

                dateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        intent.putExtra("date_filter",true);
                        startActivity(intent);

                    }
                });

                distButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        currentLocation.updateLocationUI();
//                        LatLng latlng = currentLocation.getDeviceLocation();
                        intent.putExtra("dist_filter",true);
                        startActivity(intent);

                    }
                });
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
        return rootview;
    }

}