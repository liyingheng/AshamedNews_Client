package com.paullee.liyingheng.ashamednews.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bumptech.glide.util.Util;
import com.paullee.liyingheng.ashamednews.FeaturedAdapter;
import com.paullee.liyingheng.ashamednews.R;
import com.paullee.liyingheng.ashamednews.Tweet;
import com.paullee.liyingheng.ashamednews.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class HotTabFragment extends BaseTabFragment {

    private static final String LOG_TAG=HotTabFragment.class.getSimpleName();

    SwipeRefreshLayout featuredRefreshLayout;
    //Widget for MainActivity
    ListView dataListView;
    FeaturedAdapter featuredAdapter;

    public HotTabFragment() {
        // Required empty public constructor
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            List<Tweet> mTweetList;
            mTweetList=(List)msg.obj;
            if (!mTweetList.isEmpty()) {
                Log.d(LOG_TAG, "tweetList in Handler isn't empty");
            }

            featuredAdapter=new FeaturedAdapter(getActivity(),mTweetList);
            dataListView.setAdapter(featuredAdapter);

            if(!mTweetList.isEmpty())   //Finish refreshing
            {
                featuredRefreshLayout.setRefreshing(false);
            }



            Tweet tweet=mTweetList.get(2);
            Log.d(LOG_TAG,tweet.getQVALUE());
            Log.d(LOG_TAG,"Size:"+mTweetList.size());
            //featuredAdapter.setDataSource(tweetList);
            //Log.d(LOG_TAG,(String)msg.obj);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_hot_tab, container, false);

        fetchJSONString("http://172.17.129.128/qiubai/chuanyue.php?start=0&end=5");
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        //Initialize Widget
        dataListView=(ListView)view.findViewById(R.id.hot_hotNews_ListView);
        //Respond to Vertical Swipe Refresh
        featuredRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.hot_RefreshLayout);
        featuredRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchJSONString("http://172.17.129.128/qiubai/chuanyue.php?start=0&end=5");
            }
        });
        featuredRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        featuredRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    public void fetchJSONString(final String strUrl)
    {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                //List<Tweet> tweetList=new ArrayList<>();
                try {
                    //featuredRefreshLayout.setRefreshing(true);
                    Utility.setNetworkState(getActivity(),Utility.NETWORK_LOADING);
                    URL url=new URL(strUrl);
                    URLConnection conn=url.openConnection();
                    InputStream is=conn.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                    StringBuffer buffer=new StringBuffer();

                    String str;
                    while((str=reader.readLine())!=null)
                    {
                        buffer.append(str);
                    }

                    //Parse JSON
                    Log.d(LOG_TAG,"JSON Message:"+buffer.toString());
                    Message msg=new Message();
                    msg.obj= ParseJSON(buffer.toString());
                    //msg.obj="Hello";
                    //Display Data (send List to Handler)

                    List<Tweet> tweetList=(List)msg.obj;
                    Tweet tweet=tweetList.get(0);
                    Log.d(LOG_TAG,tweet.getQVALUE());
                    handler.sendMessageDelayed(msg,0);

                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG,"URL Error!");
                    Utility.setNetworkState(getActivity(),Utility.NETWORK_URL_ERROR);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                    Log.e(LOG_TAG,"Failed to connect to the host!");
                    Utility.setNetworkState(getActivity(),Utility.NETWORK_UNREACHABLE);
                }

                catch(JSONException e)
                {
                    e.printStackTrace();
                    Log.e(LOG_TAG,"JSON Parsing Error!");
                    Utility.setNetworkState(getActivity(),Utility.NETWORK_JSON_ERROR);
                }

            }
        });
        thread.start();
    }

    public List<Tweet> ParseJSON(String strJSON)
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




