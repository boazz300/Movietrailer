package com.boazz300.movietrailer.model;

import java.util.ArrayList;

public class MoviesContent {

    public static final ArrayList<MovieModel> MOVIES = new ArrayList<>();

    public static void clear() {
        MOVIES.clear();
    }

    public static void addMovie(MovieModel movie) {
        MOVIES.add(movie);
    }
}

