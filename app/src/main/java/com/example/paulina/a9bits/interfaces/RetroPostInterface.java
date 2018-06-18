package com.example.paulina.a9bits.interfaces;

import com.example.paulina.a9bits.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetroPostInterface {
    @GET ("/mock/posts")
    Call<List<Post>> getPostList();
}
