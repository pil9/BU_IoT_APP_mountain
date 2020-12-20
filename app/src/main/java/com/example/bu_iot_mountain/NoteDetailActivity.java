package com.example.bu_iot_mountain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;

import static com.example.bu_iot_mountain.LoginActivity.useridx;


public class NoteDetailActivity extends AppCompatActivity {

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    private static final int MY_PERMISSION_REQUEST_FILE = 0;

    int noteidx;//게시물 클릭시 받아오는 게시물 인덱스
    TextView title;
    TextView context;
    ImageView iimg;
    EditText comment;
    static LinkedList<String> comtitle = new LinkedList();
    static LinkedList<String> comuserid = new LinkedList();
    ListView listView;
    myAdapters adapter;

    private static final String TAG = "NoteDetailActivity";//Log사용을 위해서 로그 태그 설정


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        Intent secondIntent = getIntent();
        noteidx =Integer.parseInt( secondIntent.getStringExtra("detailnidx") );

        title = (TextView)findViewById(R.id.title);
        context = (TextView)findViewById(R.id.context);
        iimg = (ImageView)findViewById(R.id.iimg);


        myHelper=new LoginActivity.myDBHelper(this);

        listView = (ListView) findViewById(R.id.listview1e);

        adapter = new myAdapters();
        listView.setAdapter(adapter);

        permissionCheck_img();
        commentlist();
    }


    private void permissionCheck_img() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "승낙1 : ");
            //Manifest.permission.ACCESS_FINE_LOCATION 접근 승낙 상태 일때
            viewdeatilnote();
        } else { //Manifest.permission.ACCESS_FINE_LOCATION 접근 거절 상태 일때 //사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄운다.
            Log.d(TAG, "승낙2 : ");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_FILE);
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_LOCATION) {
            Log.d(TAG, "승낙 후 1111 : ");
            viewdeatilnote();
        }
    }*/

    private void setImage(Uri uri,ImageView img) {
        try{
            Log.d(TAG, "uri- : "+ uri);
            InputStream in = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            img.setImageBitmap(bitmap);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void viewdeatilnote(){
        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;

        cursor=sqlDB.rawQuery("SELECT * FROM note WHERE nidx="+noteidx+";",null);//select문 실행

        while (cursor.moveToNext()){//다음 레코드가 있을동안 수행
            title.setText(cursor.getString(1));//제목데이터 텍스트뷰에 넣어주기
            context.setText(cursor.getString(2));//내용데이터 텍스트뷰에 넣어주기
            Uri uri = Uri.parse("file://"+cursor.getString(3));
            Log.d(TAG, "uri : "+ uri);
            setImage(uri,iimg);
        }

        cursor.close();
        sqlDB.close();

    }

   /* private void setImage(Uri uri) {
        try{
            InputStream in = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            iimg.setImageBitmap(bitmap);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }*/

    public void commentinsert(View view) {//댓글 입력버튼 클릭
        comment = (EditText) findViewById(R.id.comment);
        String comStr = comment.getText().toString();
        Log.d(TAG, "댓글 확인 : "+ comStr);
        sqlDB = myHelper.getWritableDatabase();//쓰기전용db열기
        sqlDB.execSQL("INSERT INTO comment VALUES(null,'" + useridx + "','"
                + noteidx + "','" + comStr + "');");
        //insert문으로 댓글  추가
        sqlDB.close();
        Toast.makeText(getApplicationContext(), "댓글등록 완료! ", Toast.LENGTH_LONG).show();
        comment.setText(null);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(comment.getWindowToken(), 0);
        commentlist();
    }

    public void commentlist(){

        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;

        comtitle.clear();
        cursor=sqlDB.rawQuery("SELECT * FROM comment left OUTER join member on comment.useridx = member.midx WHERE noteidx="+noteidx+";",null);//select문 실행
        String strNames="com1"+"\r\n"+"-----------------------"+"\r\n";
        String strNumbers="com2"+"\r\n"+"------------------------"+"\r\n";
        String strNumbers2="com3"+"\r\n"+"------------------------"+"\r\n";
        while (cursor.moveToNext()){//다음 레코드가 있을동안 수행
            strNames+=cursor.getString(0)+"\r\n";
            strNumbers+=cursor.getString(1)+"\r\n";
            strNumbers2+=cursor.getString(5)+"\r\n";
            comtitle.add(cursor.getString(3));
            comuserid.add(cursor.getString(5));

        }
        //Toast.makeText(getApplicationContext(),"댓글확인.ㅂ: "+strNames+" ㅈ:"+strNumbers+" ㄷ: "+strNumbers2+"",Toast.LENGTH_LONG).show();

        cursor.close();
        sqlDB.close();
    }

    class myAdapters extends BaseAdapter {
        @Override
        public int getCount() {
            return comtitle.size();
        }

        @Override
        public Object getItem(int position) {
            return comtitle.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            conView view = new conView(getApplicationContext());

            //TextView view = new TextView(getApplicationContext());
            //view.setText(fruits[position]);
            view.setFruit(comtitle.get(position));
            view.setPrice(comuserid.get(position));
            //view.setTextSize(50.0f);
            //view.setTextColor(Color.BLUE);
            return view;
        }
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