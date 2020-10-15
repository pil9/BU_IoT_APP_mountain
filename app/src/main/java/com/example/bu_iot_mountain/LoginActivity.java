package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //private final String dbName = "iotdb";
    private final String tableName = "member";

    myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용
    //SQLiteDatabase sampleDB = null;

    EditText Id;
    EditText Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Id = (EditText) findViewById(R.id.idText);
        Pass = (EditText) findViewById(R.id.editTextTextPassword);

        //Id = idText.getText().toString();
        //Pass = editTextTextPassword.getText().toString();

        myHelper=new myDBHelper(this);
        //sqlDB=myHelper.getReadableDatabase();





    }

    public void gomypage(View v){
        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;
        System.out.print("tableName:"+tableName +" id:"+ Id +" Pass:" + Pass);
        cursor=sqlDB.rawQuery("SELECT * FROM "+tableName+" WHERE id='"+Id.getText().toString()+"' and pw='"+Pass.getText().toString()+"'  ;",null);//select문 실행
        if(cursor != null){//일치하는 정보없음 로그인 실
            Toast.makeText(getApplicationContext(),"등록되지 않음 정보입니다 .",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"로그인 성공 .",Toast.LENGTH_LONG).show();

            Intent i1;
            i1 = new Intent(this, MypageActivity.class);
            startActivity(i1);

        }
        cursor.close();
        sqlDB.close();
    }

    public void gojoin(View v){
        Intent i1;
        i1 = new Intent(this, JoinActivity.class);
        startActivity(i1);
    }

    public void viewmemberlog(View v){
        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;
        cursor=sqlDB.rawQuery("SELECT * FROM member;",null);//select문 실행
        String strNames="id"+"\r\n"+"-----------------------"+"\r\n";
        String strNumbers="pw"+"\r\n"+"------------------------"+"\r\n";
        while (cursor.moveToNext()){//다음 레코드가 있을동안 수행
            strNames+=cursor.getString(0)+"\r\n";
            strNumbers+=cursor.getString(1)+"\r\n";

        }
        Toast.makeText(getApplicationContext(),"로그확인."+strNames+" ",Toast.LENGTH_LONG).show();
        cursor.close();
        sqlDB.close();
    }

    public class myDBHelper extends SQLiteOpenHelper {
        //테이블 생성
        public myDBHelper(Context context){
            super(context, "groupDB",null,1);
        }
        //onCreate()-테이블생성
        //onUpgrade()-테이블 초기화 후 생성


        @Override
        public void onCreate(SQLiteDatabase db) {//쿼리문 수행
            db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
                    + " (id CHAR(20) PRIMARY KEY, pw CHAR(20) );");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+tableName);//groupTBL존재시 테이블 지움
            onCreate(db);//테이블 다시 생성

        }
    }


}