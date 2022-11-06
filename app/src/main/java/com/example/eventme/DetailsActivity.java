package com.example.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView markertext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        markertext = findViewById(R.id.marker);

        String title = getIntent().getStringExtra("title");

        markertext.setText(title);


    }
}