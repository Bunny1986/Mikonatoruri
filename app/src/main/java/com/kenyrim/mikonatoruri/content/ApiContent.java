package com.kenyrim.mikonatoruri.content;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiContent {

    @GET("/post.php")
    Call<Content> getArt(@Query("article") String article);



}
