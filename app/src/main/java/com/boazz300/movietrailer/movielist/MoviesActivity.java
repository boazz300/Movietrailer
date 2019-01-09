package com.boazz300.movietrailer.movielist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.boazz300.movietrailer.R;
import com.boazz300.movietrailer.model.MovieListResult;
import com.boazz300.movietrailer.model.MovieModelConverter;
import com.boazz300.movietrailer.model.MoviesContent;
import com.boazz300.movietrailer.moviepage.MoviePageActivity;
import com.boazz300.movietrailer.rest.RestClientManager;
import com.boazz300.movietrailer.rest.MoviesService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesActivity extends AppCompatActivity implements OnMovieClickListener {

    private RecyclerView recyclerView;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        recyclerView = findViewById(R.id.movies_rv_list);
        progressBar = findViewById(R.id.main_progress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        createNetworkService();

        loadMovies();
    }

    private void createNetworkService() {

    }

    @Override
    public void onMovieClicked(int itemPosition) {
        if (itemPosition < 0 || itemPosition >= MoviesContent.MOVIES.size()) return;

        Intent intent = new Intent(this, MoviePageActivity.class);
        intent.putExtra(MoviePageActivity.EXTRA_ITEM_POSITION, itemPosition);
        startActivity(intent);
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_open_async_task:
                startActivity(new Intent(MoviesActivity.this, AsyncTaskActivity.class));
                return true;

            case R.id.action_open_thread_handler:
                startActivity(new Intent(MoviesActivity.this, ThreadsActivity.class));
                return true;

            case R.id.action_open_background_service:
                startActivity(new Intent(MoviesActivity.this, BGServiceActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */

    private void loadMovies() {
        MoviesContent.clear();
        progressBar.setVisibility(View.VISIBLE);
        MoviesService moviesService = RestClientManager.getMovieServiceInstance();
        moviesService.searchImage().enqueue(new Callback<MovieListResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieListResult> call, @NonNull Response<MovieListResult> response) {
                Log.i("response", "response");
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    MoviesContent.MOVIES.addAll(MovieModelConverter.convertResult(response.body()));
                    recyclerView.setAdapter(new MoviesViewAdapter(MoviesContent.MOVIES, MoviesActivity.this));
                }
            }

            @Override
            public void onFailure(Call<MovieListResult> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.i("failure", "failure");
                Toast.makeText(MoviesActivity.this, R.string.something_went_wrong_text, Toast.LENGTH_SHORT).show();

            }
        });
    }

}

