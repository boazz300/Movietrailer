package com.boazz300.movietrailer.movielist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.boazz300.movietrailer.R;
import com.boazz300.movietrailer.db.AppDatabase;
import com.boazz300.movietrailer.model.MovieListResult;
import com.boazz300.movietrailer.model.MovieModel;
import com.boazz300.movietrailer.model.MovieModelConverter;
import com.boazz300.movietrailer.model.MoviesContent;
import com.boazz300.movietrailer.moviepage.MoviePageActivity;
import com.boazz300.movietrailer.rest.RestClientManager;
import com.boazz300.movietrailer.rest.MoviesService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity implements OnMovieClickListener {

    private RecyclerView recyclerView;
    private View progressBar;
    private MoviesViewAdapter adapter;

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

    private void loadMovies() {
        MoviesContent.clear();
        List<MovieModel> cachedMovies = AppDatabase.getInstance(this).movieDao().getAll();
        MoviesContent.MOVIES.addAll(cachedMovies);
        adapter = new MoviesViewAdapter(MoviesContent.MOVIES, MoviesActivity.this);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);
        MoviesService moviesService = RestClientManager.getMovieServiceInstance();
        moviesService.searchImage().enqueue(new Callback<MovieListResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieListResult> call, @NonNull Response<MovieListResult> response) {
                Log.i("response", "response");
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    MoviesContent.clear();
                    MoviesContent.MOVIES.addAll(MovieModelConverter.convertResult(response.body()));
                    adapter.setData(MoviesContent.MOVIES);
                    AppDatabase.getInstance(MoviesActivity.this).movieDao().deleteAll();
                    AppDatabase.getInstance(MoviesActivity.this).movieDao().insertAll(MoviesContent.MOVIES);
                }
            }

            @Override
            public void onFailure(Call<MovieListResult> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MoviesActivity.this, R.string.something_went_wrong_text, Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                AppDatabase.getInstance(this.getApplicationContext()).movieDao().deleteAll();
                adapter.clearData();
                break;
        }

        return true;
    }
}

