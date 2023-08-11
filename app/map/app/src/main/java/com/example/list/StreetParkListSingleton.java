package com.example.list;

import com.example.dto.ParkingDTO;
import com.example.dto.StreetParkingDTO;

import java.util.ArrayList;
import java.util.List;

public class StreetParkListSingleton {
    private static StreetParkListSingleton instance;
    private List<StreetParkingDTO> streetparkingList;

    private StreetParkListSingleton() {
        streetparkingList = new ArrayList<>();
    }

    public static StreetParkListSingleton getInstance() {
        if (instance == null) {
            instance = new StreetParkListSingleton();
        }
        return instance;
    }

    public List<StreetParkingDTO> getStreetparkingList() {
        return streetparkingList;
    }

    public void setStreetparkingList(List<StreetParkingDTO> streetparkingList) {
        this.streetparkingList = streetparkingList;
    }
}
