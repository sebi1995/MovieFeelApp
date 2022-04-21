package com.example.zdroa.myapplication.services;

import com.android.volley.Response;
import com.example.zdroa.myapplication.repositories.WebAppMovieRepository;
import com.example.zdroa.myapplication.utils.Logger;
import com.google.gson.Gson;

import java.util.List;

public class WebAppMovieService {

    private WebAppMovieRepository movieRepository;
    private Logger logger;

    public WebAppMovieService(WebAppMovieRepository movieRepository, Logger logger) {
        this.movieRepository = movieRepository;
        this.logger = logger;
    }

    public void fetchStartingFromIdAndLimit(Response.Listener<String> onResponseListener, Response.ErrorListener onErrorListener, int startingMovieId, int limit) {
        movieRepository.fetchStartingFromIdAndLimit(onResponseListener, onErrorListener, String.valueOf(startingMovieId), String.valueOf(limit));
    }

    public void fetchByIds(Response.Listener<String> onResponseListener, Response.ErrorListener onErrorListener, List<Integer> userWatchedMoviesIds) {
        movieRepository.fetchByIds(onResponseListener, onErrorListener, new Gson().toJson(userWatchedMoviesIds));
    }

    public void fetchWatchedByUserId(Response.Listener<String> onResponseListener, Response.ErrorListener onErrorListener, Integer userUID) {
        movieRepository.fetchWatchedByUserUid(onResponseListener, onErrorListener, String.valueOf(userUID));
    }
}
