package com.example.parking;

import com.google.gson.annotations.SerializedName;

public class Parking {

    @SerializedName("apple")
    private String apple;

    @SerializedName("banana")
    private String banana;

    public Parking(String apple, String banana) {
        this.apple = apple;
        this.banana = banana;
    }

    public String getApple() {
        return apple;
    }

    public void setApple(String apple) {
        this.apple = apple;
    }

    public String getBanana() {
        return banana;
    }

    public void setBanana(String banana) {
        this.banana = banana;
    }
}
