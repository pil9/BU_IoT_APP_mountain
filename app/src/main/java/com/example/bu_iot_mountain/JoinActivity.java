package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

        myHelper=new LoginActivity.myDBHelper(this);

    }


    public void members(View v) {


        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;
        cursor=sqlDB.rawQuery("SELECT * FROM "+tableName+" WHERE id='"+idText.getText().toString()+"'   ;",null);//select문 실행

       // if(cursor == null){//중복없음 가입가능
            sqlDB.close();
            cursor.close();
            sqlDB=myHelper.getWritableDatabase();//쓰기전용db열기
            sqlDB.execSQL("INSERT INTO "+tableName+" VALUES('"+idText.getText().toString()+"',"+passText.getText().toString()+");");
            Toast.makeText(getApplicationContext(),"회원가입완료 ",Toast.LENGTH_LONG).show();

           /* Intent i1;
            i1 = new Intent(this, MypageActivity.class);
            startActivity(i1);*/
            //sqlDB.close();


        //}else{
        //    sqlDB.close();
        //    cursor.close();
        //    Toast.makeText(getApplicationContext(),"중복된 아이디가 존재합니다. ",Toast.LENGTH_LONG).show();
        //}



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