package com.roadParkng.dto;

import com.google.gson.annotations.SerializedName;


public class Parking {
    @SerializedName("id")
    private String id;

    @SerializedName("address")
    private String address;

    @SerializedName("spaces")
    private int spaces;
    @SerializedName("class")
    private String classes;
    @SerializedName("parkname")
    private String parkname;

    @SerializedName("parktype")
    private String parktype;


    public Parking(String id ,String address, int spaces, String classes, String parkname, String parktype) {
        this.address = address;
        this.spaces = spaces;
        this.classes = classes;
        this.parkname = parkname;
        this.parktype = parktype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getParkname() {
        return parkname;
    }

    public void setParkname(String parkname) {
        this.parkname = parkname;
    }

    public String getParktype() {
        return parktype;
    }

    public void setParktype(String parktype) {
        this.parktype = parktype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
