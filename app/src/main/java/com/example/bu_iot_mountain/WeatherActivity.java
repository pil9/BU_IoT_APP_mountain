package com.example.bu_iot_mountain;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "pil9";
    private HttpConnection httpConn = HttpConnection.getInstance();
    TextView location_now;
    TextView weather_now;
    TextView temp_now;
    ImageView imageView;
    Bitmap bitmap;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("\uD83C\uDF24오늘의 날씨");
        final String[] mountain = {"북한산","한라산","지리산","설악산","덕유산"};
        location_now = (TextView) findViewById(R.id.location);
        weather_now = (TextView) findViewById(R.id.weather);
        temp_now = (TextView) findViewById(R.id.temp);
        imageView = (ImageView)findViewById(R.id.weatherimg);
        spinner= (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,mountain);
        spinner.setAdapter(adapter);
        sendData("서울");


//        sendData(); // 웹 서버로 데이터 전송

        Button button = (Button) findViewById(R.id.weather1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                spinner= (Spinner)findViewById(R.id.spinner);
                String mountain_name = spinner.getSelectedItem().toString();
                Toast.makeText(WeatherActivity.this,mountain_name,Toast.LENGTH_LONG).show();
                sendData(mountain_name);

            }
        });
    }
    public void getWeatherImg(final String imgid) {
        Log.d(TAG, "imgid값:" + imgid);
        new Thread() {
            public void run() {
                try{
//                    Log.d(TAG, "imgid값2:" + imgid);
                    URL url = new URL("https://openweathermap.org/img/wn/"+imgid+"@4x.png");
//                    Log.d(TAG, "URL값:" + url);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true); //Server 통신에서 입력 가능한 상태로 만듦
                    conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)
                    InputStream is = conn.getInputStream(); //inputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 반환
                    Log.d(TAG, "비트맵값:" + bitmap);
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Log.d(TAG, "비트맵값:" + bitmap);
        imageView.setImageBitmap(bitmap);
    }

    public void sendData(final String mountain_name) {
        new Thread() {
            public void run() {
                httpConn.requestWebServer(callback,mountain_name);

            }
        }.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        Thread.interrupted();
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d(TAG, "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String body = response.body().string();
            Log.d(TAG, "서버에서 응답한 Body:" + body);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    jsonParser(body);
                }
            });


        }
    };

    void jsonParser(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            String location = jsonObject.getString("name");

            //배열 들어간 json 처리할 때
            JSONArray jArrObject = jsonObject.getJSONArray("weather");
            String weather_name = "";
            String Weather_icon = "";
            for (int i = 0; i < jArrObject.length(); i++) {
                JSONObject jObject = jArrObject.getJSONObject(i);
                String Weather_id = jObject.getString("id");
                Weather_icon = jObject.getString("icon");

                weather_name = wDescEngToKor(Integer.parseInt(Weather_id));
            }

            String temp_main = jsonObject.getString("main");
            JSONObject jsonObject1 = new JSONObject(temp_main);
//            Log.d(TAG, (jsonObject1.getInt("temp")));
            String temp = String.format("%.1f", Double.valueOf(jsonObject1.getString("temp")))+"°C";
            Log.d(TAG, "여기"+temp);
            getWeatherImg(Weather_icon);


            if (location.equals("Cheonan")) {
                location = "천안시";
            }
            location_now.setText(spinner.getSelectedItem().toString());
            weather_now.setText(weather_name);
            temp_now.setText(temp);

            Log.d(TAG, "bitmap"+bitmap);

            imageView.setImageBitmap(bitmap);
//            Toast.makeText(getBaseContext(),"동동",Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    //API 결과 값 한글로 번역 -__-
    String wDescEngToKor(int w_id) {
        int[] w_id_arr = {201, 200, 202, 210, 211, 212, 221, 230, 231, 232,
                300, 301, 302, 310, 311, 312, 313, 314, 321, 500,
                501, 502, 503, 504, 511, 520, 521, 522, 531, 600,
                601, 602, 611, 612, 615, 616, 620, 621, 622, 701,
                711, 721, 731, 741, 751, 761, 762, 771, 781, 800,
                801, 802, 803, 804, 900, 901, 902, 903, 904, 905,
                906, 951, 952, 953, 954, 955, 956, 957, 958, 959,
                960, 961, 962};
        String[] w_kor_arr = {"가벼운 비를 동반한 천둥구름", "비를 동반한 천둥구름", "폭우를 동반한 천둥구름", "약한 천둥구름",
                "천둥구름", "강한 천둥구름", "불규칙적 천둥구름", "약한 연무를 동반한 천둥구름", "연무를 동반한 천둥구름",
                "강한 안개비를 동반한 천둥구름", "가벼운 안개비", "안개비", "강한 안개비", "가벼운 적은비", "적은비",
                "강한 적은비", "소나기와 안개비", "강한 소나기와 안개비", "소나기", "악한 비", "중간 비", "강한 비",
                "매우 강한 비", "극심한 비", "우박", "약한 소나기 비", "소나기 비", "강한 소나기 비", "불규칙적 소나기 비",
                "가벼운 눈", "눈", "강한 눈", "진눈깨비", "소나기 진눈깨비", "약한 비와 눈", "비와 눈", "약한 소나기 눈",
                "소나기 눈", "강한 소나기 눈", "박무", "연기", "시야흐림", "모래 먼지", "안개", "모래", "먼지", "화산재", "돌풍",
                "토네이도", "구름 한 점 없는 맑은 하늘", "약간의 구름이 낀 하늘", "드문드문 구름이 낀 하늘", "구름이 거의 없는 하늘",
                "구름으로 뒤덮인 흐린 하늘", "토네이도", "태풍", "허리케인", "한랭", "고온", "바람부는", "우박", "바람이 거의 없는",
                "약한 바람", "부드러운 바람", "중간 세기 바람", "신선한 바람", "센 바람", "돌풍에 가까운 센 바람", "돌풍",
                "심각한 돌풍", "폭풍", "강한 폭풍", "허리케인"};
        String Weather = "";
        for (int i = 0; i < w_id_arr.length; i++) {
            if (w_id_arr[i] == w_id) {
                Weather = w_kor_arr[i];
                break;
            }
        }
        return Weather;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_btn1:
                finish();
                Intent i1;
                i1 = new Intent(this, menuActivity.class);
                startActivity(i1);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}