package com.paullee.liyingheng.ashamednews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.paullee.liyingheng.ashamednews.application.GlobalApplication;
import com.paullee.liyingheng.ashamednews.fragment.*;
import com.paullee.liyingheng.ashamednews.callback.*;
import com.paullee.liyingheng.ashamednews.util.Utility;

public class MainActivity extends FragmentActivity implements View.OnClickListener,ILoading,SharedPreferences.OnSharedPreferenceChangeListener {

    //MainActivity Variable
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    Bundle fragmentTypeBundle;


    //Widget for global
    FloatingActionButton addFAB;
    Snackbar networkStatusSnackbar;

    //Widget for Toolbar
    ImageView sideIconImageView;
    ImageView postNewIconImageView;
    Button hotTabButton;
    Button newTabButton;
    Button textTabButton;


    //Tab Fragment
    BaseTabFragment baseTabFragment;
    HotTabFragment hotTabFragment;
    NewTabFragment newTabFragment;
    TextTabFragment textTabFragment;
    FeaturedTabFragment featuredTabFragment;
    HistoryTabFragment historyTabFragment;
    NearbyTabFragment nearbyTabFragment;
    PictureTabFragment pictureTabFragment;
    PrivateMessageTabFragment privateMessageTabFragment;

    //ProgressBar
    LinearLayout loadingLayout;
    TextView noDataTextView;

    //NavBar & NavBar Fragment
    SlidingMenu navBar;
    TextView loginTextView;
    RelativeLayout hotNavTab;
    RelativeLayout featuredNavTab;
    RelativeLayout pictureNavTab;
    RelativeLayout historyNavTab;
    RelativeLayout logoutNavTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    //Initialize All Widget and Fragment
    private void initView()
    {
        //Initialize global widget
        addFAB = (FloatingActionButton) findViewById(R.id.global_addNewPost_FAButton);

        //Initialize Toolbar Widget
        sideIconImageView=(ImageView)findViewById(R.id.toolbar_SideIcon_ImageView);
        postNewIconImageView=(ImageView)findViewById(R.id.toolbar_PostNewIcon_ImageView);
        hotTabButton=(Button)findViewById(R.id.toolbar_Hot_Button);
        newTabButton=(Button)findViewById(R.id.toolbar_New_Button);
        textTabButton=(Button)findViewById(R.id.toolbar_Text_Button);

        //Initialize ProgressBar Widget
//        loadingLayout=(LinearLayout)findViewById(R.id.global_Loading_LinearLayout);
        noDataTextView=(TextView)findViewById(R.id.global_noData_TextView);

        //Initialize NavBar
        View navBarView=View.inflate(this,R.layout.navbar,null);
        navBar=new SlidingMenu(this);
        navBar.setMenu(navBarView);
        navBar.setMode(SlidingMenu.LEFT);
        navBar.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        navBar.setFadeDegree(0.35f);
        navBar.setBehindOffsetRes(R.dimen.NavBar_behindOffset);
        navBar.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        //Initialize Tab in NavBar
        loginTextView = (TextView) findViewById(R.id.navbar_nickname_TextView);
        hotNavTab = (RelativeLayout) findViewById(R.id.navbar_tab_hot);
        featuredNavTab = (RelativeLayout) findViewById(R.id.navbar_tab_featured);
        pictureNavTab = (RelativeLayout) findViewById(R.id.navbar_tab_picture);
        historyNavTab = (RelativeLayout) findViewById(R.id.navbar_tab_history);
        logoutNavTab = (RelativeLayout) findViewById(R.id.navbar_tab_logout);


        //Inflate HotTabFragment (Default Tab)
        hotTabFragment=new HotTabFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.global_FragmentContainer,hotTabFragment).commit();
        hotTabButton.setBackgroundResource(R.drawable.side_menu_background_active);



        //Initialize Callback Interface


