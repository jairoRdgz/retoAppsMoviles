package com.example.reto;

import android.media.Image;

import java.util.ArrayList;

public class Location {
    protected String name;
    protected String address;
    protected double lat;
    protected double lon;
    protected Image img;

    public Location(String name, String address, double lat, double lon, Image img){
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public Image getImg() {
        return img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    private static int lastLocation= 0;

}
