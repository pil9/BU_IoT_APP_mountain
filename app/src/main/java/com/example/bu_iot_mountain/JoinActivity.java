package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {

    private final String dbName = "iotdb";
    private final String tableName = "member";

    EditText idText;
    EditText passText;
    String Id;
    String Pass;

    SQLiteDatabase iotdb;
    String id;
    String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        idText = (EditText) findViewById(R.id.id);
        passText = (EditText) findViewById(R.id.pass);

    }


    public void members(View v) {

        SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);


        //SELECT문을 사용하여 테이블에 있는 데이터를 가져옵니다.. 아이디 중복검색
        Cursor c = ReadDB.rawQuery("SELECT * FROM " + tableName, null);
        if(c == null){//중복없음 가입가능

            id = idText.getText().toString();
            pw = passText.getText().toString();

            iotdb.execSQL("INSERT INTO " + tableName + "(name, age, num, major) VALUES" +
                    "("+"'"+id+"'"+","+pw+");");

            Toast.makeText(this,"table :" + tableName + "생성",Toast.LENGTH_SHORT).show();

        }else{

        }


        c.close();


    }


}