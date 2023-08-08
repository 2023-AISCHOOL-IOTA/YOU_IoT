package com.example.dto;

import com.google.gson.annotations.SerializedName;

public class StreetParkingDTO {
    private String id;
    @SerializedName("road_n")
    private String roadName;
    @SerializedName("start_p")
    private String startPoint;
    @SerializedName("end_p")
    private String endPoint;
    @SerializedName("permit_a")
    private String permitArea;
    @SerializedName("distance")
    private String distance;
    @SerializedName("permit_t")
    private String permitTime;
    @SerializedName("holiday")
    private String holiday="";
    @SerializedName("restrict_c")
    private String restrict_c;
    public StreetParkingDTO(){

    }

    public StreetParkingDTO(String id, String roadName, String startPoint, String endPoint, String permitArea, String distance, String permitTime, String holiday, String restrict_c) {
        this.id = id;
        this.roadName = roadName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.permitArea = permitArea;
        this.distance = distance;
        this.permitTime = permitTime;
        this.holiday = holiday;
        this.restrict_c = restrict_c;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getPermitArea() {
        return permitArea;
    }

    public void setPermitArea(String permitArea) {
        this.permitArea = permitArea;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPermitTime() {
        return permitTime;
    }

    public void setPermitTime(String permitTime) {
        this.permitTime = permitTime;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getRestrict_c() {
        return restrict_c;
    }

    public void setRestrict_c(String restrict_c) {
        this.restrict_c = restrict_c;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
