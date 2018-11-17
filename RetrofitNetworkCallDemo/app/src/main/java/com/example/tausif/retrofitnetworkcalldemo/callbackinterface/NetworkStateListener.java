package com.example.tausif.retrofitnetworkcalldemo.callbackinterface;


//This callback interface is written to send the network state from broadcast receiver to main activity
public interface NetworkStateListener {

    void getNetworkStatus (String message);

}
