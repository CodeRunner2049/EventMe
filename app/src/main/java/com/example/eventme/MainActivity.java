package com.example.eventme;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the bottom navigation view
        //create bottom navigation view object
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigatin_view);
        val navController = findNavController(R.id.nav_fragment);
        bottomNavigationView.setupWithNavController(navController);

        }
}
