package com.jhb.dvt.dvt_store.Models;

/**
 * Created by CreeD on 2016/02/26.
 */
public class Location {

    private String Place ;
    private double Lat ;
    private double Long ;
    private String Address ;
    
    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        this.Place = place;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