        //Set Button OnClick Event
        hotTabButton.setOnClickListener(this);
        newTabButton.setOnClickListener(this);
        textTabButton.setOnClickListener(this);
        sideIconImageView.setOnClickListener(this);
        hotNavTab.setOnClickListener(this);
        addFAB.setOnClickListener(this);
        loginTextView.setOnClickListener(this);
        logoutNavTab.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.toolbar_Hot_Button:
            {

                if(hotTabFragment==null) {
                    hotTabFragment = new HotTabFragment();
                }
                ClearToolbarTabBackground();
                hotTabButton.setBackgroundResource(R.drawable.side_menu_background_active);
                getSupportFragmentManager().beginTransaction().replace(R.id.global_FragmentContainer, hotTabFragment).commit();
                break;
            }
            case R.id.toolbar_New_Button:
            {
                if(newTabFragment==null) {
                    newTabFragment = new NewTabFragment();
                }
                ClearToolbarTabBackground();
                newTabButton.setBackgroundResource(R.drawable.side_menu_background_active);
                getSupportFragmentManager().beginTransaction().replace(R.id.global_FragmentContainer,newTabFragment).commit();
                break;
            }
            case R.id.toolbar_Text_Button:
            {
                if(textTabFragment==null) {
                    textTabFragment = new TextTabFragment();
                }
                ClearToolbarTabBackground();
                textTabButton.setBackgroundResource(R.drawable.side_menu_background_active);
                getSupportFragmentManager().beginTransaction().replace(R.id.global_FragmentContainer,textTabFragment).commit();
                break;
            }
            case R.id.toolbar_SideIcon_ImageView:
            {
                Log.d(LOG_TAG,"SideIconPressed!");
                navBar.toggle();
                if (GlobalApplication.userInfo != null) {
                    loginTextView.setText(GlobalApplication.userInfo.getUNAME());
                    logoutNavTab.setVisibility(View.VISIBLE);
                } else {
                    loginTextView.setText(getString(R.string.navbar_nickname_default));
                }
                break;
            }
            case R.id.navbar_nickname_TextView: {
                if (GlobalApplication.userInfo != null) {
                    Toast.makeText(this, getString(R.string.navbar_nickname_toast_logined), Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                navBar.toggle();
                break;
            }
            case R.id.navbar_tab_hot: {
                if (hotTabFragment == null) {
                    hotTabFragment = new HotTabFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.global_FragmentContainer, hotTabFragment).commit();
                hotTabFragment.setLoadingInterface(this);
                navBar.toggle();
                break;
            }
            case R.id.navbar_tab_logout: {
                if (GlobalApplication.userInfo != null) {
                    GlobalApplication.userInfo = null;
                    Toast.makeText(this, getString(R.string.navbar_logout_toast), Toast.LENGTH_LONG).show();
                    navBar.toggle();
                    logoutNavTab.setVisibility(View.GONE);
                }
                break;
            }
            case R.id.global_addNewPost_FAButton: {
                if (GlobalApplication.userInfo != null) {
                    Intent intent = new Intent(this, PostNewActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, getString(R.string.LoginActivity_toast_loginFirst), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            }
        }
    }

    @Override
    public void LoadingSuccess() {
        loadingLayout.setVisibility(View.GONE);
        Log.d(LOG_TAG,"LoadingSuccess() Loaded");
    }

    @Override
    public void LoadingFailed() {
        Log.d(LOG_TAG,"LoadingFailed() Loaded");
        loadingLayout.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(LOG_TAG,"onSharedPreferenceChanged.key="+key);
        if(key==getString(R.string.sp_key_NetworkState))
        {
            int status=sharedPreferences.getInt(getString(R.string.sp_key_NetworkState), Utility.NETWORK_OK);
            Log.d(LOG_TAG,"NetworkStatePreferenceChanged:"+status);
            switch (status)
            {
                case Utility.NETWORK_UNREACHABLE: {
                    networkStatusSnackbar = Snackbar.make(findViewById(R.id.global_CoordinatorLayout), getString(R.string.net_state_NetworkUnreachable), Snackbar.LENGTH_LONG);
                    networkStatusSnackbar.show();
                }
//                case Utility.NETWORK_JSON_ERROR:{
//                    networkStatusSnackbar = Snackbar.make(findViewById(R.id.global_CoordinatorLayout), getString(R.string.net_state_JsonParseError), Snackbar.LENGTH_LONG);
//                    networkStatusSnackbar.show();
//                }
            }
        }
    }

    private void ClearToolbarTabBackground() {
        hotTabButton.setBackgroundResource(0);
        newTabButton.setBackgroundResource(0);
        textTabButton.setBackgroundResource(0);
    }
}

