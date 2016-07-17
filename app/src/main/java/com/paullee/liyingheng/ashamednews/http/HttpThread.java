package com.paullee.liyingheng.ashamednews.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.paullee.liyingheng.ashamednews.Tweet;
import com.paullee.liyingheng.ashamednews.callback.HttpHandlerCallback;
import com.paullee.liyingheng.ashamednews.util.Utility;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by liyingheng on 16/7/17.
 */

public class HttpThread extends Thread {

    private static final String LOG_TAG=HttpThread.class.getSimpleName();

    private Context mContext;
    private String mUrl;
    public HttpHandler handler;
    private List<Tweet> parsedList;


    public HttpThread(Context context, String url,HttpHandler httpHandler) {
        mUrl=url;
        mContext=context;
        handler=httpHandler;
    }

    public void run() {
        try {

            String JsonString=HttpUtil.httpFetchData(mUrl,mContext);

            //Parse JSON
            Log.d(LOG_TAG,"JSON Message:"+JsonString.toString());
            Message msg=new Message();
            parsedList= Utility.ParseJSON(JsonString.toString());
            //Avoid returning NULL to Adapter
            if(parsedList.isEmpty())    //Parsed JSON Data is NULL
            {
                msg.what=HttpUtil.NETWORK_ERROR_GENERAL;
            }
            else
            {
                msg.what=HttpUtil.NETWORK_OK;
                msg.obj=parsedList;
            }
            handler.sendMessageDelayed(msg,0);
            //For Debug
            List<Tweet> tweetList=(List)msg.obj;
            Tweet tweet=tweetList.get(0);
            Log.d(LOG_TAG,tweet.getQVALUE());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,"URL Error!");
            Utility.setNetworkState(mContext,Utility.NETWORK_URL_ERROR);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            Log.e(LOG_TAG,"Failed to connect to the host!");
            Utility.setNetworkState(mContext,Utility.NETWORK_UNREACHABLE);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
            Log.e(LOG_TAG,"JSON Parsing Error!");
            Utility.setNetworkState(mContext,Utility.NETWORK_JSON_ERROR);
        }
    }

}
