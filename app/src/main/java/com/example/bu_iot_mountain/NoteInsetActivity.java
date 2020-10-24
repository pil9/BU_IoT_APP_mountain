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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.bu_iot_mountain.LoginActivity.useridx;


public class NoteInsetActivity extends AppCompatActivity {

    private final String tableName = "note";

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    EditText title;
    EditText context;


    private MapView mapView;

    private static final String TAG = "NoteInsetActivity";//Log사용을 위해서 로그 태그 설정
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_inset);

        /*MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);*/

        //mapView = findViewById(R.id.map_view);

        title = (EditText) findViewById(R.id.title);
        context = (EditText) findViewById(R.id.context);

        myHelper=new LoginActivity.myDBHelper(this);

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
        Log.d(TAG, "디비 넣ㅇ기  ");

        sqlDB = myHelper.getWritableDatabase();//쓰기전용db열기

        Log.d(TAG,"tableName : "+ tableName);
        Log.d(TAG,"title : "+ title.getText().toString());
        Log.d(TAG,"context : "+ context.getText().toString());
        Log.d(TAG,"uri : "+ uri.toString());
        Log.d(TAG,"useridx : "+ useridx);

        sqlDB.execSQL("INSERT INTO " + tableName + " VALUES(null,'" + title.getText().toString() + "','" + context.getText().toString() + "','" + uri.toString() + "'," + useridx + ",1);");
        //insert문으로 회원 추가
        Toast.makeText(getApplicationContext(), "인증완료! ", Toast.LENGTH_LONG).show();
        sqlDB.close();

    }


    static final int REQUEST_CODE = 1;
    ImageView imageView;
    Uri uri;

    public void onClickButton1(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_CODE) {
            uri = data.getData();
            TextView igroup1 = (TextView)findViewById(R.id.iv_view);
            igroup1.setText(uri.toString());
        }
    }
    private void setImage(Uri uri) {
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