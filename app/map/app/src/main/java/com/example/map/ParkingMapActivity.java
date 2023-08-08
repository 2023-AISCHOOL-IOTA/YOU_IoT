package com.example.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;



import com.example.list.ParkingListSingleton;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.HashMap;
import java.util.Map;

public class ParkingMapActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    public static String address;
    private static final String API_URL = "https://dapi.kakao.com/v2/local/search/address.json?query=";
    String apikey = "cadcc81741f0933c18f2b1514f4146dd";
    private MapView mapView;
    private LinearLayout linearLayout;
    ParkingListSingleton parkingListSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_map);


//        mapView = new MapView(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//뒤로가기
        getSupportActionBar().setTitle("지도");

        mapView = (MapView)findViewById(R.id.map_view);
//        mapView.setDaumMapApiKey(apikey);
//        mapView.setCurrentLocationEventListener((MapView.CurrentLocationEventListener) this);
//
//        mapView.setMapViewEventListener(this);
//        mapView.setPOIItemEventListener(this);

        createParkingListMarker();


        double cur_lat = 35.146566;
        double cur_loc = 126.922168;

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(cur_lat, cur_loc), true);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeadingWithoutMapMoving);

        // 마커 생성 및 추가
//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName("현재 위치");
//        marker.setTag(0);
//        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(cur_lat, cur_loc));
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
//        mapView.addPOIItem(marker);



        // 카카오맵 맵뷰를 RelativeLayout에 추가
//        linearLayout.addView(mapView);


    }
    private void createParkingListMarker() {
        parkingListSingleton = parkingListSingleton.getInstance();
        for(int i = 0; i < 30; i++){
            String name = parkingListSingleton.getParkingList().get(i).getAddress();
            String parkname = parkingListSingleton.getParkingList().get(i).getParkname();
            getLatLoc(name,parkname);
        }

    }
    public void getLatLoc(String address,String parkname){
        // Volley 요청 큐 초기화
        requestQueue = Volley.newRequestQueue(this);

        // API 요청 보내기
        String apiUrl = API_URL + address;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray documents = response.getJSONArray("documents");
                            if (documents.length() > 0) {
                                JSONObject firstDocument = documents.getJSONObject(0);
                                String latitude = firstDocument.getString("y");
                                String longitude = firstDocument.getString("x");
                                // 마커 생성 및 추가
                                MapPOIItem marker = new MapPOIItem();
                                marker.setItemName(parkname);
                                marker.setTag(0);
                                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(latitude), Double.parseDouble(longitude)));
                                marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                                marker.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin);
                                mapView.addPOIItem(marker);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "KakaoAK " + apikey);
                return headers;
            }
        };

        // 요청을 요청 큐에 추가
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public boolean onSupportNavigateUp() {
        // 뒤로가기 버튼 클릭 시 동작 정의
        onBackPressed();
        return true;
    }
}
