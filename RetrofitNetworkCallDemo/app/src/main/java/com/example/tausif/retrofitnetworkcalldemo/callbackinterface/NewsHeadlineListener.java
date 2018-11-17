package com.example.tausif.retrofitnetworkcalldemo.callbackinterface;

import com.example.tausif.retrofitnetworkcalldemo.model.Article;

import java.util.List;


// This callback interface is written to pass data from controller to MainActivity
public interface NewsHeadlineListener {


    void GetNewsHeadLinesResponseSuccessful (List<Article> articleList);

    void GetHeadNewsLinesResponseUnsuccessful (String message);

}
