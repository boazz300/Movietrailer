package com.boazz300.movietrailer.model;

import com.boazz300.movietrailer.rest.MoviesService;
import java.util.ArrayList;

public class MovieModelConverter {

    public static ArrayList<MovieModel> convertResult(MovieListResult movieListResult) {

        ArrayList<MovieModel> result = new ArrayList<>();
        for (MovieResult movieResult : movieListResult.getResults()) {
            result.add(new MovieModel(movieResult.getId(), movieResult.getTitle(), MoviesService.POSTER_BASE_URL + movieResult.getPosterPath(),
                    MoviesService.BACKDROP_BASE_URL + movieResult.getBackdropPath(), movieResult.getOverview(),
                    movieResult.getReleaseDate()));
        }

        return result;
    }
}
