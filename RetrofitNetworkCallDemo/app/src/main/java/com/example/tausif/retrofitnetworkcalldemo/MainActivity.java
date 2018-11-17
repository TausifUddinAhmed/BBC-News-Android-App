package com.example.tausif.retrofitnetworkcalldemo;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.example.tausif.retrofitnetworkcalldemo.adapter.NewsListAdapter;
import com.example.tausif.retrofitnetworkcalldemo.callbackinterface.NetworkStateListener;
import com.example.tausif.retrofitnetworkcalldemo.callbackinterface.NewsHeadlineListener;
import com.example.tausif.retrofitnetworkcalldemo.controller.NewsHeadLinesController;
import com.example.tausif.retrofitnetworkcalldemo.model.Article;
import com.example.tausif.retrofitnetworkcalldemo.recievers.NetworkChangeReceiver;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsHeadlineListener, NetworkStateListener {

    private BroadcastReceiver mNetworkReceiver;


    ProgressDialog progressDialog;
    NewsHeadLinesController newsHeadLinesController;
    private RecyclerView mRecyclerView;
    private NewsListAdapter mNewsListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //RecyclerView Initialization
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_news_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        //used BroadcastReceiver to check network state
        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver = new NetworkChangeReceiver(this);
        registerNetworkBroadcastForNougat();


    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    // This method would be called when network is available.
    //This method's responsibility to call  thr controller class to  fetch data from Rest api using get method.
    private void GetNewHeadlines() {


        //Progress dialog initialization so that users doesn't get bored :P
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        //controller class initialization and called the method of class
        newsHeadLinesController = new NewsHeadLinesController(this);
        newsHeadLinesController.start();


    }



    @Override
    public void getNetworkStatus(String message) {

        if (message.equals("online")) {

            GetNewHeadlines();

        } else {

            Toast.makeText(this, "Please Activate Data Connection", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void GetNewsHeadLinesResponseSuccessful(List<Article> articleList) {

        progressDialog.dismiss();
        mNewsListAdapter = new NewsListAdapter(this, articleList, R.layout.item_show_news);
        mRecyclerView.setAdapter(mNewsListAdapter);

    }

    @Override
    public void GetHeadNewsLinesResponseUnsuccessful(String message) {


        Toast.makeText(this, "Failed to Fetch News", Toast.LENGTH_SHORT).show();

    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }

    }



    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }


}
