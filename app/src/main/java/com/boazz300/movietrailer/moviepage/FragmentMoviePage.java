package com.boazz300.movietrailer.moviepage;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.boazz300.movietrailer.R;
import com.boazz300.movietrailer.model.MovieModel;

public class FragmentMoviePage extends Fragment implements View.OnClickListener {
    private static final String TAG = "MovieDetailsFragment";
    private static final String ARG_MOVIE = "MovieModel-data";
    private ImageView ivImage;
    private ImageView ivBackImage;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private MovieModel movieModel;

    public FragmentMoviePage() { }

    public static FragmentMoviePage newInstance(MovieModel movieModel) {
        FragmentMoviePage fragment = new FragmentMoviePage();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movieModel);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieModel = getArguments().getParcelable(ARG_MOVIE);
        }
        Log.d(TAG, "movieModel: "+movieModel);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_movie_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivImage = view.findViewById(R.id.details_iv_image);
        ivBackImage = view.findViewById(R.id.details_iv_back);
        tvTitle = view.findViewById(R.id.details_tv_title);
        tvReleaseDate = view.findViewById(R.id.details_tv_released_date);
        tvOverview = view.findViewById(R.id.details_tv_overview_text);

        Button btnTrailer = view.findViewById(R.id.details_btn_trailer);
        btnTrailer.setOnClickListener(this);

        setMovie();
    }

    private void setMovie() {
        if (movieModel == null) return;

        ivImage.setImageResource(movieModel.getImageRes());
        ivBackImage.setImageResource(movieModel.getBackImageRes());
        tvTitle.setText(movieModel.getName());
        tvReleaseDate.setText(movieModel.getReleaseDate());
        tvOverview.setText(movieModel.getOverview());
    }

    @Override
    public void onClick(View view) {
        if (movieModel == null) return;
        String trailerUrl = movieModel.getTrailerUrl();
        if (TextUtils.isEmpty(trailerUrl)) return;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(browserIntent);
    }
}
