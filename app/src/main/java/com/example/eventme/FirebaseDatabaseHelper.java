//reads data from Realtime Database
//returns list of events from FB RealtimeDatabase

package com.example.eventme;

import android.content.Intent;
import android.media.metrics.Event;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FirebaseDatabaseHelper {
    private final FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private final FirebaseDatabase mDatabase;
    private final DatabaseReference mReferenceEvents;
    private final DatabaseReference mReferenceUsers;
    private final List<EventBox> events = new ArrayList<>();
    private final List<UserBox> users = new ArrayList<>();
    private final List<EventBox> userEvents = new ArrayList<>();

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
                    userEvents.add(event);
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

    public void readUserDetails()
    {
        currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        mReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //possible issue with datasnap, we might need to fix later
                DataSnapshot keyNode = snapshot.child(uid);
                users.add(keyNode.getValue(UserBox.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    public void loginUser (String username, String password, final DataStatus dataStatus)
    {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        currentUser = mAuth.getCurrentUser();
                        dataStatus.DataIsUpdated();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    public void registerUser(String username, String password, final DataStatus dataStatus)
    {
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        currentUser = mAuth.getCurrentUser();
                        dataStatus.DataIsUpdated();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    public void removeEventFromUser(String eventID, final DataStatus dataStatus)
    {
        currentUser = mAuth.getCurrentUser();
        String userID = currentUser.getUid();
        mReferenceUsers.child(userID).child("events").child(eventID).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dataStatus.DataIsDeleted(); 
                    }
                });
    }

    public void logoutUser()
    {
        mAuth.signOut();
    }

    public boolean isUserLoggedIn()
    {
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) return false;
        return true;
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

    public void deleteUser(final DataStatus dataStatus)
    {
        currentUser = mAuth.getCurrentUser();
        String key = currentUser.getUid();
        mReferenceUsers.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }

    public List<EventBox> sortEventsByName(List<EventBox> sorted_events)
    {
        Collections.sort(sorted_events, new Comparator<EventBox>() {
            @Override
            public int compare(EventBox object1, EventBox object2) {
                return object1.getName().compareTo(object2.getName());
            }
        });
        return sorted_events;
    }

    public List<EventBox> sortEventsByPrice(List<EventBox> sorted_events)
    {
        Collections.sort(sorted_events,new Comparator<EventBox>() {
            public int compare(EventBox s1, EventBox s2) {
                return s1.getCost() - s2.getCost();
            }
        });
        return sorted_events;
    }

    public List<EventBox> sortEventsByDate (List<EventBox> sorted_events)
    {
        Collections.sort(sorted_events, new Comparator<EventBox>() {
            public int compare(EventBox o1, EventBox o2) {

                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return sorted_events;
    }

    public List<EventBox> searchEventsByName (List<EventBox> sorted_events, String searchTerm) {
        List<EventBox> newEvents = new ArrayList<>();
        for (EventBox e : sorted_events) {
            if (e.getName() != null && e.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                newEvents.add(e);
            }
        }
        return newEvents;
    }
    
    public void updateUserName (String username)
    {
        mReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uid = currentUser.getUid();
                mReferenceUsers.child(uid).child("name").setValue(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateUserBirthday (String birthday)
    {
        mReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uid = currentUser.getUid();
                mReferenceUsers.child(uid).child("birthday").setValue(birthday);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean checkFirebaseConnection()
    {
        if (mDatabase != null) return true;
        return false;
    }


    public List<UserBox> getUsers() {
        return users;
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

    public FirebaseUser getCurrentUser()
    {
        return currentUser;
    }

}