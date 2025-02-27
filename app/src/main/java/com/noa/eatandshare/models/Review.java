package com.noa.eatandshare.models;

public class Review {

    Restaurant restaurant;
    User user;
    String date;
    double rate;

    String details;



    public Review() {
    }

    public Review(Restaurant restaurant, User user, String date, double rate, String details) {
        this.restaurant = restaurant;
        this.user = user;
        this.date = date;
        this.rate = rate;
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Review{" +
                "restaurant=" + restaurant +
                ", user=" + user +
                ", date='" + date + '\'' +
                ", rate=" + rate +
                ", details='" + details + '\'' +
                '}';
    }
}