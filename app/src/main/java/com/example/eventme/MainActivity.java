package com.example.eventme;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.eventme.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//hello
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    //james  start
    private EditText inPutID,inputName;
    private Button btnRead,btnSave;

    private TextView textViewID,textViewName;


    private DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        super.onCreate(savedInstanceState);
//        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Initialize the bottom navigation view
        //create bottom navigation view object
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigatin_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.expandableListView, R.id.mapPage, R.id.profilePage)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigatinView, navController);

//        val navController = findNavController(R.id.nav_fragment);
//        bottomNavigationView.setupWithNavController(navController);

//
        inPutID = findViewById(R.id.inputID);
        inputName = findViewById(R.id.inputName);


        btnRead = findViewById(R.id.btnRead);
        btnSave = findViewById(R.id.btnSave);


        textViewID = findViewById(R.id.textViewID);
        textViewName = findViewById(R.id.textViewName);

        UserRef = FirebaseDatabase.getInstance().getReference().child("User");


    }
}

