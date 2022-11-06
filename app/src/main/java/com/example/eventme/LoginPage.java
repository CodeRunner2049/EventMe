package com.example.eventme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link loginPage#newInstance} factory method to
 * create an instance of this fragment.
 */

import android.content.Intent;
import android.text.TextUtils;

public class LoginPage extends Fragment {

    // creating variables for our edit text and buttons.
    private EditText userNameEdt, passwordEdt;
    private Button loginBtn, registerBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_login_page, container, false);

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

        registerBtn = rootview.findViewById(R.id.action_loginPage_to_registerPage);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(rootview).navigate(R.id.action_loginPage_to_registerPage);
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
            Toast.makeText(getActivity().getApplicationContext(), "Register Successful ", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getActivity().getApplicationContext(), MyExpandableListAdapter.class);
            i.putExtra("username", userName);
            startActivity(i);
        } else {
            // display a toast message when user logout of the app.
            Toast.makeText(getActivity().getApplicationContext(), "Incorrect login credentials", Toast.LENGTH_LONG).show();
        }
    }
}