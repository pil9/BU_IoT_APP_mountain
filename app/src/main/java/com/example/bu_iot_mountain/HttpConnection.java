package com.example.bu_iot_mountain;

import android.util.Log;
import android.widget.Toast;

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
    public void requestWebServer(Callback callback, String mountain) {
        RequestBody body = new FormBody.Builder()
//                .add("parameter", parameter)
//                .add("parameter2", parameter2)
                .build();
        if (mountain.equals("설악산")) {
            Log.d("pil", "잘 작동함");
            Request request = new Request.Builder()
                    .url("https://api.openweathermap.org/data/2.5/weather?q=Sogcho&appid=370c6108b91660deca1c2d5125c466bc&units=metric")
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callback);
        }
        else if (mountain.equals("한라산")) {
                Log.d("pil", "잘 작동함");
                Request request = new Request.Builder()
                        .url("https://api.openweathermap.org/data/2.5/weather?q=Jeju&appid=370c6108b91660deca1c2d5125c466bc&units=metric")
                        .post(body)
                        .build();
                client.newCall(request).enqueue(callback);
        }
        else if (mountain.equals("덕유산")) {
            Log.d("pil", "잘 작동함");
            Request request = new Request.Builder()
                    .url("https://api.openweathermap.org/data/2.5/weather?q=Muju&appid=370c6108b91660deca1c2d5125c466bc&units=metric")
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callback);
        }
        else if (mountain.equals("지리산")) {
            Log.d("pil", "잘 작동함");
            Request request = new Request.Builder()
                    .url("https://api.openweathermap.org/data/2.5/weather?q=Hamyang&appid=370c6108b91660deca1c2d5125c466bc&units=metric")
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callback);
        }
        else if (mountain.equals("북한산")) {
            Log.d("pil", "잘 작동함");
            Request request = new Request.Builder()
                    .url("https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=370c6108b91660deca1c2d5125c466bc&units=metric")
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callback);
        }
        else{
            Log.d("pil", "아닐때 작동");
            Request request = new Request.Builder()
                    .url("https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=370c6108b91660deca1c2d5125c466bc&units=metric")
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callback);
        }

    }
}
