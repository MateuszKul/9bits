package com.example.paulina.a9bits.interfaces;

import com.example.paulina.a9bits.model.PostDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetroPostDetailsInterface {
    @GET("/mock/post/{id}")
    Call<PostDetails> getPostDetailsList(@Path("id") String id);
}
