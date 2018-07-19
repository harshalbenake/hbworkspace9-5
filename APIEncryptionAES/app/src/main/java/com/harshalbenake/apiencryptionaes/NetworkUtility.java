package com.harshalbenake.apiencryptionaes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by GAURAV on 11-08-2016.
 */

public class NetworkUtility {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        try {

            if (context == null) {
                throw new Exception("Network utility - context is null");
            }
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
        }catch (Exception ex) {
        }
        return TYPE_NOT_CONNECTED;
    }

    public static boolean isOnline(Context context) {
        int status=getConnectivityStatus(context);
        if(status==TYPE_NOT_CONNECTED)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}