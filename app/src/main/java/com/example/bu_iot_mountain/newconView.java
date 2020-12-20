package com.example.bu_iot_mountain;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class newconView extends LinearLayout {
    TextView fruitTextView;
    TextView priceTextView;
    ImageView iimgImgView;

    private static Context context;


    public newconView(Context context) {
        super(context);
        inflatetion_init(context);

        fruitTextView = (TextView)findViewById(R.id.c_name);
        priceTextView = (TextView)findViewById(R.id.c_address);
        iimgImgView = (ImageView)findViewById(R.id.stamp1);

    }

    private void inflatetion_init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.newconlist,this,true);
    }

    public void setFruit(String fruit){
        fruitTextView.setText(fruit);
    }
    public void setPrice(String price){
        priceTextView.setText(price);
    }
    public void setiimg(String iimg){

        //String pak_name = context.getPackageName();
        //int resID = context.getResources().getIdentifier(iimg, "drawable",pak_name);
        //iimgImgView.setImageResource(resID);
    }
}