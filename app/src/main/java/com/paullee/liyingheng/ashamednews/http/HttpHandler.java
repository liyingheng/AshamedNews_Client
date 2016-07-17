package com.paullee.liyingheng.ashamednews.http;

import android.os.Handler;
import android.os.Message;

import com.paullee.liyingheng.ashamednews.Tweet;
import com.paullee.liyingheng.ashamednews.callback.HttpHandlerCallback;

import java.util.List;

/**
 * Created by liyingheng on 16/7/17.
 */

public class HttpHandler extends Handler{

    List<Tweet> parsedList;
    HttpHandlerCallback handlerCallback;

    public HttpHandler()
    {

    }

    public void setHandlerCallback(HttpHandlerCallback handlerCallback) {
        this.handlerCallback = handlerCallback;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what)
        {
            case HttpUtil.NETWORK_ERROR_GENERAL:
            {
                //Failed to return parsed JSON
                break;
            }
            case HttpUtil.NETWORK_OK:
            {
                //Successfully return parsed JSON to Fragment
                parsedList=(List<Tweet>)msg.obj;
                handlerCallback.sendbackHandlerData(parsedList);
                break;
            }
            default:
        }

    }
}
