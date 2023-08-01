package com.example.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


public class ParkingListActivity extends AppCompatActivity {
    //    List<ContactsContract.Contacts.Data> dataInfo;
//
//    RecyclerView recyclerView;
//    RecyclerAdapter recyclerAdapter;
//
//    Parking parking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_list);

        Response.Listener<String> rplsn = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ((TextView) findViewById(R.id.dataName)).setText(response.toString());
                if (false) try {
                    JSONObject jo = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), "Dbg onResponse: " + jo.toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Err onResponseJson: " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };
        Response.ErrorListener errlsn = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ParkingListActivity.this, "Err ErrorListener: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        String url = "http://172.31.99.70:8070";
        StringRequest req = new StringRequest(Request.Method.GET, url, rplsn, errlsn);
//        req.setShouldCache(false);
        RequestQueue rq = Volley.newRequestQueue(ParkingListActivity.this);
        rq.getCache().clear();
        rq.add(req);

    }
}




