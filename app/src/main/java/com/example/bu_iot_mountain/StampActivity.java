package com.example.bu_iot_mountain;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import static com.example.bu_iot_mountain.MypageActivity.fruits;
import static com.example.bu_iot_mountain.MypageActivity.iimg;
import static com.example.bu_iot_mountain.MypageActivity.price;
import static com.example.bu_iot_mountain.QRcodeActivity.stamp_data;

public class StampActivity extends AppCompatActivity {
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fruits.addFirst("보탑사 스탬프");
                price.addFirst("(12월5일까지) 기념품관 20% 할인 쿠폰");
                iimg.addFirst("stamp1");

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

