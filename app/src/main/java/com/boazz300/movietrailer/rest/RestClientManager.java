package com.boazz300.movietrailer.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientManager {

    private static MoviesService moviesService;

    public static MoviesService getMovieServiceInstance() {
        if (moviesService == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl(MoviesService.BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            moviesService = retrofit.create(MoviesService.class);
        }

        return moviesService;
    }
}
