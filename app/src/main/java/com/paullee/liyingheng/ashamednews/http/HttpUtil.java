package com.paullee.liyingheng.ashamednews.http;

import android.content.Context;
import android.util.Log;

import com.paullee.liyingheng.ashamednews.util.Utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;

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
    public static final int NETWORK_POST_RETURN = 300;

    private static final String LOG_TAG = HttpUtil.class.getSimpleName();


    public static String httpDoGet(String strUrl, Context context)
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

    public static String httpDoPost(String strUrl, Context context, String value) {
        String result = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true); //Support for read
            conn.setDoInput(true);  //Support for write

            OutputStream os = conn.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            Log.d(LOG_TAG, "POST SEND:" + value);
            dos.write(value.getBytes());
            dos.flush();
            dos.close();
            os.close();

            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = null;
            StringBuffer sbf = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sbf.append(str);
            }
            Log.d(LOG_TAG, "POST RECEIVED:" + sbf.toString());
            result = sbf.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
