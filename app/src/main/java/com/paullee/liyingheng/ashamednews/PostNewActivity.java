package com.paullee.liyingheng.ashamednews;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.paullee.liyingheng.ashamednews.application.GlobalApplication;
import com.paullee.liyingheng.ashamednews.callback.HttpPostCallback;
import com.paullee.liyingheng.ashamednews.http.HttpHandler;
import com.paullee.liyingheng.ashamednews.http.HttpThread;

public class PostNewActivity extends AppCompatActivity implements View.OnClickListener, HttpPostCallback {

    //Initialize Widget
    ImageView sendImageView;
    EditText contentEditText;

    //Initialize Variable
    HttpHandler handler;
    private static final String LOG_TAG = PostNewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new);
        Toolbar postnewToolbar = (Toolbar) findViewById(R.id.postnew_toolbar);
        setSupportActionBar(postnewToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(4);

        //Initialize Callback
        handler = new HttpHandler();
        handler.setPostCallback(this);

        //Initialize view
        sendImageView = (ImageView) findViewById(R.id.postnew_send_ImageView);
        contentEditText = (EditText) findViewById(R.id.postnew_content);

        //Set OnClickListener
        sendImageView.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home: {
                finish();
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.postnew_send_ImageView: {
                String sendContent = contentEditText.getText().toString();
                if ((sendContent != null) && (!sendContent.trim().equalsIgnoreCase(""))) {
                    Log.d(LOG_TAG, "send pressed! Content:" + sendContent);
                    //Commit content to server
                    String uid = GlobalApplication.userInfo.getUSERID();
                    String tid = "3";//糗事的类型 1代表干货  2代表嫩草  3代表文字
                    String qimg = "";//图片
                    String value = "value={\"uid\":\"" + uid + "\"," + "\"tid\":\"" + tid + "\","
                            + "\"qimg\":\"" + qimg + "\"," + "\"qvalue\":\"" + sendContent
                            + "\"," + "\"qlike\":\"0\"," + "\"qunlike\":\"0\"}";
                    Log.d(LOG_TAG, "send pressed! value:" + value);
                    new HttpThread(this, GlobalApplication.URL_POSTNEW, handler, value).start();
                    break;
                }
            }
        }
    }

    @Override
    public void sendbackPostCallback(String postReturn) {
        Toast.makeText(this, postReturn, Toast.LENGTH_LONG).show();

    }
}
