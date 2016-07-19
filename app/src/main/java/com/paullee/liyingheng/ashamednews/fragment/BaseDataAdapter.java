package com.paullee.liyingheng.ashamednews.fragment;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.paullee.liyingheng.ashamednews.R;
import com.paullee.liyingheng.ashamednews.Tweet;
import com.paullee.liyingheng.ashamednews.application.GlobalApplication;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by liyingheng on 16/7/14.
 */

public class BaseDataAdapter extends BaseAdapter {

    private static final String LOG_TAG = BaseDataAdapter.class.getSimpleName();

    Context mContext;
    List<Tweet> mTweetList;

    ImageView avatarImageView;
    ImageView tpicImageView;
    TextView nicknameTextView;
    TextView userIdTextView;
    TextView timestampTextView;
    TextView contentTextView;
    TextView likeTextView;
    TextView dislikeTextView;
    TextView commentTextView;

    public BaseDataAdapter(Context context) {
        mContext = context;
    }

    public BaseDataAdapter(Context context, List<Tweet> tweetList) {
        super();
        mContext=context;
        mTweetList=tweetList;
        if(!mTweetList.isEmpty())
        {
            Log.d(LOG_TAG,"mTweetList isn't null");
        }
    }

    public void setDataSource(List<Tweet> dataSource) {
        mTweetList = dataSource;
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

        convertView = View.inflate(mContext, R.layout.data_post_list_item, null);

        avatarImageView=(ImageView)convertView.findViewById(R.id.hot_item_avatar);
        tpicImageView = (ImageView) convertView.findViewById(R.id.hot_item_pic);
        nicknameTextView=(TextView)convertView.findViewById(R.id.hot_item_nickname);
        userIdTextView=(TextView)convertView.findViewById(R.id.hot_item_userId);
        timestampTextView=(TextView)convertView.findViewById(R.id.hot_item_timestamp);
        likeTextView = (TextView) convertView.findViewById(R.id.hot_item_like);
        dislikeTextView = (TextView) convertView.findViewById(R.id.hot_item_dislike);
        commentTextView = (TextView) convertView.findViewById(R.id.hot_item_comment);
        contentTextView=(TextView)convertView.findViewById(R.id.hot_item_content);

        nicknameTextView.setText(tweet.getUNAME());
        userIdTextView.setText("@"+tweet.getUSERID());
        contentTextView.setText(tweet.getQVALUE());
        likeTextView.setText(tweet.getQLIKE());
        dislikeTextView.setText(tweet.getQUNLIKE());
        commentTextView.setText(tweet.getQSHARE());

        String avatarURL = GlobalApplication.URL_BASE_AVATAR + tweet.getUHEAD();
        String imageURL = GlobalApplication.URL_BASE_PIMG + tweet.getQIMG();

        Glide.with(mContext)
                .load(avatarURL)
                .into(avatarImageView);

        Glide.with(mContext)
                .load(imageURL)
                .into(tpicImageView);
        //Log.d(LOG_TAG,imageURL);
        //Log.d(LOG_TAG,"Content:"+tweet.getQVALUE());

        return convertView;
    }


}
