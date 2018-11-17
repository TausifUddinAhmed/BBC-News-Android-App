package com.example.tausif.retrofitnetworkcalldemo.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.tausif.retrofitnetworkcalldemo.callbackinterface.NetworkStateListener;


//This class defined itself :P

public class NetworkChangeReceiver extends BroadcastReceiver {


    NetworkStateListener networkStateListener;


    public NetworkChangeReceiver  (){


    }


    public NetworkChangeReceiver  (NetworkStateListener networkStateListener){

        this.networkStateListener = networkStateListener;

    }




    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            if (isOnline(context)) {

                networkStateListener.getNetworkStatus("online");
            } else {
                networkStateListener.getNetworkStatus("offline");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}