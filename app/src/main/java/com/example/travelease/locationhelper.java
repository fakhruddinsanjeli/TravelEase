package com.example.travelease;

public class locationhelper {


    private double latitude;
    private double longitude;
    private String Place;



    public locationhelper(String Place) {
        this.Place=Place;


    }

    public String getPlacename() {
        return Place;
    }

    public void setPlacename(String Place) {
        this.Place = Place;
    }




}
