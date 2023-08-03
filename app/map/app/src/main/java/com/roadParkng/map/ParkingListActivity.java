package com.roadParkng.map;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.map.R;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ParkingListActivity extends AppCompatActivity {
    private static RequestQueue requestQueue;
    ListView listView;
    private static final String TAG = "MAIN";
    private String URL = "http://192.168.20.54:5000";
    private Gson gson;
    List<String> addressList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_list);

        listView = findViewById(R.id.parkingName);




        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, response -> {
            JSONArray jsonArray = null;
            JSONObject jsonObject = null;
            String addressData;
            JSONObject addressJson = null;
            String address = null;


            try {
                jsonArray = new JSONArray(response);
                for(int i = 0 ; i< jsonArray.length(); i++){
                    addressData = jsonArray.getString(i);
                    jsonObject = new JSONObject(addressData);
                    addressJson = (JSONObject) jsonObject.get(String.valueOf(i));
                    //주소json잘불러왔는지 확인
//                    Log.d("주소 json : ", String.valueOf(addressJson));
                    address = addressJson.getString("address");
//                    Log.d("주소  : ", address);
                    addressList.add(address);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ParkingListActivity.this, android.R.layout.simple_list_item_1, addressList);

                    listView.setAdapter(adapter);
                }


            } catch (JSONException e) {
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

}






