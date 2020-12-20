package com.example.bu_iot_mountain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class menuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void gonotelist(View v){
        finish();
        Intent i1;
        i1 = new Intent(this, MountListActivity.class);
        startActivity(i1);
    }

    public void noteinsert(View v){
        finish();
        Intent i1;
        i1 = new Intent(this, NoteInsetActivity.class);
        startActivity(i1);
    }

    public void gomypage(View v) {
        finish();
        Intent i1;
        i1 = new Intent(this, MypageActivity.class);
        startActivity(i1);
    }

    public void mymount(View v){
        finish();
        Intent i1;
        i1 = new Intent(this, Myuplist.class);
        startActivity(i1);
    }

    public void weather(View v){
        finish();
        Intent i1;
        i1 = new Intent(this, WeatherActivity.class);
        startActivity(i1);
    }

}
