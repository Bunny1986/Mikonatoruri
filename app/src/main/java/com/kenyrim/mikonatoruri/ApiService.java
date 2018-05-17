package com.kenyrim.mikonatoruri;

import com.kenyrim.mikonatoruri.main.Model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/list.php?category=football")
    Call<Model> getFootball();

    @GET("/list.php?category=hockey")
    Call<Model> getHockey();

    @GET("/list.php?category=tennis")
    Call<Model> getTennis();

    @GET("/list.php?category=basketball")
    Call<Model> getBasketball();

    @GET("/list.php?category=volleyball")
    Call<Model> getVolleyball();

    @GET("/list.php?category=cybersport")
    Call<Model> getCybersport();

}
