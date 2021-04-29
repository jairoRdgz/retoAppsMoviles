package com.example.reto;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import java.util.ArrayList;

public class Location {
    protected String id;
    protected String name;
    protected String address;
    protected double lat;
    protected double lon;
    protected String img;

    public Location(String id,String nombre, String direccion, double lat, double lon, String img){
        this.id = id;
        this.name = nombre;
        this.address = direccion;
        this.lat = lat;
        this.lon = lon;
        this.img = img;
    }

    public String getId() {
        return id;
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

    public String getImg() {
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

    public void setImg(String img) {
        this.img = img;
    }

    private static int lastLocation= 0;

}
