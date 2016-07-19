package com.paullee.liyingheng.ashamednews;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paullee.liyingheng.ashamednews.application.GlobalApplication;
import com.paullee.liyingheng.ashamednews.callback.HttpPostCallback;
import com.paullee.liyingheng.ashamednews.http.HttpHandler;
import com.paullee.liyingheng.ashamednews.http.HttpThread;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, HttpPostCallback {

    private EditText username;
    private EditText password;
    private Button signupButton;
    private Button loginRedirectButton;
    private HttpHandler handler;

    private static final String LOG_TAG = SignupActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar signupToolbar = (Toolbar) findViewById(R.id.signup_toolbar);
        setSupportActionBar(signupToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(20);

        username = (EditText) findViewById(R.id.signup_item_username);
        password = (EditText) findViewById(R.id.signup_item_password);
        signupButton = (Button) findViewById(R.id.signup_button);
        loginRedirectButton = (Button) findViewById(R.id.signup_redirect_login);

        //Initialize handler
        handler = new HttpHandler();

        //Set OnClickListener
        signupButton.setOnClickListener(this);
        loginRedirectButton.setOnClickListener(this);

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
            case R.id.signup_button: {
                String user = username.getText().toString();
                String pwd = password.getText().toString();
                if (user != null && pwd != null) {
                    String value = "value={\"uname\":\"" + user + "\",\"upassword\":\"" + pwd + "\"}";
                    //Send Register Information to the Cloud
                    new HttpThread(this, GlobalApplication.URL_REGISTER, handler, value).start();
                    //Set Callback
                    handler.setPostCallback(this);
                } else {
                    Snackbar snackbar = Snackbar.make(null, "用户名或密码不能为空", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                break;
            }
            case R.id.signup_redirect_login: {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void sendbackPostCallback(String postReturn) {
        Log.d(LOG_TAG, "ActivityCallback:PostCallback:" + postReturn);
        if (postReturn.equalsIgnoreCase("ok")) {
            Toast.makeText(this, getString(R.string.SignUpActivity_toast_SignupSuccessful), Toast.LENGTH_LONG).show();
            finish();
        } else if (postReturn.equalsIgnoreCase("no")) {
            Toast.makeText(this, getString(R.string.SignUpActivity_toast_SignupFailed), Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.SignUpActivity_toast_SignupFailed), Toast.LENGTH_LONG).show();
        }
    }
}
