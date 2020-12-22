package com.example.bu_iot_mountain;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.bu_iot_mountain.LoginActivity.useridx;

public class Myuplist extends AppCompatActivity {

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    private ListView noticeListView;
    private List<Notice> noticeList = new ArrayList<Notice>();
    // static LinkedList<String> notelistmini = new LinkedList();
    private MountListAdt Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myuplist);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("\uD83C\uDFD5정복리스트");
        myHelper=new LoginActivity.myDBHelper(this);

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        Adapter = new MountListAdt(getApplicationContext(), noticeList);
        noticeListView.setAdapter(Adapter);
        mininotelist();

        Button btn = (Button)findViewById(R.id.myuplistback);
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
        noticeList.clear();
        cursor2 = sqlDB.rawQuery("SELECT * FROM note left OUTER join mount on note.mountidx=mount.mmidx where note.useridx = "+useridx+"  group by note.mountidx;", null);
        while (cursor2.moveToNext()){
            noticeList.add(new Notice(cursor2.getString(8),cursor2.getString(8),cursor2.getString(0)));
        }

        cursor2.close();
        sqlDB.close();

    }


}