package com.paullee.liyingheng.ashamednews;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paullee.liyingheng.ashamednews.application.GlobalApplication;

public class SettingActivity extends AppCompatActivity {

    //Declare Widget
    EditText serveripEditText;
    Button applyButton;

    //Declare Variable
    private String mServerIP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar settingToolbar = (Toolbar) findViewById(R.id.SettingsActivity_toolbar);
        setSupportActionBar(settingToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(20);

        //Init Widget
        serveripEditText = (EditText) findViewById(R.id.SettingsActivity_item_serverip);
        applyButton = (Button) findViewById(R.id.SettingsActivity_commit_button);

        //Init SharedPreference
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        mServerIP = sp.getString(getString(R.string.sp_key_ServerAddress), GlobalApplication.BASE_URL_IP);
        serveripEditText.setText(mServerIP);

        //Set OnclickListener
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SettingActivity.this);
                sp.edit().putString(getString(R.string.sp_key_ServerAddress), serveripEditText.getText().toString()).apply();
                finish();
            }
        });
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
}
