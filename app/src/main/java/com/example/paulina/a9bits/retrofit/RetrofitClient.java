package com.example.paulina.a9bits.retrofit;

import android.view.LayoutInflater;
import android.view.View;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String baseUrl = "https://apps.nd0.pl/";
    private static Retrofit.Builder retrofitbuilder;
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofitbuilder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = retrofitbuilder.build();
        }
        return retrofit;
    }

}

