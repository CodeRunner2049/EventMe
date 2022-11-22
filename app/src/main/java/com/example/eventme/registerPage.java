package com.example.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import android.content.Intent;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class registerPage extends AppCompatActivity {

    // creating variables for our edit text and buttons.
    FirebaseDatabaseHelper fb = new FirebaseDatabaseHelper();
    private EditText nameEdt, emailEdt, userNameEdt, passwordEdt;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        mAuth = FirebaseAuth.getInstance();

        // initializing our edit text  and buttons.
        nameEdt = findViewById(R.id.idEdtFirstName);
        emailEdt = findViewById(R.id.idEdtEmail);
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);

        registerBtn = findViewById(R.id.idBtnRegister);

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
                    Toast.makeText(registerPage.this, "Please enter name and email", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                    Toast.makeText(registerPage.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
                }


                mAuth.createUserWithEmailAndPassword(userName, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(registerPage.this, "User registered successfully!", Toast.LENGTH_LONG).show();
                                currentUser = mAuth.getCurrentUser();
//                                Navigation.findNavController(rootview).navigate(R.id.action_registerPage_to_profilePage);

                                UserBox user = new UserBox(name, email, userName, password);
                                fb.addUserDetails(user, new FirebaseDatabaseHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<EventBox> events, List<String> keys) {}
                                    @Override
                                    public void DataIsInserted() {}

                                    @Override
                                    public void DataIsUpdated() {}

                                    @Override
                                    public void DataIsDeleted() {}
                                });

                                Intent i = new Intent(registerPage.this, MainActivity.class);
                                startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(registerPage.this, "Register failed! " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });



            }
        });


    }
}