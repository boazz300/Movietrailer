package com.boazz300.movietrailer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MoviePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
    }
    public void openTrailer(View view){
        Button btnTrailer = findViewById(R.id.details_btn_trailer);
        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trailerUrl = MoviePageActivity.this.getString(R.string.infinity_war_trailer);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                startActivity(browserIntent);
            }
        });
    }
}
