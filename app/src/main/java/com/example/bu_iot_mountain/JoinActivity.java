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

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

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


        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;
        cursor=sqlDB.rawQuery("SELECT * FROM "+tableName+" WHERE id='"+idText+"'   ;",null);//select문 실행

        if(cursor == null){//중복없음 가입가능
            sqlDB.close();
            cursor.close();
            sqlDB=myHelper.getWritableDatabase();//쓰기전용db열기
            sqlDB.execSQL("INSERT INTO groupTBL VALUES('"+idText+"',"+passText+");");
            Toast.makeText(getApplicationContext(),"회원가입완료 ",Toast.LENGTH_LONG).show();

            Intent i1;
            i1 = new Intent(this, MypageActivity.class);
            startActivity(i1);
            sqlDB.close();


        }else{
            sqlDB.close();
            cursor.close();
            Toast.makeText(getApplicationContext(),"중복된 아이디가 존재합니다. ",Toast.LENGTH_LONG).show();
        }



    }


}