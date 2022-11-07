package com.example.eventme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Intent;
import android.text.TextUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link registerPage#} factory method to
 * create an instance of this fragment.
 */
public class registerPage extends Fragment {

    // creating variables for our edit text and buttons.
    private EditText nameEdt, emailEdt, userNameEdt, passwordEdt;
    private Button registerBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_register_page, container, false);

        // initializing our edit text  and buttons.
        nameEdt = rootview.findViewById(R.id.idEdtFirstName);
        emailEdt = rootview.findViewById(R.id.idEdtEmail);
        userNameEdt = rootview.findViewById(R.id.idEdtUserName);
        passwordEdt = rootview.findViewById(R.id.idEdtPassword);

        registerBtn = rootview.findViewById(R.id.idBtnRegister);

        // adding on click listener for our button.
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are getting data from our edit text.
                String name = nameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String userName = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();


                // checking if the entered text is empty or not.
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter name and email", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter user name and password", Toast.LENGTH_SHORT).show();
                }

                // calling a method to login our user.
                registerUser(name, email, userName, password);
            }
        });

        return rootview;
    }

    private void registerUser (String name, String email, String userName, String password) {
        // calling a method to register a user.
        // after login checking if the user is null or not.
        // if the user is not null then we will display a toast message
        // with user login and passing that user to new activity.

        FirebaseDatabaseHelper fb = new FirebaseDatabaseHelper();
        fb.addUser(new UserBox(name, email, userName, password), new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {Toast.makeText(getActivity().getApplicationContext(), "Register Successful ", Toast.LENGTH_SHORT).show();}

            @Override
            public void DataIsUpdated() {}

            @Override
            public void DataIsDeleted() {}
        });
        Intent i = new Intent(getActivity().getApplicationContext(), ExplorePage.class);
        i.putExtra("username", userName);
        startActivity(i);
    }

}
