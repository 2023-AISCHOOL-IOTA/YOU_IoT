package com.roadParkng.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.map.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import com.roadParkng.dto.ParkingDTO;
import com.roadParkng.list.ParkingListSingleton;
import com.roadParkng.mainbtnpage.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ParkingMapActivity extends AppCompatActivity {
    public static String address;
    private static final String API_URL = "https://dapi.kakao.com/v2/local/search/address.json?query=";
    String apikey = "cadcc81741f0933c18f2b1514f4146dd";
    String auth = "KakaoAK " + apikey;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_map);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//뒤로가기
        getSupportActionBar().setTitle("지도");



        mapView = new MapView(this);

        // KeywordSearcher를 이용해 주소 또는 장소 검색

//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

// 위치 권한 확인
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

// 권한이 있는 경우 현재 위치 가져오기
//        Location loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        double cur_lat = 35.146933;
        double cur_loc = 126.922031;

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(cur_lat, cur_loc), true);

        // 마커 생성 및 추가
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("현재 위치");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(cur_lat, cur_loc));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mapView.addPOIItem(marker);

        RelativeLayout relativeLayout = findViewById(R.id.map_view);
        relativeLayout.addView(mapView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // 뒤로가기 버튼 클릭 시 동작 정의
        onBackPressed();
        return true;
    }
}