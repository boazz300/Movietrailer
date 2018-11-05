package com.boazz300.movietrailer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MoviePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
    }
    public void openTrailer(View view){
        String video_path = "https://www.youtube.com/watch?v=6ZfuNTqbHE8";
        Uri uri = Uri.parse(video_path);
        uri = Uri.parse("vnd.youtube:"  + uri.getQueryParameter("v"));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
