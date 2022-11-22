package com.example.eventme.WhiteboxTestSuite;

import static org.junit.Assert.*;

import android.media.metrics.Event;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.eventme.EventBox;
import com.example.eventme.FirebaseDatabaseHelper;
import com.example.eventme.UserBox;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class EventMeWhiteboxTests {
    FirebaseDatabaseHelper fb = new FirebaseDatabaseHelper();
    private final DatabaseReference mReferenceUsers;
    private final FirebaseDatabase mDatabase;


    public EventMeWhiteboxTests () {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceUsers = mDatabase.getReference("User");
    }

    @Test
    public void testFirebaseConnected(){
        assertEquals(fb.checkFirebaseConnection(), true);
    }

    @Test
    public void testReadEvents()
    {
        fb.readEvents(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                assertEquals(events.size(), 21);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    @Test
    public void testUserRegister()
    {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fb.registerUser("testUser@gmail.com", "abc123", new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated() {
                FirebaseUser currentuser = fb.getCurrentUser();
                assertNotNull(currentuser);
                assertEquals(currentuser.getEmail(), "testUser@gmail.com");
            }

            @Override
            public void DataIsDeleted() {}
        });

    }

    @Test
    public void testUserLogout()
    {
        fb.logoutUser();
        assertNull(fb.getCurrentUser());
    }

    @Test
    public void testUserLogin()
    {
        fb.loginUser("testUser@gmail.com", "abc123", new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated()
            {
                FirebaseUser currentuser = fb.getCurrentUser();
                assertNotNull(currentuser);
                assertEquals(currentuser.getEmail(), "testUser@gmail.com");
            }

            @Override
            public void DataIsDeleted() {}
        });
    }


    @Test
    public void testAddEventsToUser()
    {
        fb.loginUser("testUser@gmail.com", "abc123", new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated()
            {
                EventBox event = new EventBox("new event", "ABCDEFG", 0, "test event", "JUnit", "This is a test event for JUnit tests", 0, 0, "2000-01-10 15:00:00.000", 0.0, 0.0, "");
                fb.addEventToUser(event, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<EventBox> events, List<String> keys) {            }

                    @Override
                    public void DataIsInserted() {
                        assertEquals(fb.containsEvent("ABCDEFG"), true);
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }

            @Override
            public void DataIsDeleted() {}
        });

    }

    @Test
    public void testRemoveEventFromUser()
    {
        fb.loginUser("testUser@gmail.com", "abc123", new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated()
            {
                fb.removeEventFromUser("ABCDEFG", new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<EventBox> events, List<String> keys) {            }

                    @Override
                    public void DataIsInserted() {
                        assertEquals(fb.containsEvent("ABCDEFG"), false);
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }

            @Override
            public void DataIsDeleted() {}
        });

    }

    @Test
    public void testContainsDate()
    {
        fb.logoutUser();
        fb.loginUser("testUser@gmail.com", "abc123", new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated()
            {
                EventBox event = new EventBox("new event", "ABCDEFG", 0, "test event", "JUnit", "This is a test event for JUnit tests", 0, 0, "2000-01-10 15:00:00.000", 0.0, 0.0, "");
                fb.addEventToUser(event, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<EventBox> events, List<String> keys) {            }

                    @Override
                    public void DataIsInserted() {
                        assertEquals(fb.containsDate("2000-01-10 15:00:00.000"), true);
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }

            @Override
            public void DataIsDeleted() {}
        });
    }

    @Test
    public void testReadUserEvents()
    {
        fb.logoutUser();
        fb.loginUser("testUser@gmail.com", "abc123", new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated()
            {
                EventBox event = new EventBox("new event", "ABCDEFG", 0, "test event", "JUnit", "This is a test event for JUnit tests", 0, 0, "2000-01-10 15:00:00.000", 0.0, 0.0, "");
                fb.addEventToUser(event, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<EventBox> events, List<String> keys) {            }

                    @Override
                    public void DataIsInserted() {
                        fb.readUserEvents(new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                                assertEquals(events.size(), 1);
                                assertEquals(events.get(0).getName(), "new event");
                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }

            @Override
            public void DataIsDeleted() {}
        });
    }

    @Test
    public void testSortEventsByPrice()
    {
        fb.logoutUser();
        fb.loginUser("testUser@gmail.com", "abc123", new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated()
            {
                fb.readUserEvents(new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                        List<EventBox> sorted_events = fb.sortEventsByPrice(events);
                        assertEquals(sorted_events.get(0), 0);
                        assertEquals(sorted_events.get(sorted_events.size() - 1), 500);

                        // Sort events list by price and compare to sortEventsByPrice
                        Collections.sort(events,new Comparator<EventBox>() {
                            public int compare(EventBox s1, EventBox s2) {
                                return s1.getCost() - s2.getCost();
                            }
                        });

                        assertEquals(sorted_events, events);
                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }

            @Override
            public void DataIsDeleted() {}
        });
    }

    @Test
    public void testSortEventsByName ()
    {
        fb.logoutUser();
        fb.loginUser("testUser@gmail.com", "abc123", new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated()
            {
                fb.readUserEvents(new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                        List<EventBox> sorted_events = fb.sortEventsByName(events);
                        assertEquals(sorted_events.get(0).getName(), "21 Savage");
                        assertEquals(sorted_events.get(sorted_events.size() - 1).getName(), "Victory Block Party");

                        // Sort events list by name and compare to sortEventsByName
                        Collections.sort(events, new Comparator<EventBox>() {
                            @Override
                            public int compare(EventBox object1, EventBox object2) {
                                return object1.getName().compareTo(object2.getName());
                            }
                        });

                        assertEquals(sorted_events, events);
                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }

            @Override
            public void DataIsDeleted() {}
        });
    }

    @Test
    public void testSortEventsByDate ()
    {
        fb.logoutUser();
        fb.loginUser("testUser@gmail.com", "abc123", new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated()
            {
                fb.readUserEvents(new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                        List<EventBox> sorted_events = fb.sortEventsByDate(events);
                        assertEquals(sorted_events.get(0).getName(), "Victory Block Party");
                        assertEquals(sorted_events.get(sorted_events.size() - 1).getName(), "Head in the Clouds");

                        // Sort events list by name and compare to sortEventsByName
                        Collections.sort(events, new Comparator<EventBox>() {
                            @Override
                            public int compare(EventBox object1, EventBox object2) {
                                return object1.getDate().compareTo(object2.getDate());
                            }
                        });

                        assertEquals(sorted_events, events);
                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }

            @Override
            public void DataIsDeleted() {}
        });
    }

    @Test
    public void testUpdateUsername()
    {
        fb.logoutUser();
        fb.loginUser("testUser@gmail.com", "abc123", new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated()
            {
                fb.updateUserName("new username");
                String uid = fb.getCurrentUser().getUid();

                mReferenceUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //possible issue with datasnap, we might need to fix later
                        DataSnapshot keyNode = snapshot.child(uid);
                        UserBox user = keyNode.getValue(UserBox.class);

                        assertEquals(user.getName(), "new username");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }


            @Override
            public void DataIsDeleted() {}
        });
    }

    @Test
    public void testUpdateBirthday()
    {
        fb.logoutUser();
        fb.loginUser("testUser@gmail.com", "abc123", new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {}

            @Override
            public void DataIsInserted() {}

            @Override
            public void DataIsUpdated()
            {
                fb.updateUserBirthday("02/12/2002");
                String uid = fb.getCurrentUser().getUid();

                mReferenceUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //possible issue with datasnap, we might need to fix later
                        DataSnapshot keyNode = snapshot.child(uid);
                        UserBox user = keyNode.getValue(UserBox.class);

                        assertEquals(user.getBirthday(), "02/12/2002");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }


            @Override
            public void DataIsDeleted() {}
        });
    }

    @Test
    public void testSeachByName ()
    {
        fb.readEvents(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<EventBox> events, List<String> keys) {
                String searchTerm = "USC";
                events = fb.searchEventsByName(events, searchTerm);
                assertEquals(events.get(0).getName(), "USC v UCLA");
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }


}
