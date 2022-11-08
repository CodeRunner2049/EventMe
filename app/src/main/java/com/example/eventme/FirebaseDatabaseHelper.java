//reads data from Realtime Database
//returns list of events from FB RealtimeDatabase

package com.example.eventme;

import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceEvents;
    private DatabaseReference mReferenceUsers;
    private List<EventBox> events = new ArrayList<>();
    private List<UserBox> users = new ArrayList<>();

    // data 3
    public interface DataStatus
    {
        void DataIsLoaded(List<EventBox> events, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }



    public FirebaseDatabaseHelper() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceEvents = mDatabase.getReference("Events");
        mReferenceUsers = mDatabase.getReference("User");
    }

    public void readEvents(final DataStatus dataStatus)
    {
        mReferenceEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                events.clear();
                List<String> keys = new ArrayList<>();
                //possible issue with datasnap, we might need to fix later
                for (DataSnapshot keyNode : snapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    EventBox event = keyNode.getValue(EventBox.class);
                    events.add(event);
                }
                dataStatus.DataIsLoaded(events, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void readUserEvents(final DataStatus dataStatus) {
        currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        mReferenceUsers.child(uid).child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                events.clear();
                List<String> keys = new ArrayList<>();
                //possible issue with datasnap, we might need to fix later
                for (DataSnapshot keyNode : snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    EventBox event = keyNode.getValue(EventBox.class);
                    events.add(event);
                }
                dataStatus.DataIsLoaded(events, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addUserDetails(UserBox user, final DataStatus dataStatus)
    {
        currentUser = mAuth.getCurrentUser();
        String userID = currentUser.getUid();
        mReferenceUsers.child(userID).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void addEventToUser(final EventBox event, final DataStatus dataStatus)
    {
        currentUser = mAuth.getCurrentUser();
        String userID = currentUser.getUid();
        String eventID = event.getId();
        mReferenceUsers.child(userID).child("events").child(eventID).setValue(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void updateUser(String key, UserBox user, final DataStatus dataStatus)
    {
        mReferenceUsers.child(key).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }

    public void deleteUser(String key, final DataStatus dataStatus)
    {
        mReferenceUsers.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }



    public List<EventBox> getEvents() {
        return events;
    }

    public List<UserBox> getUsers() {
        return users;
    }
}