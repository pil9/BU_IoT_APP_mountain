package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import static com.example.bu_iot_mountain.LoginActivity.useridx;
public class MypageActivity extends AppCompatActivity {

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    TextView gomount,gomount2;
    ProgressBar progress2;

    static LinkedList<String> notelistmini = new LinkedList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        myHelper=new LoginActivity.myDBHelper(this);

        gomount = (TextView)findViewById(R.id.gomount);
        gomount2 = (TextView)findViewById(R.id.gomount2);
        progress2 = (ProgressBar)findViewById(R.id.progress2);

        clearpersent();





    }

    public void clearpersent(){
        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;

        cursor=sqlDB.rawQuery("SELECT * FROM mount;",null);//mountlist
        int mcount = 0;
        while (cursor.moveToNext()){
            mcount++;
        }
        cursor.close();
        cursor=sqlDB.rawQuery("SELECT * FROM note where useridx ="+useridx+" GROUP BY mountidx ;",null);//select문 실행

        int count = 0;
        while (cursor.moveToNext()){//다음 레코드가 있을동안 수행
            count++;
        }
        double per = (double) count/mcount*100;
        int Progressnum = (int) per;
        Toast.makeText(getApplicationContext(), "pronum: "+Progressnum, Toast.LENGTH_LONG).show();
        gomount.setText(per+"% 정복");//제목데이터 텍스트뷰에 넣어주기
        gomount2.setText(mcount+"개 중 "+count+"개 완주");
        progress2.setProgress(Progressnum);
        cursor.close();
        sqlDB.close();

    }

    public void gomountlist(View v){
        Intent i1;
        i1 = new Intent(this, MountListActivity.class);
        startActivity(i1);
    }

    public void gonotelist(View v){
        Intent i1;
        i1 = new Intent(this, MountListActivity.class);
        startActivity(i1);
    }

    public void noteinsert(View v){
        Intent i1;
        i1 = new Intent(this, NoteInsetActivity.class);
        startActivity(i1);
    }

    public void godetailnote(View v){
        Intent i1;
        i1 = new Intent(this, NoteDetailActivity.class);
        startActivity(i1);
    }




}