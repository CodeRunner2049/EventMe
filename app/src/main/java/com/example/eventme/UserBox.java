package com.example.eventme;

import java.util.ArrayList;
import java.util.Date;

public class UserBox {

    public UserBox(String name, String email, String username, String password){
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getUserId(){
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getName(){
        return name;
    }

    public Date getBirthday(){
        return birthday;
    }

    public String getImage_url() {
        return image_url;
    }

    public ArrayList<EventBox> getRegisteredEvents() {
        return registeredEvents;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegisteredEvents(ArrayList<EventBox> registeredEvents) {
        this.registeredEvents = registeredEvents;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String userId;
    private String username;
    private String password;
    private String email;
    private String name;
    private Date birthday;
    private String image_url;
    private ArrayList<EventBox> registeredEvents;
}
