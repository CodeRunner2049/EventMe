package com.example.eventme;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profilePage#newInstance} factory method to
 * create an instance of this fragment.
 */

import android.content.Intent;
import android.text.TextUtils;

public class profilePage extends Fragment {

    // creating variables for our edit text and buttons.
    private EditText userNameEdt, passwordEdt;
    private Button loginBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_profile_page, container, false);

        // initializing our edit text  and buttons.
        userNameEdt = rootview.findViewById(R.id.idEdtUserName);
        passwordEdt = rootview.findViewById(R.id.idEdtPassword);
        loginBtn = rootview.findViewById(R.id.idBtnLogin);

        // adding on click listener for our button.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are getting data from our edit text.
                String userName = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                // checking if the entered text is empty or not.
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter user name and password", Toast.LENGTH_SHORT).show();
                }

                // calling a method to login our user.
                loginUser(userName, password);
            }
        });
        return rootview;
    }

    private void loginUser(String userName, String password) {
        // calling a method to login a user.
        // after login checking if the user is null or not.
        if (userName == "admin" && password == "password") {
            // if the user is not null then we will display a toast message
            // with user login and passing that user to new activity.
            Toast.makeText(getActivity().getApplicationContext(), "Login Successful ", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getActivity().getApplicationContext(), ExplorePage.class);
            i.putExtra("username", userName);
            startActivity(i);
        } else {
            // display a toast message when user logout of the app.
            Toast.makeText(getActivity().getApplicationContext(), "Incorrect login credentials", Toast.LENGTH_LONG).show();
        }
    }
}