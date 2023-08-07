package com.roadParkng.mainbtnpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;


import com.example.map.R;

import com.roadParkng.map.ParkingListActivity;
import com.roadParkng.map.ParkingMapActivity;
import com.roadParkng.map.StreetParkingListActivity;


public class MainActivity extends AppCompatActivity{
//    private MapView mapView;
//    private ViewGroup mapViewContainer;
//    EditText editText;
//    //경도 위도 변수
//    private double cur_lat;
//    private double cur_lon;
//
//    ParkingDTO parking;
//    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //공영민영주차장 리스트
        Button parking_findBtn = findViewById(R.id.parkingfind_btn);
        //
        Button streetparking_findBtn = findViewById(R.id.roadParkingBtn);
        Button mapView_Btn = findViewById(R.id.mapViewBtn);

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
                Intent intent = new Intent(getApplicationContext(), ParkingMapActivity.class);
                startActivity(intent);
            }
        });

    }
}