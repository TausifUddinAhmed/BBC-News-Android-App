package com.example.tausif.retrofitnetworkcalldemo.apiinterface;

import com.example.tausif.retrofitnetworkcalldemo.model.NewsHeadLineResponses;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {


    // Ex https://newsapi.org/v2/top-headlines?sources=bbc-news&apikey=69bd6f42f32f43e3ba76ec9cd250743b
    @GET("top-headlines")
    Call<NewsHeadLineResponses> getHeadLines(@Query("sources") String sources, @Query("apikey") String apiKey);


}
