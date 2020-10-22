package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
    }

    public void gomountlist(View v){
        Intent i1;
        i1 = new Intent(this, MountListActivity.class);
        startActivity(i1);
    }

    public void gonotelist(View v){
        Intent i1;
        i1 = new Intent(this, NoteListActivity.class);
        startActivity(i1);
    }

    public void noteinsert(View v){
        Intent i1;
        i1 = new Intent(this, NoteInsetActivity.class);
        startActivity(i1);
    }

}