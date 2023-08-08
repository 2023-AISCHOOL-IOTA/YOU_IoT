package com.example.list;

import com.example.dto.ParkingDTO;

import java.util.ArrayList;
import java.util.List;

public class ParkingListSingleton {
    private static ParkingListSingleton instance;
    private List<ParkingDTO> parkingList;

    private ParkingListSingleton() {
        parkingList = new ArrayList<>();
    }

    public static ParkingListSingleton getInstance() {
        if (instance == null) {
            instance = new ParkingListSingleton();
        }
        return instance;
    }

    public List<ParkingDTO> getParkingList() {
        return parkingList;
    }

    public void setParkingList(List<ParkingDTO> parkingList) {
        this.parkingList = parkingList;
    }
}
