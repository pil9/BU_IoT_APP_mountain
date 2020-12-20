package com.example.bu_iot_mountain;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import static com.example.bu_iot_mountain.LoginActivity.useridx;
import static com.example.bu_iot_mountain.MypageActivity.fruits;
import static com.example.bu_iot_mountain.MypageActivity.iimg;
import static com.example.bu_iot_mountain.MypageActivity.price;
import static com.example.bu_iot_mountain.QRcodeActivity.stamp_data;

public class StampActivity extends AppCompatActivity {

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    ImageView imageView;
    Button button;
    TextView textview;
//    private TextView Textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamp);
        textview = (TextView)findViewById(R.id.stamp_name);
        textview.setText(stamp_data);
        Log.d("aaa",stamp_data);
//        imageView = (ImageView)findViewById(R.id.imageview);
        button = (Button)findViewById(R.id.button);

        myHelper=new LoginActivity.myDBHelper(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sqlDB = myHelper.getReadableDatabase();
                //Cursor cursor2;
                fruits.clear();
                price.clear();
                iimg.clear();

                sqlDB.execSQL("INSERT INTO stamp VALUES(null,'" + useridx + "','"
                        + stamp_data + "','산장 20% 할인');");
                //insert문으로 인증게시물 추가
                Toast.makeText(getApplicationContext(), "스탬프 획득! ", Toast.LENGTH_LONG).show();

                sqlDB.close();

                //fruits.addFirst("보탑사 스탬프");
                //price.addFirst("(12월5일까지) 기념품관 20% 할인 쿠폰");
                //iimg.addFirst("stamp1");

                finish();

//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
//                float scale = (float) (1024/(float)bitmap.getWidth());
//                int image_w = (int) (bitmap.getWidth() * scale);
//                int image_h = (int) (bitmap.getHeight() * scale);
//                Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
//                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//
//                Intent intent = new Intent(StampActivity.this, StampSecondActivity.class);
//
//                startActivity(intent);
            }
        });


    }
}

