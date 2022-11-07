//old explore page
package com.example.eventme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExplorePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExplorePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner spinner;
    private static final String[] paths = {"item 1", "item 2", "item 3"};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner dropdown;

    public ExplorePage() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExplorePage.
     */
    // TODO: Rename and change types and number of parameters
    public static ExplorePage newInstance(String param1, String param2) {
        ExplorePage fragment = new ExplorePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabaseHelper fb = new FirebaseDatabaseHelper();
        fb.readEvents(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                Toast.makeText(getActivity().getApplicationContext(), "Data was loaded", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated() {}

            @Override
            public void DataIsDeleted() {}
        });


    }
    String[] courses = { "C", "Data structures",
            "Interview prep", "Algorithms",
            "DSA with java", "OS" };
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootview = inflater.inflate(R.layout.fragment_profile_page, container, false);
//        View view = inflater.inflate(R.layout.fragment_explore_page, container, false);
//        Spinner spinner = view.findViewById(R.id.spinner1);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, courses);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);
//
//
//        return inflater.inflate(R.layout.fragment_explore_page, container, false);
//    }




}