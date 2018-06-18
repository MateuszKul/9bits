package com.example.paulina.a9bits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.example.paulina.a9bits.adapter.PostListAdapter;
import com.example.paulina.a9bits.interfaces.RetroPostInterface;
import com.example.paulina.a9bits.model.Author;
import com.example.paulina.a9bits.model.Post;
import com.example.paulina.a9bits.retrofit.RetrofitClient;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private String tag = "MainActivity";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data();
    }

    public void data() {
        recyclerView = findViewById(R.id.main_activity_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        Retrofit client = RetrofitClient.getInstance();
        RetroPostInterface retro = client.create(RetroPostInterface.class);
        Call<List<Post>> request = retro.getPostList();
        request.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    for (Post iter : response.body()) {
                        if (!iter.getAuthor().exists())
                            iter.getAuthor().save();
                        iter.save();
                    }
                    PostListAdapter adapter = new PostListAdapter(response.body(), getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(tag, "Download posts error: " + t.getMessage());
            }
        });
    }

}
