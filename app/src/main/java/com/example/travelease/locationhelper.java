package com.example.travelease;

public class locationhelper {


    private double latitude;
    private double longitude;
    private String placename;



    public locationhelper(String placename) {
        this.placename=placename;


    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }




}
