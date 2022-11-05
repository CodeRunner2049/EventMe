//reads data from Realtime Database
//returns list of events from FB RealtimeDatabase

package com.example.eventme;

import androidx.annotation.NonNull;

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
    private List<EventBox> events = new ArrayList<>();

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
        mReferenceEvents = mDatabase.getReference("Events");
    }

    public void readEvents(final DataStatus dataStatus)
    {
        mReferenceEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                events.clear();
                List<String> keys = new ArrayList<>();
                //possible issue with datasnap, we might need to fix later
                for (DataSnapshot keyNode : dataSnapshot.getChildren())
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
}