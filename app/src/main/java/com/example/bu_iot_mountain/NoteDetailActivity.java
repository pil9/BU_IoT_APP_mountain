package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class NoteDetailActivity extends AppCompatActivity {

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    String noteidx;//게시물 클릭시 받아오는 게시물 인덱스
    TextView title;
    TextView context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        title = (TextView)findViewById(R.id.title);
        context = (TextView)findViewById(R.id.context);

        viewdeatilnote();

    }

    public void viewdeatilnote(){
        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;
        cursor=sqlDB.rawQuery("SELECT * FROM note WHERE "+noteidx+";",null);//select문 실행

        while (cursor.moveToNext()){//다음 레코드가 있을동안 수행
            title.setText(cursor.getString(1));//제목데이터 텍스트뷰에 넣어주기
            context.setText(cursor.getString(2));//내용데이터 텍스트뷰에 넣어주기 
        }
        //Toast.makeText(getApplicationContext(),"로그확인.idx: "+strNames+" id:"+strNumbers+" pw: "+strNumbers2+"",Toast.LENGTH_LONG).show();
        cursor.close();
        sqlDB.close();
    }


}