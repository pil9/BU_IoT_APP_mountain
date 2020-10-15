package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final String dbName = "iotdb";
    private final String tableName = "member";

    SQLiteDatabase sampleDB = null;

    EditText idText;
    EditText editTextTextPassword;
    String Id;
    String Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Id = idText.getText().toString();
        Pass = editTextTextPassword.getText().toString();


        try {


            sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            //테이블이 존재하지 않으면 새로 생성합니다.
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
                    + " (id VARCHAR(20), pw VARCHAR(20) );");

            //테이블이 존재하는 경우 기존 데이터를 지우기 위해서 사용합니다.
            //sampleDB.execSQL("DELETE FROM " + tableName  );

            //새로운 데이터를 테이블에 집어넣습니다..
            /*for (int i=0; i<names.length; i++ ) {
                sampleDB.execSQL("INSERT INTO " + tableName
                        + " (name, phone)  Values ('" + names[i] + "', '" + phones[i]+"');");
            }*/
            Toast.makeText(getApplicationContext(),  "디비생성", Toast.LENGTH_LONG).show();

            sampleDB.close();

        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());


        }




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