package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static com.example.bu_iot_mountain.LoginActivity.useridx;
public class MypageActivity extends AppCompatActivity {

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    TextView gomount,gomount2;
    ProgressBar progress2;

    private ListView noticeListView;
    private List<Notice> noticeList = new ArrayList<Notice>();
   // static LinkedList<String> notelistmini = new LinkedList();
    private NoticeListAdt Adapter;
    private static final String TAG = "MypageActivity";//Log사용을 위해서 로그 태그 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        myHelper=new LoginActivity.myDBHelper(this);

        gomount = (TextView)findViewById(R.id.gomount);
        gomount2 = (TextView)findViewById(R.id.gomount2);
        progress2 = (ProgressBar)findViewById(R.id.progress2);

        clearpersent();

        noticeListView = (ListView) findViewById(R.id.listview1);
        Adapter = new NoticeListAdt(getApplicationContext(), noticeList);
        noticeListView.setAdapter(Adapter);
        mininotelist();
        noticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 상세정보 화면으로 이동하기(인텐트 날리기)
                // 1. 다음화면을 만든다
                // 2. AndroidManifest.xml 에 화면을 등록한다
                // 3. Intent 객체를 생성하여 날린다
                Intent intent = new Intent(
                        getApplicationContext(), // 현재화면의 제어권자
                        NoteDetailActivity.class); // 다음넘어갈 화면

                // intent 객체에 데이터를 실어서 보내기
                // 리스트뷰 클릭시 인텐트 (Intent) 생성하고 position 값을 이용하여 인텐트로 넘길값들을 넘긴다
                Log.d(TAG, "detail idx::::::"+noticeList.get(position).date);
                intent.putExtra("detailnidx", noticeList.get(position).date);

                //TextView nnnidx =(TextView)findViewById(R.id.dateText);
                //String nsidx = nnnidx.getText().toString();
                /*Intent i1;
                i1 = new Intent(this, NoteDetailActivity.class);
                i1.putExtra("detailnidx", nsidx);*/
                startActivity(intent);

            }
        });


    }

    public void mininotelist() {
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor2;

        cursor2 = sqlDB.rawQuery("SELECT * FROM note left OUTER join member on note.useridx=member.midx where note.useridx="+useridx+" order by nidx desc limit 5;", null);
        while (cursor2.moveToNext()){
            noticeList.add(new Notice(cursor2.getString(8),cursor2.getString(1),cursor2.getString(0)));
        }

        cursor2.close();
        sqlDB.close();

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
        //Toast.makeText(getApplicationContext(), "pronum: "+Progressnum, Toast.LENGTH_LONG).show();
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

   /* public void godetailnote(View v){
        TextView nnnidx =(TextView)findViewById(R.id.dateText);
        String nsidx = nnnidx.getText().toString();
        Intent i1;
        i1 = new Intent(this, NoteDetailActivity.class);
        i1.putExtra("detailnidx", nsidx);
        startActivity(i1);
    }*/




}