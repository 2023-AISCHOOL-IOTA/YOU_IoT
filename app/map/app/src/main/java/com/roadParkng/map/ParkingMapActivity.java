package com.roadParkng.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.map.R;

public class ParkingMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_map);
        //        지도를 띄우자
////         java code
//        mapView = new MapView(this);
//        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
//        mapView.setZoomLevel(4, true);
//
//        mapViewContainer.addView(mapView);
////        mapView.setMapViewEventListener((MapView.MapViewEventListener) this);
//        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
//
//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName("현재위치");
//        marker.setTag(0);
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(cur_lat, cur_lon), true);
//        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(cur_lat, cur_lon));
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//        mapView.addPOIItem(marker);
    }
}