package com.example.eventme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.metrics.Event;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    TextView markertext;
    List<EventBox> listOfEvents;
    List<EventBox> userEvents;
    private ImageView imageView;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabaseHelper fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        markertext = (TextView) findViewById(R.id.mArker);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        registerButton = findViewById(R.id.register);
        registerButton.setText("Register For Event");

        String eventId = getIntent().getStringExtra("eventId");
        fb = new FirebaseDatabaseHelper();

        fb.readEvents(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                Toast.makeText(getApplicationContext(), "Data was loaded", Toast.LENGTH_SHORT).show();
                listOfEvents = events;

                final EventBox temp = events.get(keys.indexOf(eventId));

                String temporary = "";
                if (temp != null) {
                    temporary = temp.getName() + "\n" + temp.getDate() + "\n" + temp.getEvent_Type() + "\n" + temp.getEvent_Description() + "\n";
                } else {
                    temporary = eventId;
                }

                markertext.setText(temporary);
                String urlImage = temp.getImage_url();
                imageView = findViewById(R.id.image_view1);
                Glide.with(getApplicationContext()).load(urlImage).into(imageView);

                if (currentUser != null) {
                    fb.readUserEvents(new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                            userEvents = events;
                            if (!containsEvent(temp.getId()))
                            {
                                registerButton.setText("Register for Event");
                                registerForEvent(temp);
                            }
                            else
                            {
                                registerButton.setText("Unregister from Event");
                                unregisterFromEvent(temp);
                            }
                        }
                        @Override
                        public void DataIsInserted () {
                        }

                        @Override
                        public void DataIsUpdated () {
                        }

                        @Override
                        public void DataIsDeleted () {
                        }
                    });

                }
                else
                {
                    registerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(DetailsActivity.this, "Please login to register", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(DetailsActivity.this, LoginPage.class);
                            startActivity(intent);
                        }
                    });
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

    public void registerForEvent(final EventBox event)
    {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentUser = mAuth.getCurrentUser();
                if (containsDate(event.getDate()))
                {
                    Toast.makeText(DetailsActivity.this, "Event Conflict! Are you sure you want to register?", Toast.LENGTH_LONG).show();
                    registerButton.setText("OK!");
                    registerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addEvent(event);
                        }
                    });
                }
                else
                {
                    addEvent(event);
                }
            }
        });
    }

    public void addEvent(EventBox event)
    {
        fb.addEventToUser(event, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {
            }

            @Override
            public void DataIsInserted() {
                Toast.makeText(DetailsActivity.this, "Added to registered events!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void DataIsUpdated() {
            }

            @Override
            public void DataIsDeleted() {
            }
        });
    }

    public void unregisterFromEvent(EventBox event)
    {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentUser = mAuth.getCurrentUser();
                fb.removeEventFromUser(event, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                    }

                    @Override
                    public void DataIsInserted() {
                    }

                    @Override
                    public void DataIsUpdated() {
                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(DetailsActivity.this, "Unregistered from event!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    public static void hideKeyboard(@NonNull Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean containsEvent(String eventID)
    {
        for (EventBox event : userEvents)
        {
            if (event.getId().equals(eventID))
            {
                return true;
            }
        }
        return false;
    }

    public boolean containsDate(String datetime)
    {
        for (EventBox event : userEvents)
        {
            if (event.getDate().equals(datetime))
            {
                return true;
            }
        }
        return false;
    }
}