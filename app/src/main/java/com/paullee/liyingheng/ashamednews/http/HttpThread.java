package com.paullee.liyingheng.ashamednews.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.paullee.liyingheng.ashamednews.Tweet;
import com.paullee.liyingheng.ashamednews.callback.HttpHandlerCallback;
import com.paullee.liyingheng.ashamednews.util.Utility;

import org.json.JSONException;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    private String postValue;


    public HttpThread(Context context, String url, HttpHandler httpHandler, String postValue) {
        mUrl=url;
        mContext=context;
        handler=httpHandler;
        this.postValue = postValue;
    }

    public void run() {
        if (postValue == null) {   //Execute GET method
            Message msg = new Message();
            try {
                String JsonString = HttpUtil.httpDoGet(mUrl, mContext);
                //Fetch Data From Cloud
//                Utility.setNetworkState(mContext, Utility.NETWORK_LOADING);
//                URL url = new URL(mUrl);
//                URLConnection conn = url.openConnection();
//                InputStream is = conn.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//                StringBuffer JsonString = new StringBuffer();
//                String str;
//                while ((str = reader.readLine()) != null) {
//                    JsonString.append(str);
//                }

                //Parse JSON
                Log.d(LOG_TAG, "JSON Message:" + JsonString.toString());
                parsedList = Utility.ParseJSON(JsonString.toString());
                //Avoid returning NULL to Adapter
                if (parsedList.isEmpty())    //Parsed JSON Data is NULL
                {
                    msg.what = HttpUtil.NETWORK_ERROR_GENERAL;
                } else {
                    msg.what = HttpUtil.NETWORK_OK;
                    msg.obj = parsedList;
                }
                handler.sendMessage(msg);
                //For Debug

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "URL Error!");
                Utility.setNetworkState(mContext, Utility.NETWORK_URL_ERROR);
                msg.what = HttpUtil.NETWORK_ERROR_GENERAL;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Failed to connect to the host!");
                Utility.setNetworkState(mContext, Utility.NETWORK_UNREACHABLE);
                msg.what = HttpUtil.NETWORK_ERROR_GENERAL;
                handler.sendMessage(msg);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "JSON Parsing Error!");
                Utility.setNetworkState(mContext, Utility.NETWORK_JSON_ERROR);
                msg.what = HttpUtil.NETWORK_ERROR_GENERAL;
                handler.sendMessage(msg);
            } finally {

            }
        } else    //Execute POST method
        {
            String result = HttpUtil.httpDoPost(mUrl, mContext, postValue);
            Message msg = new Message();
            msg.what = HttpUtil.NETWORK_POST_RETURN;
            msg.obj = result;
            handler.sendMessage(msg);
        }
    }
}
