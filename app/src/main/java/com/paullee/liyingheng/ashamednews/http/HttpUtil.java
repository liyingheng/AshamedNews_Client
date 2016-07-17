package com.paullee.liyingheng.ashamednews.http;

import android.content.Context;

import com.paullee.liyingheng.ashamednews.util.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by liyingheng on 16/7/17.
 */

public class HttpUtil {

    public static final int NETWORK_OK=200;
    public static final int NETWORK_LOADING=100;
    public static final int NETWORK_ERROR_GENERAL=101;
    public static final int NETWORK_URL_ERROR=102;
    public static final int NETWORK_UNREACHABLE=103;
    public static final int NETWORK_JSON_ERROR=104;


    public static String httpFetchData(String strUrl, Context context)
            throws MalformedURLException,IOException
    {
        Utility.setNetworkState(context,Utility.NETWORK_LOADING);
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
        return buffer.toString();
    }
}
