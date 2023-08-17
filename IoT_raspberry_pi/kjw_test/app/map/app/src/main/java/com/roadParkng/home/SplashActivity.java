package com.roadParkng.home;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.map.R;
import com.roadParkng.mainbtnpage.MainActivity;

public class SplashActivity extends Activity {
    //어플시작할때 모든 데이터를가지고 와서 list에 저장
    //single ton으로 로직짜야할듯?
    //다시
    //help : 왜 try문을 나오면 객체 저장이 다 사라지는지 646 --> 0개로바뀜 parkingListActivity 확인해야함


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
    protected void onPause(){
        super.onPause();
        finish();
    }
}