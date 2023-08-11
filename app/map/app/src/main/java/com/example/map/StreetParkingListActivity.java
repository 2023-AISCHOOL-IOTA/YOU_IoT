package com.example.map;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dto.StreetParkingDTO;
import com.example.list.ParkingListSingleton;
import com.example.list.StreetParkListSingleton;
import com.example.mainbtnpage.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StreetParkingListActivity extends AppCompatActivity {

    private static RequestQueue requestQueue;
    ListView listView;
    private static final String TAG = "MAIN";

    List<String> addressList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    //TODO 1: Gson으로 json형식 받아와서 StreetParkingDTO에 넣기
    //Gson안돼요.... 해결 나중에 하자
//    Gson gson = new Gson();
    List<StreetParkingDTO> streetParkingList = new ArrayList<>();

    MainActivity mainActivity = new MainActivity();
    private String URL = mainActivity.getURL()+"street_park";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_parking_list);

        Toolbar toolbar = findViewById(R.id.streetToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//뒤로가기
        getSupportActionBar().setTitle("노상 주차장 위치");

        listView = findViewById(R.id.streetParkingName);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addressList);
        listView.setAdapter(adapter);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, response -> {
            JSONArray jsonArray = null;
            JSONObject jsonObject = null;
            String addressData = null;
            JSONObject addressJson = null;

            String id = null;
            String roadName = null;
            String startPoint = null;
            String endPoint = null;
            String permitArea = null;
            String distance = null;
            String permitTime = null;
            String holiday = null;
            String restrict_c = null;


            try {
                jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    addressData = jsonArray.getString(i);
                    id = String.valueOf(i+1);
                    jsonObject = new JSONObject(addressData);
                    addressJson = (JSONObject) jsonObject.get(id);

                    //TODO 1: Gson사용하려면 주석해제
//                    streetParkingDTO = gson.fromJson(String.valueOf(jsonObject), StreetParkingDTO.class);


                    roadName = addressJson.getString("road_n");
                    startPoint = addressJson.getString("start_p");
                    endPoint = addressJson.getString("end_p");
                    permitArea = addressJson.getString("permit_a");
                    distance = addressJson.getString("distance");
                    permitTime = addressJson.getString("permit_t");
                    holiday = addressJson.getString("holiday");
                    restrict_c = addressJson.getString("restrict_c");
                    StreetParkingDTO streetParking = new StreetParkingDTO(id,roadName,startPoint, endPoint,permitArea, distance, permitTime, holiday, restrict_c);
//
                    streetParkingList.add(streetParking);
                    addressList.add("도로명 주소  : " + roadName + '\n'+ "시작시점 - 끝지점  : " + startPoint + " - " + endPoint);
                }
                StreetParkListSingleton.getInstance().setStreetparkingList(streetParkingList);
                // 어댑터에 데이터 변경을 알림
                adapter.notifyDataSetChanged();
            } catch (JSONException | NullPointerException e) {
                Log.d("오류다...", String.valueOf(e));
                throw new RuntimeException(e);
            }
        }, error -> {

        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // 뒤로가기 버튼 클릭 시 동작 정의
        onBackPressed();
        return true;
    }
}