package com.kenyrim.mikonatoruri.tools;

import com.kenyrim.mikonatoruri.ApiService;
import com.kenyrim.mikonatoruri.content.ApiContent;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final String URL = "http://mikonatoruri.win";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
    public static ApiContent getApiContent() {
        return getRetrofitInstance().create(ApiContent.class);
    }
}
