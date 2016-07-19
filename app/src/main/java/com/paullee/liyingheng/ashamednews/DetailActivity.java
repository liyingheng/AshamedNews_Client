package com.paullee.liyingheng.ashamednews;

import android.content.Intent;
import android.media.Image;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.paullee.liyingheng.ashamednews.application.GlobalApplication;

public class DetailActivity extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;
    private static final String ASHAMED_HASHTAG = "糗事百科 THE KEY专版";
    private String shareContent;
    Tweet mTweet;

    //Declare Widget
    ImageView avatarImageView;
    ImageView maincontentImageView;
    TextView nicknameTextView;
    TextView useridTextView;
    TextView maincontentTextView;


    public DetailActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Initialize Widget
        initView();

        //Receive intent
        Intent intent = getIntent();
        mTweet = (Tweet) intent.getSerializableExtra(getString(R.string.intent_tabToDetail));

        //SetContent
        setWidgetText();

    }

    private void initView() {
        avatarImageView = (ImageView) findViewById(R.id.detail_item_avatar);
        maincontentImageView = (ImageView) findViewById(R.id.detail_item_pic);
        nicknameTextView = (TextView) findViewById(R.id.detail_item_nickname);
        useridTextView = (TextView) findViewById(R.id.detail_item_userId);
        maincontentTextView = (TextView) findViewById(R.id.detail_item_content);
    }

    private void setWidgetText() {
        Glide.with(this)
                .load(GlobalApplication.URL_BASE_AVATAR + mTweet.getUHEAD())
                .into(avatarImageView);
        Glide.with(this)
                .load(GlobalApplication.URL_BASE_PIMG + mTweet.getQIMG())
                .into(maincontentImageView);
        nicknameTextView.setText(mTweet.getUNAME());
        useridTextView.setText("@" + mTweet.getUID());
        maincontentTextView.setText(mTweet.getQVALUE());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem item = menu.findItem(R.id.detail_share_action);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(DetailShareIntent());
        }
        return true;
    }


    private Intent DetailShareIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareContent + ASHAMED_HASHTAG);
        return intent;
    }
}
