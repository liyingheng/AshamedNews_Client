package com.paullee.liyingheng.ashamednews.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.paullee.liyingheng.ashamednews.R;
import com.paullee.liyingheng.ashamednews.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyingheng on 16/7/16.
 */

public class Utility {

    private static final String LOG_TAG=Utility.class.getSimpleName();

    //Network State Constant
    public static final int NETWORK_OK=200;
    public static final int NETWORK_LOADING=100;
    public static final int NETWORK_URL_ERROR=101;
    public static final int NETWORK_UNREACHABLE=102;
    public static final int NETWORK_JSON_ERROR=103;


    public static void setNetworkState(Context context, int networkState)
    {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(context.getString(R.string.sp_key_NetworkState),networkState);
        editor.apply();
    }

    public static List<Tweet> ParseJSON(String strJSON)
            throws JSONException
    {
        JSONArray allTweetJsonArray;
        List<Tweet> tweetList=new ArrayList<>();
        try {
            allTweetJsonArray = new JSONArray(strJSON);
            for(int i=0;i<allTweetJsonArray.length();i++)
            {
                JSONObject eachTweetJSON=allTweetJsonArray.getJSONObject(i);
                Tweet tweet=new Tweet();

                tweet.setQID(eachTweetJSON.getString("QID"));
                tweet.setUID(eachTweetJSON.getString("UID"));
                tweet.setTID(eachTweetJSON.getString("TID"));
                tweet.setQIMG(eachTweetJSON.getString("QIMG"));
                tweet.setQVALUE(eachTweetJSON.getString("QVALUE"));
                tweet.setQLIKE(eachTweetJSON.getString("QLIKE"));
                tweet.setQUNLIKE(eachTweetJSON.getString("QUNLIKE"));
                tweet.setQSHARE(eachTweetJSON.getString("QSHARE"));
                tweet.setISCHECKDE(eachTweetJSON.getString("ISCHECKDE"));
                tweet.setUSERID(eachTweetJSON.getString("USERID"));
                tweet.setUNAME(eachTweetJSON.getString("UNAME"));
                tweet.setUHEAD(eachTweetJSON.getString("UHEAD"));
                tweet.setTNAME(eachTweetJSON.getString("TNAME"));
                tweetList.add(tweet);
            }
            if(!tweetList.isEmpty())
            {
                Log.d(LOG_TAG,"ParseJson tweetList isn't empty");
            }
            return tweetList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
