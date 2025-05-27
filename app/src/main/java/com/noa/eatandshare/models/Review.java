package com.noa.eatandshare.models;

public class Review {


    String id;
    String restaurantId;
    String userID;
    String date;
    double rate;
    String details;



    public Review() {
    }

    public Review(String id, String restaurantId, String userID, String date, double rate, String details) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.userID = userID;
        this.date = date;
        this.rate = rate;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", userID='" + userID + '\'' +
                ", date='" + date + '\'' +
                ", rate=" + rate +
                ", details='" + details + '\'' +
                '}';
    }
}