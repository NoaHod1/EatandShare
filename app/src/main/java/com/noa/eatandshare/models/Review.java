package com.noa.eatandshare.models;

public class Review {

    String id;

    Restaurant restaurant;
    User user;
    String date;
    double rate;

    int numberRating;
    double sumRate;

    String details;



    public Review() {
    }

    public Review(String id, Restaurant restaurant, User user, String date, double rate, int numberRating, double sumRate, String details) {
        this.id = id;
        this.restaurant = restaurant;
        this.user = user;
        this.date = date;
        this.rate = rate;
        this.numberRating = numberRating;
        this.sumRate = sumRate;
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

    public void setRate() {
        this.rate = this.sumRate/this.numberRating;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getNumberRating() {
        return numberRating;
    }

    public void setNumberRating(int numberRating) {
        this.numberRating = numberRating;
    }

    public double getSumRate() {
        return sumRate;
    }

    public void setSumRate(double sumRate) {
        this.sumRate = sumRate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", restaurant=" + restaurant +
                ", user=" + user +
                ", date='" + date + '\'' +
                ", rate=" + rate +
                ", details='" + details + '\'' +
                '}';
    }
}