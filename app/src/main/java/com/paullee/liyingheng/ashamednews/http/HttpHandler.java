package com.paullee.liyingheng.ashamednews.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.paullee.liyingheng.ashamednews.Tweet;
import com.paullee.liyingheng.ashamednews.callback.HttpHandlerCallback;
import com.paullee.liyingheng.ashamednews.callback.HttpPostCallback;

import java.util.List;

/**
 * Created by liyingheng on 16/7/17.
 */

public class HttpHandler extends Handler{

    List<Tweet> parsedList;
    HttpHandlerCallback handlerCallback;
    HttpPostCallback postCallback;

    private static final String LOG_TAG = HttpHandler.class.getSimpleName();

    public HttpHandler()
    {

    }

    public void setHandlerCallback(HttpHandlerCallback handlerCallback) {
        this.handlerCallback = handlerCallback;
    }

    public void setPostCallback(HttpPostCallback postCallback) {
        this.postCallback = postCallback;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what)
        {
            case HttpUtil.NETWORK_ERROR_GENERAL:
            {
                //Failed to return parsed JSON
                handlerCallback.sendbackHandlerData(null, HttpUtil.NETWORK_ERROR_GENERAL);
                break;
            }
            case HttpUtil.NETWORK_OK:
            {
                //Successfully return parsed JSON to Fragment
                parsedList=(List<Tweet>)msg.obj;
                handlerCallback.sendbackHandlerData(parsedList, HttpUtil.NETWORK_OK);
                break;
            }
            case HttpUtil.NETWORK_POST_RETURN: {
                String postReturnValue = "";
                postReturnValue = (String) msg.obj;
                postCallback.sendbackPostCallback(postReturnValue);
                Log.d(LOG_TAG, "HandlerReceived:" + postReturnValue);
                break;
            }
            default:
        }

    }
}
