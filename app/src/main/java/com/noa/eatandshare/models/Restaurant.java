package com.noa.eatandshare.models;

import java.io.Serial;
import java.io.Serializable;

public class Restaurant implements Serializable {

    String id, name, city, street, type, details; // שדה העיר (city) כבר נמצא כאן

    int numberOfRating;
    boolean kosher;
    double rate, sumRating;

    protected String domain;
    protected String pic;
    protected String phone;



    public Restaurant(String id, String name, String city, String street, String type, String details, int numberOfRating, boolean kosher, double rate, double sumRating, String domain, String pic, String phone) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.type = type;
        this.details = details;
        this.numberOfRating = numberOfRating;
        this.kosher = kosher;
        this.rate = rate;
        this.sumRating = sumRating;
        this.domain = domain;
        this.pic = pic;
        this.phone = phone;
    }

    // קונסטרוקטור ברירת מחדל
    public Restaurant() {
    }

    // Getter and Setter עבור כל שדה
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
        return city;  // כאן אתה מקבל את העיר
    }

    public void setCity(String city) {
        this.city = city;  // כאן אתה מגדיר את העיר
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", type='" + type + '\'' +
                ", details='" + details + '\'' +
                ", numberOfRating=" + numberOfRating +
                ", kosher=" + kosher +
                ", rate=" + rate +
                ", sumRating=" + sumRating +
                ", domain='" + domain + '\'' +
                ", pic='" + pic + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
