package com.example.zdroa.myapplication.aid;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

/**
 * Created by zdroa on 19/05/2017.
 */

public class NetworkCheck {
    public static boolean hasNetworkConnection(Activity context) {ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            return false;
        }
        NetworkCapabilities activeNetworkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
        return activeNetworkCapabilities != null && (
                activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
    }
}
