package missionarogya.thestrokeapp;

import java.util.Comparator;

/**
 * Created by Sonali Sinha on 2/1/2016.
 */
public class Hospital implements Comparable<Hospital> {
    private String name;
    private String address;
    private int phoneNumber;
    private int rating;
    private String services;

    Hospital(String name, String address, int phoneNumber, int rating, String services){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.rating = rating;
        this.services = services;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Overriding the compareTo method
    public int compareTo(Hospital h){
        return (h.rating) - (this.rating);
    }
}
