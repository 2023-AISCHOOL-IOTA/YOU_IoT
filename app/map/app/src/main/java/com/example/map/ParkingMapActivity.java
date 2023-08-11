package com.example.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


import com.example.list.ParkingListSingleton;
import com.example.parking.CreateMarker;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParkingMapActivity extends AppCompatActivity implements MapView.MapViewEventListener {
    private static final String API_URL = "https://dapi.kakao.com/v2/local/search/address.json?query=";

    //카카오 restAPI키
    String apikey = "cadcc81741f0933c18f2b1514f4146dd";
    private MapView mapView;
    ParkingListSingleton parkingListSingleton;
    CreateMarker createMarker;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private final List<MapPOIItem> visibleMarkers = new ArrayList<>();
    private final List<MapPOIItem> allMarkers = new CopyOnWriteArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_map);
        mapView = (MapView) findViewById(R.id.map_view);
        if (checkLocationService()) {
            // GPS가 켜져있을 경우
            permissionCheck();
        } else {
            // GPS가 꺼져있을 경우
            Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//뒤로가기
        getSupportActionBar().setTitle("지도");

        createParkingListMarker();


    }

    private void permissionCheck() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 경우 권한 요청
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(this, "위치 권한이 있습니다.", Toast.LENGTH_SHORT).show();
            startTracking();
        }
    }

    // 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 위치 권한 획득 성공
                Toast.makeText(this, "위치 권한이 승인되었습니다!!!!", Toast.LENGTH_SHORT).show();
                createParkingListMarker();
                startTracking();
                // 여기서 카카오맵을 초기화하거나 위치 정보를 활용할 수 있습니다.
            } else {
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show();
                permissionCheck();
            }
        }
    }

    // 위치추적 시작
    private void startTracking() {
        Log.d("start들어옴?", "ooooo");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = location -> {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Log.d("경도 위도", latitude + " / " + longitude);
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeadingWithoutMapMoving);
            Log.d("start들어옴?", "위도경도 세팅 완");
        };
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
//        createParkingListMarker();
    }

    //GPS가 켜져있는지
    private boolean checkLocationService() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void createParkingListMarker() {
        parkingListSingleton = ParkingListSingleton.getInstance();
        getLatLocBatch(0);
//        Log.d("마커생성 매서드", "실행 완!!!");
        new Handler().postDelayed(() -> {
            addMarkersWithinVisibleBounds();
        }, 1000);
    }

    private void getLatLocBatch(int startIndex) {
        int batchSize = 10; // 배치 크기 설정
        int endIndex = Math.min(startIndex + batchSize, parkingListSingleton.getParkingList().size());
//        int endIndex = Math.min(startIndex + batchSize, 30);
        Log.d("마커생성 배치 메서드", String.valueOf(endIndex));
        for (int i = startIndex; i < endIndex; i++) {
            String name = parkingListSingleton.getParkingList().get(i).getAddress();
            String parkname = parkingListSingleton.getParkingList().get(i).getParkname();
            Thread thread = new Thread(() -> {
                getLatLocAsync(name, parkname);
            });

            // 스레드 시작
            thread.start();
        }

        if (endIndex < parkingListSingleton.getParkingList().size()) {
//        if (endIndex < 30) {
            // 다음 배치를 처리하기 위해 잠시 딜레이를 줌

            new Handler().postDelayed(() -> getLatLocBatch(endIndex), 1000);
        }
    }

    private void getLatLocAsync(String address, String parkname) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = API_URL + address;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    try {

                        JSONArray documents = response.getJSONArray("documents");
                        if (documents.length() > 0) {
                            JSONObject firstDocument = documents.getJSONObject(0);
                            String latitude = firstDocument.getString("y");
                            String longitude = firstDocument.getString("x");

                            MapPOIItem marker = new MapPOIItem();
                            marker.setItemName(parkname);
                            marker.setTag(0);
                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(
                                    Double.parseDouble(latitude), Double.parseDouble(longitude)));
                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin);

                            synchronized (allMarkers) {
                                allMarkers.add(marker);
                                mapView.addPOIItem(marker);
//                                Log.d("마커생성 restapi메서드 ",marker.getItemName());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Error", error.toString())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "KakaoAK " + apikey);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
//        Log.d("json요청", String.valueOf(jsonObjectRequest));
    }

//    private void addMarkersWithinBounds() {
//        mapView.removeAllPOIItems(); // 현재 지도에 표시된 마커 제거
//
//        MapPoint.GeoCoordinate topLeft = mapView.getMapPointBounds().topRight.getMapPointGeoCoord();
//        MapPoint.GeoCoordinate bottomRight = mapView.getMapPointBounds().bottomLeft.getMapPointGeoCoord();
//
//        for (MapPOIItem marker : allMarkers) {
//            MapPoint.GeoCoordinate coordinate = marker.getMapPoint().getMapPointGeoCoord();
//            if (coordinate.latitude >= bottomRight.latitude && coordinate.latitude <= topLeft.latitude
//                    && coordinate.longitude >= topLeft.longitude && coordinate.longitude <= bottomRight.longitude) {
//                mapView.addPOIItem(marker);  // 영역에 포함되는 마커만 추가
//            }
//        }
//    }

    private void addMarkersWithinVisibleBounds() {
        Log.d("현재 마커갯수", String.valueOf(allMarkers.size()));
        mapView.removeAllPOIItems(); // 현재 지도에 표시된 마커 제거

        MapPoint.GeoCoordinate topLeft = mapView.getMapPointBounds().topRight.getMapPointGeoCoord();
        MapPoint.GeoCoordinate bottomRight = mapView.getMapPointBounds().bottomLeft.getMapPointGeoCoord();

        List<MapPOIItem> markersToAdd = new ArrayList<>(); // 임시 리스트 생성

        for (MapPOIItem marker : allMarkers) {
            MapPoint.GeoCoordinate coordinate = marker.getMapPoint().getMapPointGeoCoord();
            if (coordinate.latitude <= topLeft.latitude && coordinate.latitude >= bottomRight.latitude
                    && coordinate.longitude >= topLeft.longitude && coordinate.longitude <= bottomRight.longitude) {
                markersToAdd.add(marker);  // 화면에 보이는 영역에 포함되는 마커 추가
            }
        }

        mapView.addPOIItems(markersToAdd.toArray(new MapPOIItem[0])); // 임시 리스트의 마커들 추가
    }


    @Override
    public boolean onSupportNavigateUp() {
        // 뒤로가기 버튼 클릭 시 동작 정의
        onBackPressed();
        return true;
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        addMarkersWithinVisibleBounds();
    }
}
