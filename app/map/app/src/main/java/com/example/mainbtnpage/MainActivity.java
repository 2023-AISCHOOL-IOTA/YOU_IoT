package com.example.mainbtnpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;


import com.example.map.R;

import com.example.dto.ParkingDTO;
import com.example.dto.StreetParkingDTO;
import com.example.map.ParkingListActivity;
import com.example.map.ParkingMapActivity;
import com.example.map.StreetParkingListActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    private String URL = "http://nayeho.ngrok.io/";
    List<ParkingDTO> parkingAddressList = new ArrayList<>();
    List<StreetParkingDTO> streetAddressList = new ArrayList<>();
    public String getURL(){
        return URL;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }
//    public void setParkingAddressList(List<ParkingDTO> parkingAddressList){
//        this.parkingAddressList = parkingAddressList;
//    }
//
//    public void setStreetAddressList(List<StreetParkingDTO> streetAddressList){
//        this.streetAddressList = streetAddressList;
//    }
//    public List<ParkingDTO> getParkingAddressList() {
//        return parkingAddressList;
//    }
//
//    public List<StreetParkingDTO> getStreetAddressList() {
//        return streetAddressList;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //공영민영주차장 리스트
        Button parking_findBtn = findViewById(R.id.parkingfind_btn);
        //
        Button streetparking_findBtn = findViewById(R.id.roadParkingBtn);
        Button mapView_Btn = findViewById(R.id.mapViewBtn);

        Intent mapintent = new Intent(getApplicationContext(), ParkingMapActivity.class);

        parking_findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParkingListActivity.class);
                startActivity(intent);
            }
        });
        streetparking_findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StreetParkingListActivity.class);
                startActivity(intent);
            }
        });
        mapView_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mapintent);
//                Intent intent = new Intent(getApplicationContext(), ParkingMapActivity.class);
//                intent.putParcelableArrayListExtra("parkingList", new ArrayList<>(parkingAddressList));
//                startActivity(intent);
            }
        });

    }


}