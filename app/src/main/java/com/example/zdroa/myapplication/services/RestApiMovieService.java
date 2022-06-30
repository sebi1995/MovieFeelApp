package com.example.zdroa.myapplication.services;

import static com.example.zdroa.myapplication.utils.AppSettings.MOVIES_TO_DISPLAY_LIMIT_PER_PAGE;
import static com.example.zdroa.myapplication.utils.AppSettings.REST_REQUEST_MS_DELAY;

import com.example.zdroa.myapplication.models.Movie;
import com.example.zdroa.myapplication.repositories.RestApiRepository;
import com.example.zdroa.myapplication.utils.AsyncTaskRunner;
import com.example.zdroa.myapplication.utils.Logger;
import com.example.zdroa.myapplication.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;

public class RestApiMovieService {

    private final RestApiRepository movieRepository;
    private final Logger logger;

    public RestApiMovieService(RestApiRepository movieRepository, Logger logger) {
        this.movieRepository = movieRepository;
        this.logger = logger;
    }

    public void fetchStartingFromIdAndLimit(AsyncTaskRunner.Callback<List<Movie>> onResponseListener, int startingMovieId) {
        AsyncTaskRunner.execute(() -> {
            List<Movie> movies = new ArrayList<>();
            int movieId = startingMovieId;
            while (movies.size() < MOVIES_TO_DISPLAY_LIMIT_PER_PAGE) {
                Thread.sleep(REST_REQUEST_MS_DELAY);
                movieRepository.fetchById(
                        jsonResponse -> MovieUtils.convertJsonToMovie(jsonResponse).ifPresent(movies::add),
                        this.logger::logError,
                        ++movieId);
            }
            return movies;
        }, onResponseListener);
    }

    public void fetchMoviesByIds(AsyncTaskRunner.Callback<List<Movie>> onResponse, List<Integer> userWatchedMoviesIds) {
        AsyncTaskRunner.execute(() -> {
            List<Movie> movies = new ArrayList<>();
            for (Integer movieId : userWatchedMoviesIds) {
                Thread.sleep(REST_REQUEST_MS_DELAY);
                movieRepository.fetchById(
                        jsonResponse -> MovieUtils.convertJsonToMovie(jsonResponse).ifPresent(movies::add),
                        this.logger::logError,
                        movieId);
            }
            return movies;
        }, onResponse);
    }
}
