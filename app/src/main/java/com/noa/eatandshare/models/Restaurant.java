package com.noa.eatandshare.models;

public class Restaurant {

    String id, name, city, street, type;

    int streetNumber,numberOfRating;
    boolean kosher;
    double rate, sumRating;

    public Restaurant(String id, String name, String city, String street, String type, int streetNumber, int numberOfRating, boolean kosher, double rate, double sumRating) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.type = type;
        this.streetNumber = streetNumber;
        this.numberOfRating = numberOfRating;
        this.kosher = kosher;
        this.rate = rate;
        this.sumRating = sumRating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getNumberOfRating() {
        return numberOfRating;
    }

    public void setNumberOfRating(int numberOfRating) {
        this.numberOfRating = numberOfRating;
    }

    public boolean isKosher() {
        return kosher;
    }

    public void setKosher(boolean kosher) {
        this.kosher = kosher;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getSumRating() {
        return sumRating;
    }

    public void setSumRating(double sumRating) {
        this.sumRating = sumRating;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", type='" + type + '\'' +
                ", streetNumber=" + streetNumber +
                ", numberOfRating=" + numberOfRating +
                ", kosher=" + kosher +
                ", rate=" + rate +
                ", sumRating=" + sumRating +
                '}';
    }
}
