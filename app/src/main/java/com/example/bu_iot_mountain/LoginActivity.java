package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void gomypage(View v){
        Intent i1;
        i1 = new Intent(this, MypageActivity.class);
        startActivity(i1);
    }

    public void gojoin(View v){
        Intent i1;
        i1 = new Intent(this, JoinActivity.class);
        startActivity(i1);
    }


}