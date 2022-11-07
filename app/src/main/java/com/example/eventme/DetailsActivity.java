package com.example.eventme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    TextView markertext;
    List<EventBox> listOfEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        markertext = findViewById(R.id.marker);

        String title = getIntent().getStringExtra("title");


        FirebaseDatabaseHelper fb = new FirebaseDatabaseHelper();
        fb.readEvents(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                Toast.makeText(getApplicationContext(), "Data was loaded", Toast.LENGTH_SHORT).show();
                listOfEvents = events;

                EventBox temp = null;
                for(EventBox e: listOfEvents){

                    if(e.getName() == title){
                        temp = e;
                        break;
                    }

                }

                String temporary = "";
                if(temp != null) {
                    temporary = temp.getDate() + "\n" + temp.getEvent_Type() + temp.getEvent_Description() + "\n";
                }
                else{
                    temporary = title;
                }

                markertext.setText(temporary);


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
}