package com.example.map;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivityTest extends TestCase {


    @Test
    @DisplayName("웹서버 연동 test")
    public void testOnRequestPermissionsResult() throws IOException {
        URL url = new URL("http://192.168.21.71:8080"); // 입력받은 웹서버 URL 저장
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // DB에 연결
        int resCode = conn.getResponseCode();

        assertEquals(200,resCode);
    }
}