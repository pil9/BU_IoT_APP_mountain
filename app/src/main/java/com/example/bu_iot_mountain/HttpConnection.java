package com.example.bu_iot_mountain;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpConnection {
    private OkHttpClient client;
    private static HttpConnection instance = new HttpConnection();
    public static HttpConnection getInstance() {
        return instance;
    }

    private HttpConnection(){ this.client = new OkHttpClient(); }


    /** 웹 서버로 요청을 한다. */
    public void requestWebServer(Callback callback) {
        RequestBody body = new FormBody.Builder()
//                .add("parameter", parameter)
//                .add("parameter2", parameter2)
                .build();
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=cheonan&appid=370c6108b91660deca1c2d5125c466bc&units=metric")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
