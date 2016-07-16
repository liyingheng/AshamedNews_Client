package com.paullee.liyingheng.ashamednews;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by liyingheng on 16/7/14.
 */

public class FeaturedAdapter extends BaseAdapter {

    private static final String LOG_TAG=FeaturedAdapter.class.getSimpleName();

    Context mContext;
    List<Tweet> mTweetList;

    ImageView avatarImageView;
    TextView nicknameTextView;
    TextView userIdTextView;
    TextView timestampTextView;
    TextView contentTextView;

    public FeaturedAdapter(Context context,List<Tweet> tweetList) {
        super();
        mContext=context;
        mTweetList=tweetList;
        if(!mTweetList.isEmpty())
        {
            Log.d(LOG_TAG,"mTweetList isn't null");
        }
    }



    @Override
    public int getCount() {
        return mTweetList.size();
        //Log.d(LOG_TAG,String.valueOf(mTweetList.size()));

    }

    @Override
    public Object getItem(int position) {
        return mTweetList.get(position);
        //return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
        //return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet=mTweetList.get(position);

        convertView=View.inflate(mContext,R.layout.featured_post_list_item,null);

        avatarImageView=(ImageView)convertView.findViewById(R.id.hot_item_avatar);
        nicknameTextView=(TextView)convertView.findViewById(R.id.hot_item_nickname);
        userIdTextView=(TextView)convertView.findViewById(R.id.hot_item_userId);
        timestampTextView=(TextView)convertView.findViewById(R.id.hot_item_timestamp);
        contentTextView=(TextView)convertView.findViewById(R.id.hot_item_content);

        nicknameTextView.setText(tweet.getUNAME());
        userIdTextView.setText("@"+tweet.getUSERID());
        contentTextView.setText(tweet.getQVALUE());
        String imageURL="http://172.17.129.220/qiubai/Userimg/"+tweet.getUHEAD();

        Glide.with(mContext)
                .load(imageURL)
                .into(avatarImageView);
        //Log.d(LOG_TAG,imageURL);
        //Log.d(LOG_TAG,"Content:"+tweet.getQVALUE());

        return convertView;
    }


}
