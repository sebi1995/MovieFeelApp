package com.example.zdroa.myapplication.services;

import com.example.zdroa.myapplication.models.Movie;
import com.example.zdroa.myapplication.repositories.RestApiRepository;
import com.example.zdroa.myapplication.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class RestApiMovieService implements MovieService {

    private RestApiRepository movieRepository;
    private Logger logger;

    public RestApiMovieService(RestApiRepository movieRepository, Logger logger) {
        this.movieRepository = movieRepository;
        this.logger = logger;
    }

    @Override
    public void fetchMoviesStartingWithIdAndLimit(HttpRequestRunner.Callback<List<Movie>> onResponseListener, int startingMovieId, int limit) {
        HttpRequestRunner.executeAsync(() -> {
            List<Movie> movies = new ArrayList<>();
            int movieId = startingMovieId;
            while (movies.size() != limit) {
                Thread.sleep(100);
                movieRepository.fetchById(jsonResponse -> {
                    convertToMovie(jsonResponse).ifPresent(movies::add);
                }, error -> {
                    logger.logError(error);
                }, ++movieId);
            }
            return movies;
        }, onResponseListener);
    }

    @Override
    public void fetchMoviesByIds(HttpRequestRunner.Callback<List<Movie>> onResponse, List<Integer> userWatchedMoviesIds) {
        HttpRequestRunner.executeAsync(() -> {
            List<Movie> movies = new ArrayList<>();
            for (Integer movieId : userWatchedMoviesIds) {
                Thread.sleep(100);
                movieRepository.fetchById(
                        jsonResponse -> {
                            convertToMovie(jsonResponse).ifPresent(movies::add);
                        }, error -> {
                            logger.logError(error);
                        },
                        movieId);
            }
            return movies;
        }, onResponse);
    }

}
