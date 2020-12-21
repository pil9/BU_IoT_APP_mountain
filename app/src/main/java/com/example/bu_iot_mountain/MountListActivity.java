package com.example.bu_iot_mountain;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
        ActionBar ab = getSupportActionBar();
        ab.setTitle("💬등산커뮤니티");
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

                Intent intent = new Intent(
                        getApplicationContext(), // 현재화면의 제어권자
                        NoteDetailActivity.class); // 다음넘어갈 화면

                intent.putExtra("detailnidx", noticeList.get(position).date);

                startActivity(intent);

            }
        });

        Button btn = (Button)findViewById(R.id.mountback);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i1;
                i1 = new Intent(getApplicationContext(), menuActivity.class);
                startActivity(i1);
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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_btn1:
                finish();
                Intent i1;
                i1 = new Intent(this, menuActivity.class);
                startActivity(i1);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}