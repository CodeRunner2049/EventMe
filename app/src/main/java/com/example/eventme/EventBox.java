package com.example.eventme;

import java.util.HashMap;
import java.util.Map;

public class EventBox {

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


    }


        private String event_id;
        private String name;
        private Integer distance;
        private Integer cost;
        private String eventType;
        private String sponsor;
        private String eventDescription;
        private Integer parking;
        private Integer numberRegistration;
        private String date;
        private String image_url;
        private Coordinates coordinates;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Integer getCost() {
            return cost;
        }

        public void setCost(Integer cost) {
            this.cost = cost;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public String getSponsor() {
            return sponsor;
        }

        public void setSponsor(String sponsor) {
            this.sponsor = sponsor;
        }

        public String getEventDescription() {
            return eventDescription;
        }

        public void setEventDescription(String eventDescription) {
            this.eventDescription = eventDescription;
        }

        public Integer getParking() {
            return parking;
        }

        public void setParking(Integer parking) {
            this.parking = parking;
        }

        public Integer getNumberRegistration() {
            return numberRegistration;
        }

        public void setNumberRegistration(Integer numberRegistration) {
            this.numberRegistration = numberRegistration;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
        }

        private String getImage_url(){
            return image_url;
        }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
