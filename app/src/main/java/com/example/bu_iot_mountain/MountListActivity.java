package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MountListActivity extends AppCompatActivity {

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    private ListView noticeListView;
    private List<Notice> noticeList = new ArrayList<Notice>();
    // static LinkedList<String> notelistmini = new LinkedList();
    private NoticeListAdt Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        myHelper=new LoginActivity.myDBHelper(this);

        /*noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("1번게시글","김철수","2020-10-24"));
        noticeList.add(new Notice("2번게시글","박영희","2020-10-24"));
        noticeList.add(new Notice("3번게시글","홍길동","2020-10-24"));
        noticeList.add(new Notice("4번게시글","김범중","2020-10-24"));
        noticeList.add(new Notice("5번게시글","이정필","2020-10-24"));


        Adapter = new NoticeListAdt(getApplicationContext(), noticeList);
        noticeListView.setAdapter(Adapter);*/

        noticeListView = (ListView) findViewById(R.id.noticeListView);
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
                //Log.d(TAG, "detail idx::::::"+noticeList.get(position).date);
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

        cursor2 = sqlDB.rawQuery("SELECT * FROM note left OUTER join member on note.useridx=member.midx order by nidx desc;", null);
        while (cursor2.moveToNext()){
            noticeList.add(new Notice(cursor2.getString(8),cursor2.getString(1),cursor2.getString(0)));
        }

        cursor2.close();
        sqlDB.close();

    }

    public void gomypage(View v){
        Intent i1;
        i1 = new Intent(this, MypageActivity.class);
        startActivity(i1);
    }

}