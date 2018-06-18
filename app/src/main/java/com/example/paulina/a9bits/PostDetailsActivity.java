package com.example.paulina.a9bits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.paulina.a9bits.interfaces.RetroPostDetailsInterface;
import com.example.paulina.a9bits.model.Post;
import com.example.paulina.a9bits.model.PostDetails;
import com.example.paulina.a9bits.retrofit.RetrofitClient;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostDetailsActivity extends AppCompatActivity {

    private String tag = "PostDetailsActivity";
    private View view;
    private String stringId;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        stringId = bundle.getString("post_id");
        data();
        backButton = (Button) findViewById(R.id.details_post_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void data() {
        view = findViewById(R.id.details_post_activity);
        Retrofit client = RetrofitClient.getInstance();
        RetroPostDetailsInterface retro = client.create(RetroPostDetailsInterface.class);
        Call<PostDetails> request = retro.getPostDetailsList(stringId);
        request.enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {
                if (response.isSuccessful()) {
                    PostDetails postDetails = response.body();

                    TextView textView = findViewById(R.id.details_post_author_label);
                    textView.setText(postDetails.getAuthor().toString());

                    textView = findViewById(R.id.details_post_title_label);
                    textView.setText(postDetails.getTitle());

                    textView = findViewById(R.id.details_post_desc_label);
                    textView.setText(postDetails.getDescription());

                    textView = findViewById(R.id.details_post_data_label);
                    long time = Long.parseLong(postDetails.getTimestamp());
                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    cal.setTimeInMillis(time * 1000L);
                    textView.setText(DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString());

                    Glide.with(view).load("https://cdn.pixabay.com/photo/2018/05/17/17/50/world-cup-2018-3409222_960_720.jpg").into((ImageView) view.findViewById(R.id.details_post_image_view));
                }
            }

            @Override
            public void onFailure(Call<PostDetails> call, Throwable t) {
                Log.d(tag, "Download post error: " + t.getMessage());
            }
        });
    }
}
