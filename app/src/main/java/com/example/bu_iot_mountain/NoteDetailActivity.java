package com.example.bu_iot_mountain;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

    int noteidx = 2;//게시물 클릭시 받아오는 게시물 인덱스
    TextView title;
    TextView context;
    ImageView iimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        title = (TextView)findViewById(R.id.title);
        context = (TextView)findViewById(R.id.context);
        iimg = (ImageView)findViewById(R.id.iimg);


        myHelper=new LoginActivity.myDBHelper(this);
        viewdeatilnote();

    }

    public void viewdeatilnote(){
        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;
        cursor=sqlDB.rawQuery("SELECT * FROM note WHERE "+noteidx+";",null);//select문 실행

        while (cursor.moveToNext()){//다음 레코드가 있을동안 수행
            title.setText(cursor.getString(1));//제목데이터 텍스트뷰에 넣어주기
            context.setText(cursor.getString(3));//내용데이터 텍스트뷰에 넣어주기
            //String UUrl = cursor.getString(3);
            //Toast.makeText(getApplicationContext(), "사진확인 ! "+UUrl, Toast.LENGTH_LONG).show();
            //iimg.setImageURI(Uri.parse(UUrl));

            String fileName = cursor.getString(3);
            File file = new File(fileName);//절대값 경로
            Uri uriname = Uri.parse(cursor.getString(3));
            setImage(uriname);

           /* Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            Toast.makeText(getApplicationContext(), "사진확인 ! "+bitmap, Toast.LENGTH_LONG).show();
            iimg.setImageBitmap(bitmap);*/

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