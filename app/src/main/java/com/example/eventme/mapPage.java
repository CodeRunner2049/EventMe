package com.example.eventme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class mapPage extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GoogleMap googleMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        //get event locations on the map


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            FirebaseDatabaseHelper fb = new FirebaseDatabaseHelper();
            fb.readEvents(new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                    Toast.makeText(getActivity().getApplicationContext(), "Data was loaded", Toast.LENGTH_SHORT).show();

                    for(int i = 0 ; i < events.size() ; i++)
                    {
                        latlngs.add(new LatLng(events.get(i).getLatitude(), events.get(i).getLongitude()));
    //                    LatLng sydney = new LatLng(events.get(i).getLatitude(), events.get(i).getLongitude());
    //                    googleMap.addMarker(new MarkerOptions().position(sydney).title(" "));
    //                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    }
    //
                    for (LatLng point : latlngs) {
                        googleMap.addMarker(new MarkerOptions().position(point).title("Marker in Sydney"));
                    }

                }
                @Override
                public void DataIsInserted() {}

                @Override
                public void DataIsUpdated() {}

                @Override
                public void DataIsDeleted() {}
            });

            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_map_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








    }










}