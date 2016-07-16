package com.paullee.liyingheng.ashamednews;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by liyingheng on 16/7/16.
 */

public class Utility {

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
}
