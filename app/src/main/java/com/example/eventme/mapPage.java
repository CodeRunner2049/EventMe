package com.example.eventme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.lang.Math;

public class mapPage extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GoogleMap googleMap;
    ImageView transparentImageView;
    RecyclerView mRecyclerView;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private ArrayList<Marker> markers = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;


    private Location currLocation;
//    FragmentMapPageBinding binding;
    SupportMapFragment mapFragment;
    private static final int REQUEST_CODE = 101;

    private GoogleMap map;
    private ScrollView mScrollView;
    private Location lastKnownLocation;
    private static final int DEFAULT_ZOOM = 5;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    ArrayList<EventBox> sorted_events;

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
//            map = ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
            map = googleMap;

            // Turn on the My Location layer and the related control on the map.
            updateLocationUI();

            // Get the current location of the device and set the position of the map.
            getDeviceLocation();

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

                    sorted_events = (ArrayList<EventBox>) events;

                    fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                LatLng currLoci = new LatLng(location.getLatitude(), location.getLongitude());
                                googleMap.addMarker(new MarkerOptions().position(currLoci).title("Your current location!"));

                                Collections.sort(sorted_events,new Comparator<EventBox>() {
                                    public int compare(EventBox s1, EventBox s2) {
                                        return (int)(pythagoreanDistance(s2.getLatitude(), location.getLatitude(), s2.getLongitude(), location.getLongitude())
                                        - pythagoreanDistance(s1.getLatitude(), location.getLatitude(), s1.getLongitude(), location.getLongitude()));
                                    }
                                });

                                new RecyclerView_Config().setConfig(mRecyclerView, getContext(), sorted_events, keys);
                                //intent: sending current emulator location to results page
//                                Intent intent = new Intent(getContext(), resultsPage.class);
//                                intent.putExtra("curr_loc", currLoci);
//                                startActivity(intent);
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Can't get current location: " + e.getMessage(), Toast.LENGTH_LONG).show();
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


            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {

                    String markertitle = marker.getTitle();
                    if (!markertitle.equals("Your current location!"))
                    {
                        Intent i = new Intent(getContext(), DetailsActivity.class);
                        i.putExtra("eventId", markertitle);
                        startActivity(i);
                    }
                    return false;
                }
            });

//            Location loci = fusedLocationClient.getLastLocation().getResult();
//            LatLng currLoci = new LatLng(loci.getLatitude(), loci.getLongitude());
//            googleMap.addMarker(new MarkerOptions().position(currLoci).title("Your current location!"));
                    //fetchLastLocation();

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_map_page, container, false);
        mScrollView = (ScrollView) rootview.findViewById(R.id.scrollView);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.mRecyclerView);

        return rootview;
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
        /* check for this */
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

//        fetchLastLocation();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) { locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Toast.makeText(getContext(), "Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
//            Log.e("Exception: %s", e.getMessage());
        }
    }

    public LatLng getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        LatLng[] latlng = new LatLng[1];
        try {
//            LatLng latLng;
//            latLng.
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                latlng[0] = new LatLng(lastKnownLocation.getLatitude(),
                                        lastKnownLocation.getLongitude());
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng[0]
                                        , DEFAULT_ZOOM));
//                                return latlng;
                            }
                        } else {
                            Toast.makeText(getContext(), "Current location is null.", Toast.LENGTH_LONG).show();
//
                        }
                    }
                });
            }
            return latlng[0];
        } catch (SecurityException e)  {
            Toast.makeText(getContext(), "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public double pythagoreanDistance(
            double x1,
            double y1,
            double x2,
            double y2) {

        double ac = Math.abs(y2 - y1);
        double cb = Math.abs(x2 - x1);

        return Math.hypot(ac, cb);
    }
}






