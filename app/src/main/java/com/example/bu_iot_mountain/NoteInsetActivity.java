package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapView;


public class NoteInsetActivity extends AppCompatActivity {

    private MapView mapView;

    private static final String TAG = "NoteInsetActivity";//Log사용을 위해서 로그 태그 설정
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_inset);

        /*MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);*/

        //mapView = findViewById(R.id.map_view);



    }

    public void pick(View v){
        Log.d(TAG, "사진버튼클릭 ");
        dispatchTakePictureIntent();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Log.d(TAG, "사진버튼클릭 들어옴 ");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Log.d(TAG, "사진촬영 작동 ");
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }




    public void gomypage(View v){
        Intent i1;
        i1 = new Intent(this, MypageActivity.class);
        startActivity(i1);
    }

}