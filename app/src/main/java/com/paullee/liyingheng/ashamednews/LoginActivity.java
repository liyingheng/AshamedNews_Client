package com.paullee.liyingheng.ashamednews;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.paullee.liyingheng.ashamednews.application.GlobalApplication;
import com.paullee.liyingheng.ashamednews.callback.HttpPostCallback;
import com.paullee.liyingheng.ashamednews.http.HttpHandler;
import com.paullee.liyingheng.ashamednews.http.HttpThread;
import com.paullee.liyingheng.ashamednews.util.Utility;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, HttpPostCallback {


    //Widget
    Button signupRedirectButton;
    Button loginButton;
    EditText usernameEditText;
    EditText passwordEditText;


    //Variable
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    HttpHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar loginToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(loginToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(20);

        //Initialize Widget
        signupRedirectButton = (Button) findViewById(R.id.login_redirect_signup);
        usernameEditText = (EditText) findViewById(R.id.login_item_username);
        passwordEditText = (EditText) findViewById(R.id.login_item_password);
        loginButton = (Button) findViewById(R.id.login_login_button);

        //Set handler
        handler = new HttpHandler();

        //Set OnClickListener
        signupRedirectButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

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
            case R.id.login_redirect_signup: {
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.login_login_button: {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if ((username != null) && (username.equals("")) && (password != null) && (password.equals("")))
                    ;
                {
                    String value = "value={\"uname\":\"" + username + "\",\"upassword\":\"" + password + "\"}";
                    //Commit to Cloud
                    new HttpThread(this, GlobalApplication.URL_LOGIN, handler, value).start();
                    handler.setPostCallback(this);
                    Log.d(LOG_TAG, "login_login_button Pressed!");
                    break;
                }
            }
        }
    }

    @Override
    public void sendbackPostCallback(String postReturn) {
        Log.d(LOG_TAG, postReturn);

        if (postReturn.equalsIgnoreCase("nopass")) {
            Toast.makeText(this, getString(R.string.LoginActivity_toast_loginFailed), Toast.LENGTH_LONG).show();
        } else {
            try {
                UserInfo userInfo = Utility.ParseUsername(postReturn);
                if (!userInfo.getUNAME().equalsIgnoreCase("")) {
                    Toast.makeText(this, getString(R.string.LoginActivity_toast_loginSuccess) + " " + userInfo.getUNAME(), Toast.LENGTH_LONG).show();
                    GlobalApplication.userInfo = userInfo;
                    finish();
                }

            } catch (JSONException ex) {
                ex.printStackTrace();
                Toast.makeText(this, getString(R.string.LoginActivity_toast_loginUnknown), Toast.LENGTH_LONG).show();
            }
        }
    }
}
