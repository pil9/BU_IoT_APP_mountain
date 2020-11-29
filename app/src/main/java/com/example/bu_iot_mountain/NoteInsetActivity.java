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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.GeolocationPermissions;

import net.daum.mf.map.api.MapView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.bu_iot_mountain.LoginActivity.useridx;


public class NoteInsetActivity extends AppCompatActivity {

    private final String tableName = "note";

    private static final int MY_PERMISSION_REQUEST_LOCATION = 0;
    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    EditText title;
    EditText context;

    Spinner s;

    private MapView mapView;
    private WebView mWebView;
    private WebSettings mWebSettings; //웹뷰세팅

    private static final String TAG = "NoteInsetActivity";//Log사용을 위해서 로그 태그 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_inset);
        mWebView = (WebView) findViewById(R.id.webmap);

        title = (EditText) findViewById(R.id.title);
        context = (EditText) findViewById(R.id.context);

        myHelper = new LoginActivity.myDBHelper(this);


        sqlDB = myHelper.getReadableDatabase();//읽기모드로 디비 오픈
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM mount ;", null);//select문 실행
        List plist = new ArrayList();
        while (cursor.moveToNext()) {
            String eng = cursor.getString(1);
            plist.add(eng);
        }

        s = (Spinner) findViewById(R.id.spinner_m);
        ArrayAdapter dataA = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, plist);
        s.setAdapter(dataA);

        permissionCheck();

    }

    public void initWebView() {

        mWebSettings = mWebView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부

        mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                // Set that we want geolocation perms for this origin
                callback.invoke(origin, true, false);
            }

        });


        mWebSettings.setGeolocationEnabled(true);
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebSettings.setDomStorageEnabled(true);
        Log.d(TAG, "웹뷰 시작 : ");
        mWebView.loadUrl("file:///android_asset/mapweb.html"); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작



    }

    private void permissionCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "승낙1 : ");
            //Manifest.permission.ACCESS_FINE_LOCATION 접근 승낙 상태 일때
            initWebView();
        } else { //Manifest.permission.ACCESS_FINE_LOCATION 접근 거절 상태 일때 //사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄운다.
            Log.d(TAG, "승낙2 : ");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_LOCATION) {
            Log.d(TAG, "승낙 후 1111 : ");
            initWebView();
        }
    }


    public void pick(View v) {
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

    public void insertnote(View v) {
        String mountname = s.getSelectedItem().toString();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM mount WHERE name='" + mountname + "'   ;", null);//select문 실행
        cursor.moveToFirst();
        int mountidx = cursor.getInt(0);

        Log.d(TAG, "디비 넣기  ");

        sqlDB = myHelper.getWritableDatabase();//쓰기전용db열기

        Log.d(TAG, "tableName : " + tableName);
        Log.d(TAG, "title : " + title.getText().toString());
        Log.d(TAG, "context : " + context.getText().toString());
        Log.d(TAG, "uri : " + uri.toString());
        Log.d(TAG, "useridx : " + useridx);
        Log.d(TAG, "mountidx : " + mountidx);

        sqlDB.execSQL("INSERT INTO " + tableName + " VALUES(null,'" + title.getText().toString() + "','"
                + context.getText().toString() + "','" + uri.toString() + "'," + useridx + "," + mountidx + ");");
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

    public void onClickButton1(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);//미디어 저장소 접근
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {//사진 선택 완료
            uri = data.getData();//사진 저장 url주소 변수에 저장
            TextView igroup1 = (TextView) findViewById(R.id.iv_view);
            igroup1.setText(uri.toString());//url주소 textview로 표시
        }
    }

    private void setImage(Uri uri) {//imageview를 선택한 사진으로 변경
        try {
            InputStream in = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void gomypage(View v) {
        Intent i1;
        i1 = new Intent(this, MypageActivity.class);
        startActivity(i1);
    }


}
