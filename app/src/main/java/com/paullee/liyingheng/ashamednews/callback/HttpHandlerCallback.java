package com.paullee.liyingheng.ashamednews.callback;

import android.os.Message;

import com.paullee.liyingheng.ashamednews.Tweet;

import java.util.List;

/**
 * Created by liyingheng on 16/7/17.
 */

public interface HttpHandlerCallback {
    public void sendbackHandlerData(List<Tweet> parsedData, int HandlerNetworkState);
}
