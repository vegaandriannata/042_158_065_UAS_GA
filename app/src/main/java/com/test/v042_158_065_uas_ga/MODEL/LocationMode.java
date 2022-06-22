package com.test.v042_158_065_uas_ga.MODEL;

import com.google.gson.annotations.SerializedName;

public class LocationMode {
    @SerializedName("alamat")
    private String imageLocationName;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

    public LocationMode(String imageLocationName, String latitude, String longitude){
        this.imageLocationName = imageLocationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public LocationMode(){

    }
    public String getImageLocationName(){
        return imageLocationName;
    }
    public void setImageLocationName(String imageLocationName){
        this.imageLocationName = imageLocationName;
    }
    public String getLatitude(){
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude(){
        return longitude;
    }
    public void setLongitude(String longitude){
        this.longitude = longitude;
    }
}
