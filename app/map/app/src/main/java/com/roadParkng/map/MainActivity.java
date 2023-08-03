package com.roadParkng.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.map.R;
import com.roadParkng.dto.Parking;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity{
    private MapView mapView;
    private ViewGroup mapViewContainer;
    EditText editText;
    //경도 위도 변수
    private double cur_lat;
    private double cur_lon;

    Parking parking;
    Handler handler = new Handler();
//= "http://192.168.21.71:8080"
    private static String urls = "http://172.31.99.70:8070."; // flask 호출 url

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                //해시값구해서 developers 플랫폼 수정
                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

//         권한ID를 가져옵니다
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);

        int permission2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int permission3 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

//         권한이 열려있는지 확인
        if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED || permission3 == PackageManager.PERMISSION_DENIED) {
            // 마쉬멜로우 이상버전부터 권한을 물어본다
            if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
                requestPermissions(
                        new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1000);
            }
            return;
        }

//        지도를 띄우자
//         java code
        mapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapView.setZoomLevel(4, true);

        mapViewContainer.addView(mapView);
//        mapView.setMapViewEventListener((MapView.MapViewEventListener) this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);

        //위치 가져오기
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //마지막 위치 받아오기
        Location loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        cur_lat = loc_Current.getLatitude();
        cur_lon = loc_Current.getLongitude();


        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("현재위치");
        marker.setTag(0);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(cur_lat, cur_lon), true);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(cur_lat, cur_lon));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);

        editText = findViewById(R.id.editText); // 앱 내에서 위치 확인
//        Toast.makeText(MainActivity.this, parking.getApple(),Toast.LENGTH_SHORT).show();
//
//

//        Button button = findViewById(R.id.button); // 앱 내에서 위치확인
//        button.setOnClickListener(new View.OnClickListener() { // 버튼을 눌렀을 때
//            @Override
//            public void onClick(View v) {
//                urls = editText.getText().toString();
//                Toast.makeText(MainActivity.this, "버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, parking.getApple(),Toast.LENGTH_SHORT).show();
//                RequestThread thread = new RequestThread(); // Thread 생성
//                thread.start(); // Thread 시작
//            }
//        });





    }
    //DB연동


    class RequestThread extends Thread { // DB를 불러올 때도 앱이 동작할 수 있게 하기 위해 Thread 생성
        @Override
        public void run() { // 이 쓰레드에서 실행 될 메인 코드
            try {
                URL url = new URL(urls); // 입력받은 웹서버 URL 저장
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // DB에 연결
                if(conn != null){ // 만약 연결이 되었을 경우
                    conn.setConnectTimeout(10000); // 10초 동안 기다린 후 응답이 없으면 종료
                    conn.setRequestMethod("GET"); // GET 메소드 : 웹 서버로 부터 리소스를 가져온다.
                    conn.setDoInput(true); // 서버에서 온 데이터를 입력받을 수 있는 상태인가? true
                    conn.setDoOutput(true); // 서버에서 온 데이터를 출력할 수 있는 상태인가? true

                    int resCode = conn.getResponseCode(); // 응답 코드를 리턴 받는다.

                    if(resCode == HttpURLConnection.HTTP_OK){ // 만약 응답 코드가 200(=OK)일 경우
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                         BufferedReader() : 엔터만 경계로 인식하고 받은 데이터를 String 으로 고정, Scanner 에 비해 빠름!
//                         InputStreamReader() : 지정된 문자 집합 내의 문자로 인코딩
//                         getInputStream() : url 에서 데이터를 읽어옴
                        String line = null; // 웹에서 가져올 데이터를 저장하기위한 변수
                        while(true){
                            line = reader.readLine(); // readLine() : 한 줄을 읽어오는 함수
                            if(line == null) // 만약 읽어올 줄이 없으면 break
                                break;
                            println(line); // 출력 *80번째 줄의 함수*

                        }
                        reader.close(); // 입력이 끝남
                    }
                    conn.disconnect(); // DB연결 해제
                }
            } catch (Exception e) { //예외 처리
                e.printStackTrace(); // printStackTrace() : 에러 메세지의 발생 근원지를 찾아서 단계별로 에러를 출력
            }
        }
    }

    public void println(final String data){ // final : 변수의 상수화 => 변수 변경 불가
        // post() : 핸들러에서 쓰레드로 ()를 보냄
// Runnable() : 실행 코드가 담긴 객체
// run() : 실행될 코드가 들어있는 메소드
        handler.post(() -> Toast.makeText(MainActivity.this, data,Toast.LENGTH_SHORT).show());
    }

    //http://192.168.20.199:8070
    // 권한 체크 이후로직
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        // READ_PHONE_STATE의 권한 체크 결과를 불러온다
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == 1000) {
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            // 권한 체크에 동의를 하지 않으면 안드로이드 종료
            if (check_result == false) {
                finish();
            }
        }
    }

    //DB연동

}