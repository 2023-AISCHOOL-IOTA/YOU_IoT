package com.example.parking;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.list.ParkingListSingleton;
import com.example.list.StreetParkListSingleton;
import com.example.map.ParkingMapActivity;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateMarker{
    private static CreateMarker instance;
    private List<MapPOIItem> allMarkers;
    private static final String API_URL = "https://dapi.kakao.com/v2/local/search/address.json?query=";
    String apikey = "cadcc81741f0933c18f2b1514f4146dd";
    ParkingListSingleton parkingListSingleton;
    //카카오 restAPI키
    private CreateMarker() {
        allMarkers = new ArrayList<>();
    }
    public static CreateMarker getInstance() {
        if (instance == null) {
            instance = new CreateMarker();
        }
        return instance;
    }

    public void createParkingListMarker(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        parkingListSingleton = ParkingListSingleton.getInstance();
        getLatLocBatch(requestQueue,0);
//        Log.d("마커생성 매서드", "실행 완!!!");
    }

    public void getLatLocBatch(RequestQueue requestQueue,int startIndex) {
        int batchSize = 10; // 배치 크기 설정
        int endIndex = Math.min(startIndex + batchSize, parkingListSingleton.getParkingList().size());
//        int endIndex = Math.min(startIndex + batchSize, 30);
        Log.d("마커생성 배치 메서드", String.valueOf(endIndex));
        for (int i = startIndex; i < endIndex; i++) {
            String name = parkingListSingleton.getParkingList().get(i).getAddress();
            String parkname = parkingListSingleton.getParkingList().get(i).getParkname();
            Thread thread = new Thread(() -> {
                try {
                    getLatLocAsync(requestQueue,name, parkname);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                }
            });

            // 스레드 시작
            thread.start();
        }

        if (endIndex < parkingListSingleton.getParkingList().size()) {
//        if (endIndex < 30) {
            // 다음 배치를 처리하기 위해 잠시 딜레이를 줌

            new Handler().postDelayed(() -> getLatLocBatch(requestQueue,endIndex), 1000);
        }
    }

    public void getLatLocAsync(RequestQueue requestQueue,String address, String parkname) throws IllegalAccessException, InstantiationException {
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
//                                mapView.addPOIItem(marker);
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

    public List<MapPOIItem> getAllMarkers() {
        return allMarkers;
    }
}
