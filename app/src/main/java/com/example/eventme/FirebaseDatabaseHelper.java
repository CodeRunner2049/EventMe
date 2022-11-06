//reads data from Realtime Database
//returns list of events from FB RealtimeDatabase

package com.example.eventme;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

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
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceEvents = mDatabase.getReference("events");
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

    public void readUsers(final DataStatus dataStatus) {
        mReferenceEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                List<String> keys = new ArrayList<>();
                //possible issue with datasnap, we might need to fix later
                for (DataSnapshot keyNode : snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    UserBox user = keyNode.getValue(UserBox.class);
                    users.add(user);
                }
                dataStatus.DataIsLoaded(events, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String addUser(UserBox user, final DataStatus dataStatus)
    {
        String userId = mReferenceUsers.push().getKey();
        mReferenceUsers.child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dataStatus.DataIsInserted();
                    }
                });

        return userId;
    }

    public String addEvent(EventBox event, final DataStatus dataStatus)
    {
        String eventID = mReferenceEvents.push().getKey();
        mReferenceUsers.child(eventID).setValue(eventID)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dataStatus.DataIsInserted();
                    }
                });
        return eventID;
    }

    public List<EventBox> getEvents() {
        return events;
    }

    public List<UserBox> getUsers() {
        return users;
    }
}