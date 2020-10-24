package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MountListActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdt Adapter;
    private List<Notice> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("1번게시글","김철수","2020-10-24"));
        noticeList.add(new Notice("1번게시글","박영희","2020-10-24"));
        noticeList.add(new Notice("1번게시글","홍길동","2020-10-24"));
        noticeList.add(new Notice("1번게시글","김범중","2020-10-24"));
        noticeList.add(new Notice("1번게시글","이정필","2020-10-24"));


        Adapter = new NoticeListAdt(getApplicationContext(), noticeList);
        noticeListView.setAdapter(Adapter);
    }

    public void gomypage(View v){
        Intent i1;
        i1 = new Intent(this, MypageActivity.class);
        startActivity(i1);
    }

}