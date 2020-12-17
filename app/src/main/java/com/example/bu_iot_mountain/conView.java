package com.example.bu_iot_mountain;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class conView extends LinearLayout {
    TextView fruitTextView;
    TextView priceTextView;


    public conView(Context context) {
        super(context);
        inflatetion_init(context);

        fruitTextView = (TextView)findViewById(R.id.c_name);
        //priceTextView = (TextView)findViewById(R.id.c_address);

    }

    private void inflatetion_init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.conitem,this,true);
    }

    public void setFruit(String fruit){
        fruitTextView.setText(fruit);
    }
    public void setPrice(String price){
       // priceTextView.setText(price);
    }
}