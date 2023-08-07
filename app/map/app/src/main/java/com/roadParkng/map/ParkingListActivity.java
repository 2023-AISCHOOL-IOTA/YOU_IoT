package com.roadParkng.map;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.map.R;
import com.google.gson.Gson;
import com.roadParkng.dto.ParkingDTO;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class ParkingListActivity extends AppCompatActivity {
    private static RequestQueue requestQueue;
    ListView listView;
    private static final String TAG = "MAIN";
    private String URL = "http://nayeho.ngrok.io/parking";
    List<String> addressList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    List<ParkingDTO> parkingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//뒤로가기
        getSupportActionBar().setTitle("공영/민영 주차장위치");

        listView = findViewById(R.id.parkingName);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addressList);
        listView.setAdapter(adapter);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, response -> {
            JSONArray jsonArray = null;
            JSONObject jsonObject = null;
            String addressData;
            JSONObject addressJson = null;

            String id = null;
            String address = null;
            int spaces = 0;
            String classes = null;
            String parkname = null;
            String parktype = null;


            try {
                jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    addressData = jsonArray.getString(i);
                    id = String.valueOf(i);
                    jsonObject = new JSONObject(addressData);
                    addressJson = (JSONObject) jsonObject.get(String.valueOf(i));
                    address = addressJson.getString("address");
                    spaces = addressJson.getInt("spaces");
                    classes = addressJson.getString("class");
                    parkname = addressJson.getString("parkname");
                    parktype = addressJson.getString("parktype");
                    ParkingDTO parking = new ParkingDTO(id,address,spaces,classes,parkname,parktype);

                    parkingList.add(parking);
                    addressList.add(address);
                }

                // 어댑터에 데이터 변경을 알림
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.d("오류... : ", String.valueOf(e));
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
