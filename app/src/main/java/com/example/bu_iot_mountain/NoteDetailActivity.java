package com.example.bu_iot_mountain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class NoteDetailActivity extends AppCompatActivity {

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    private static final int MY_PERMISSION_REQUEST_LOCATION = 0;

    int noteidx = 1;//게시물 클릭시 받아오는 게시물 인덱스
    TextView title;
    TextView context;
    ImageView iimg;

    private static final String TAG = "NoteDetailActivity";//Log사용을 위해서 로그 태그 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        title = (TextView)findViewById(R.id.title);
        context = (TextView)findViewById(R.id.context);
        iimg = (ImageView)findViewById(R.id.iimg);


        myHelper=new LoginActivity.myDBHelper(this);


        permissionCheck();
    }


    private void permissionCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "승낙1 : ");
            //Manifest.permission.ACCESS_FINE_LOCATION 접근 승낙 상태 일때
            viewdeatilnote();
        } else { //Manifest.permission.ACCESS_FINE_LOCATION 접근 거절 상태 일때 //사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄운다.
            Log.d(TAG, "승낙2 : ");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_LOCATION) {
            Log.d(TAG, "승낙 후 1111 : ");
            viewdeatilnote();
        }
    }

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
            context.setText(cursor.getString(3));//내용데이터 텍스트뷰에 넣어주기
            //String UUrl = cursor.getString(3);
            //Toast.makeText(getApplicationContext(), "사진확인 ! "+UUrl, Toast.LENGTH_LONG).show();
            //iimg.setImageURI(Uri.parse(UUrl));

          /*  String fileName = cursor.getString(3);
            File file = new File(fileName);//절대값 경로*/

            Uri uri = Uri.parse("file://"+cursor.getString(3));
           // Log.d(TAG, "uriname : "+ uriname);
           // Uri uri = Uri.parse("file:/" + Environment.getExternalStorageDirectory() + uriname);
            Log.d(TAG, "uri : "+ uri);
            setImage(uri,iimg);
            //iimg.setImageURI(uri);
            //setImage(uri);
/*            String file_path = uri_path(uriname);
            iimg.setImageURI(uriname);*/
            //setImage(uriname);



            /*Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriname);
            //ImageView image = (ImageView)findViewById(R.id.imageView1); //배치해놓은 ImageView에 set image.setImageBitmap(image_bitmap);
            iimg.setImageBitmap(image_bitmap);*/

          //  Uri uri = Uri.parse("file:///" + Environment.getExternalStorageDirectory() + "/572/내그림/image_sample.jpg");
        //    iimg.setImageURI(uri);

        }
        //Toast.makeText(getApplicationContext(),"로그확인.idx: "+strNames+" id:"+strNumbers+" pw: "+strNumbers2+"",Toast.LENGTH_LONG).show();
        cursor.close();
        sqlDB.close();
    }

    private void setImage(Uri uri) {
        try{
            InputStream in = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            iimg.setImageBitmap(bitmap);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

}