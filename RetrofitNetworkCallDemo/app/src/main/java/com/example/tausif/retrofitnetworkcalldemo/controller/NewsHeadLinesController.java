package com.example.tausif.retrofitnetworkcalldemo.controller;

import android.util.Log;

import com.example.tausif.retrofitnetworkcalldemo.apiinterface.Api;
import com.example.tausif.retrofitnetworkcalldemo.callbackinterface.NewsHeadlineListener;
import com.example.tausif.retrofitnetworkcalldemo.model.NewsHeadLineResponses;
import com.example.tausif.retrofitnetworkcalldemo.utils.AppConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// This is the main  controller class where all business logic is written to fetch the data from  rest api call

public class NewsHeadLinesController implements Callback<NewsHeadLineResponses> {


    private NewsHeadlineListener mNewsHeadlineListener = null;


    public NewsHeadLinesController( NewsHeadlineListener newsHeadlineListener){

        mNewsHeadlineListener = newsHeadlineListener;


    }

    public void start(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        Api NewsHeadLinesApi = retrofit.create(Api.class);
        Call<NewsHeadLineResponses> call = NewsHeadLinesApi.getHeadLines(AppConfig.SOURCES,AppConfig.API_KEY);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<NewsHeadLineResponses> call, Response<NewsHeadLineResponses> response) {


        Log.e("_____listener","NewsHeadLines response: "+response.body().getArticles().toString());

        if (response.isSuccessful() && response.body().getStatus().equalsIgnoreCase("ok")) {

            if (mNewsHeadlineListener != null)

                mNewsHeadlineListener.GetNewsHeadLinesResponseSuccessful(response.body().getArticles());


        } else {
            if (mNewsHeadlineListener != null)
                mNewsHeadlineListener.GetHeadNewsLinesResponseUnsuccessful(response.errorBody().toString());
        }

    }

    @Override
    public void onFailure(Call<NewsHeadLineResponses> call, Throwable t) {

        Log.e("_____listener","Failure: "+t.getMessage());
    }
}
