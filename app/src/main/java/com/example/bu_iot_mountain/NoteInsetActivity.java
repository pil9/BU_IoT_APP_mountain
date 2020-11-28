package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.bu_iot_mountain.LoginActivity.useridx;


public class NoteInsetActivity extends AppCompatActivity {

    private final String tableName = "note";

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    EditText title;
    EditText context;

    Spinner s;

    private MapView mapView;

    private static final String TAG = "NoteInsetActivity";//Log사용을 위해서 로그 태그 설정
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_inset);


        title = (EditText) findViewById(R.id.title);
        context = (EditText) findViewById(R.id.context);

        myHelper=new LoginActivity.myDBHelper(this);


        sqlDB=myHelper.getReadableDatabase();//읽기모드로 디비 오픈
        Cursor cursor;
        cursor=sqlDB.rawQuery("SELECT * FROM mount ;",null);//select문 실행
        List plist = new ArrayList();
        while(cursor.moveToNext()){
            String eng = cursor.getString(1);
            plist.add(eng);
        }

        s =(Spinner)findViewById(R.id.spinner_m);
        ArrayAdapter dataA = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, plist);
        s.setAdapter(dataA);




    }

    public void pick(View v){
        Log.d(TAG, "사진버튼클릭 ");
        dispatchTakePictureIntent();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Log.d(TAG, "사진버튼클릭 들어옴 ");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Log.d(TAG, "사진촬영 작동 ");
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void insertnote(View v){
        String mountname = s.getSelectedItem().toString();
        Cursor cursor;
        cursor=sqlDB.rawQuery("SELECT * FROM mount WHERE name='"+mountname+"'   ;",null);//select문 실행
        cursor.moveToFirst();
        int mountidx = cursor.getInt(0);

        Log.d(TAG, "디비 넣기  ");

        sqlDB = myHelper.getWritableDatabase();//쓰기전용db열기

        Log.d(TAG,"tableName : "+ tableName);
        Log.d(TAG,"title : "+ title.getText().toString());
        Log.d(TAG,"context : "+ context.getText().toString());
        Log.d(TAG,"uri : "+ uri.toString());
        Log.d(TAG,"useridx : "+ useridx);
        Log.d(TAG,"mountidx : "+ mountidx);

        sqlDB.execSQL("INSERT INTO " + tableName + " VALUES(null,'" + title.getText().toString() + "','"
                + context.getText().toString() + "','" + uri.toString() + "'," + useridx + ","+mountidx+");");
        //insert문으로 인증게시물 추가
        Toast.makeText(getApplicationContext(), "인증완료! ", Toast.LENGTH_LONG).show();
        sqlDB.close();

        Intent i1;
        i1 = new Intent(this, MypageActivity.class);
        startActivity(i1);

    }


    static final int REQUEST_CODE = 1;
    ImageView imageView;
    Uri uri;

    public void onClickButton1(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);//미디어 저장소 접근
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_CODE) {//사진 선택 완료
            uri = data.getData();//사진 저장 url주소 변수에 저장
            TextView igroup1 = (TextView)findViewById(R.id.iv_view);
            igroup1.setText(uri.toString());//url주소 textview로 표시
        }
    }
    private void setImage(Uri uri) {//imageview를 선택한 사진으로 변경
        try{
            InputStream in = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }



    public void gomypage(View v){
        Intent i1;
        i1 = new Intent(this, MypageActivity.class);
        startActivity(i1);
    }




}