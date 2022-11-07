package com.example.eventme;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class profilePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ImageView avatar;
    private Button profileUpload, logout;
    private EditText nameEditText;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceUsers;
    private FirebaseUser currentUser;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageURI;


    public profilePage() {
        // Required empty public constructor
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceUsers = mDatabase.getReference("User");
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }
    //change
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_profile_page, container, false);

        currentUser = mAuth.getCurrentUser();
        avatar = rootview.findViewById(R.id.uploadPicture);
        nameEditText = rootview.findViewById(R.id.userNameEditText);
//        birthdayEditText = rootview.findViewById(R.id.BirthdayEditText);
        profileUpload = rootview.findViewById(R.id.imageUpload);
        logout = rootview.findViewById(R.id.logout);

        if (currentUser == null)
        {
            Toast.makeText(getContext(), "Login to View Profile", Toast.LENGTH_LONG).show();
//            NavController nav = Navigation.findNavController(rootview);
//            nav.navigate(R.id.action_profilePage_to_loginPage);
            Intent i = new Intent(getContext(), LoginPage.class);
            startActivity(i);
        }
        else
        {
            String uid = currentUser.getUid();

            Toast.makeText(getContext(), "User is already logged in!", Toast.LENGTH_LONG).show();
            ActivityResultLauncher<Intent> imageActivity = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == getActivity().RESULT_OK) {
                                // There are no request codes
                                Intent data = result.getData();
                                imageURI = data.getData();
                                avatar.setImageURI(imageURI);
                            }
                        }
                    });

            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    imageActivity.launch(galleryIntent);
                }
            });

            profileUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (imageURI != null)
                    {
                        uploadToFirebase(imageURI);

                    }

                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "User logged out!", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                    Intent i = new Intent(getContext(), LoginPage.class);
                    startActivity(i);
                }
            });
        }



        return rootview;
    }

    private void uploadToFirebase (Uri uri)
    {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String modelId = uri.toString();
                        mReferenceUsers.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String uid = currentUser.getUid();
                                mReferenceUsers.child(uid).child("image_url").setValue(uid);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(getContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Uploading Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension (Uri mUri)
    {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

}