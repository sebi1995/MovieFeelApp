package com.example.zdroa.myapplication.aid;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zdroa on 19/05/2017.
 */

public class NetworkCheck {
    public boolean NetworkCheck(Activity activity)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null &&activeNetworkInfo.isConnected();
    }
}
