package com.roadParkng.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


public class ParkingDTO implements Parcelable {
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


    public ParkingDTO(String id, String address, int spaces, String classes, String parkname, String parktype) {
        this.id = id;
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
    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(address);
        dest.writeInt(spaces);
        dest.writeString(classes);
        dest.writeString(parkname);
        dest.writeString(parktype);
    }

    protected ParkingDTO(Parcel in) {
        id = in.readString();
        address = in.readString();
        spaces = in.readInt();
        classes = in.readString();
        parkname = in.readString();
        parktype = in.readString();
    }

    public static final Creator<ParkingDTO> CREATOR = new Creator<ParkingDTO>() {
        @Override
        public ParkingDTO createFromParcel(Parcel in) {
            return new ParkingDTO(in);
        }

        @Override
        public ParkingDTO[] newArray(int size) {
            return new ParkingDTO[size];
        }
    };
}
