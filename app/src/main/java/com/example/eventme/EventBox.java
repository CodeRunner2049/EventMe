package com.example.eventme;

import java.util.HashMap;
import java.util.Map;

public class EventBox {

<<<<<<< HEAD
    public EventBox() {}
=======
    public EventBox(String event_id, String name, Integer distance, Integer cost, String eventType, String sponsor, String eventDescription, Integer parking, Integer numberRegistration, String date, String image_url, Coordinates coordinates) {
        this.event_id = event_id;
        this.name = name;
        this.distance = distance;
        this.cost = cost;
        this.eventType = eventType;
        this.sponsor = sponsor;
        this.eventDescription = eventDescription;
        this.parking = parking;
        this.numberRegistration = numberRegistration;
        this.date = date;
        this.image_url = image_url;
        this.coordinates = coordinates;
    }

    public static class Coordinates {

        private Double latitude;
        private Double longitude;
        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

>>>>>>> 167a792ee1ab8f3ff306a41cff908fdf09baa3c5

    public EventBox(String name, String ID, Integer cost, String event_Type, String sponsor, String event_Description, Integer parking, Integer number_Registration, String date, Double latitude, Double longitude, String image_url) {
        Name = name;
        this.ID = ID;
        Cost = cost;
        Event_Type = event_Type;
        Sponsor = sponsor;
        Event_Description = event_Description;
        Parking = parking;
        Number_Registration = number_Registration;
        Date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image_url = image_url;
    }

    private String Name;
    private String ID;
    private Integer Cost;
    private String Event_Type;
    private String Sponsor;
    private String Event_Description;
    private Integer Parking;
    private Integer Number_Registration;
    private String Date;
    private Double latitude;
    private Double longitude;
    private String image_url;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }

    public Integer getCost() {
        return Cost;
    }

    public void setCost(Integer Cost) {
        this.Cost = Cost;
    }

    public String getEvent_Type() {
        return Event_Type;
    }

    public void setEvent_Type(String Event_Type) {
        this.Event_Type = Event_Type;
    }

    public String getSponsor() {
        return Sponsor;
    }

    public void setSponsor(String Sponsor) {
        this.Sponsor = Sponsor;
    }

    public String getEvent_Description() {
        return Event_Description;
    }

    public void setEvent_Description(String Event_Description) {
        this.Event_Description = Event_Description;
    }

    public Integer getParking() {
        return Parking;
    }

    public void setParking(Integer Parking) {
        this.Parking = Parking;
    }

    public Integer getNumber_Registration() {
        return Number_Registration;
    }

    public void setNumber_Registration(Integer Number_Registration) {
        this.Number_Registration = Number_Registration;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


}
