package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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

        myHelper=new LoginActivity.myDBHelper(this);

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        Adapter = new MountListAdt(getApplicationContext(), noticeList);
        noticeListView.setAdapter(Adapter);
        mininotelist();

    }

    public void mininotelist() {
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor2;

        cursor2 = sqlDB.rawQuery("SELECT * FROM note left OUTER join mount on note.mountidx=mount.mmidx group by note.mountidx;", null);
        while (cursor2.moveToNext()){
            noticeList.add(new Notice(cursor2.getString(8),cursor2.getString(8),cursor2.getString(0)));
        }

        cursor2.close();
        sqlDB.close();

    }


}