package com.test.v042_158_065_uas_ga.MODEL;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListLocationModel {
    @SerializedName("data")
    private List<LocationMode> mData;

    public ListLocationModel(List<LocationMode>mData){
        this.mData = mData;}

    public ListLocationModel(){
    }
    public List<LocationMode> getmData(){
        return mData;
    }
    public void setmData(List<LocationMode>mData){
        this.mData = mData;}
}
