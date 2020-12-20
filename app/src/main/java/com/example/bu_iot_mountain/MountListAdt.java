package com.example.bu_iot_mountain;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MountListAdt extends BaseAdapter {

    private Context context;
    private List<Notice> noticeList;

    public MountListAdt(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.myup, null);
        TextView noticeText=(TextView)v.findViewById(R.id.upcon);
//        TextView nameText=(TextView)v.findViewById(R.id.mnames);
        TextView dateText=(TextView)v.findViewById(R.id.dateText);


        noticeText.setText(noticeList.get(i).getNotice());
        //nameText.setText(noticeList.get(i).getName());
        dateText.setText(noticeList.get(i).getDate()+"번 게시물 정복완료");

        v.setTag(noticeList.get(i).getNotice());
        return v;


    }
}
