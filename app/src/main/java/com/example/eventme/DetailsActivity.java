package com.example.eventme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    TextView markertext;
    List<EventBox> listOfEvents;
    private ImageView imageView;


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
                    String horse = e.getName();
                    if(horse.equals(title)){
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
                markertext.setText("\n");
                markertext.setText("testingtestingtesting");
                String urlImage = temp.getImage_url();

                imageView = findViewById(R.id.image_view1);
                Glide.with(getApplicationContext()).load(urlImage).into(imageView);



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