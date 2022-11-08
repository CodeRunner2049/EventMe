package com.example.eventme;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eventme.databinding.FragmentMapPageBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class mapPage extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GoogleMap googleMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private ArrayList<Marker> markers = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;
    private Location currLocation;
    FragmentMapPageBinding binding;
    SupportMapFragment mapFragment;
    private static final int REQUEST_CODE = 101;



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
        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {

            FirebaseDatabaseHelper fb = new FirebaseDatabaseHelper();
            fb.readEvents(new FirebaseDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                    Toast.makeText(getActivity().getApplicationContext(), "Data was loaded", Toast.LENGTH_SHORT).show();

                    for (EventBox event : events) {
                        LatLng latlng = new LatLng(event.getLatitude(), event.getLongitude());
                        Marker marker = googleMap.addMarker(new MarkerOptions().position(latlng).title(event.getId()));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                        markers.add(marker);
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


            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {

                    String markertitle = marker.getTitle();
                    Intent i = new Intent(getContext(), DetailsActivity.class);
                    i.putExtra("eventId", markertitle);
                    startActivity(i);

                    return false;
                }
            });

//            Location loci = fusedLocationClient.getLastLocation().getResult();
//            LatLng currLoci = new LatLng(loci.getLatitude(), loci.getLongitude());
//            googleMap.addMarker(new MarkerOptions().position(currLoci).title("Your current location!"));

            fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                LatLng currLoci = new LatLng(location.getLatitude(), location.getLongitude());
                                googleMap.addMarker(new MarkerOptions().position(currLoci).title("Your current location!"));

                                //intent: sending current emulator location to results page
                                Intent intent = new Intent(getContext(), resultsPage.class);
                                intent.putExtra("curr_loc", currLoci);
                                startActivity(intent);
                            }
                        }

                    });



            //fetchLastLocation();

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());
//        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currLocation = location;
                UpdateCurrentLocation();
                Toast.makeText(getContext(), currLocation.getLatitude()
                        + "" + currLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                SupportMapFragment supportMapFragment = (SupportMapFragment)
                        getChildFragmentManager().findFragmentById(R.id.map);
                supportMapFragment.getMapAsync((OnMapReadyCallback) mapPage.this);
            }
        });
    }
    private void UpdateCurrentLocation() {
        LatLng latLng = new LatLng(currLocation.getLatitude(),
                currLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("Here I am!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        googleMap.addMarker(markerOptions);
    }



}
