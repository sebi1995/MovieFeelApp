package com.example.zdroa.myapplication.repositories;

import android.content.Context;

import com.android.volley.Response;
import com.example.zdroa.myapplication.handlers.WebAppRequestHandler;
import com.example.zdroa.myapplication.utils.RequestUrls;
import com.google.common.collect.ImmutableMap;

public class WebAppMovieRepository extends WebAppRequestHandler {

    private static WebAppMovieRepository instance = null;

    public static WebAppMovieRepository getInstance(Context context) {
        if (instance == null){
            instance = new WebAppMovieRepository(context);
        }
        return instance;
    }

    private WebAppMovieRepository(Context context) {
        super(context);
    }

    public void fetchById(Response.Listener<String> onResponseListener, Response.ErrorListener onErrorListener, Integer movieId) {
        doGetRequest(RequestUrls.WEB_APP_FETCH_MOVIE_BY_ID_URL, onResponseListener, onErrorListener, () ->
                ImmutableMap.<String, String>builder()
                        .put(MOVIE_ID, String.valueOf(movieId))
                        .build()
        );
    }

    public void fetchByIds(Response.Listener<String> onResponseListener, Response.ErrorListener onErrorListener, String movieIdsJson) {
        doGetRequest(RequestUrls.WEB_APP_FETCH_MOVIES_BY_IDS_URL, onResponseListener, onErrorListener, () ->
                ImmutableMap.<String, String>builder()
                        .put(MOVIE_IDS, movieIdsJson)
                        .build()
        );
    }

    public void fetchStartingFromIdAndLimit(Response.Listener<String> onResponseListener, Response.ErrorListener onErrorListener, String startingMovieId, String limit) {
        doGetRequest(RequestUrls.WEB_APP_FETCH_MOVIES_STARTING_WITH_ID_AND_LIMIT_URL, onResponseListener, onErrorListener, () ->
                ImmutableMap.<String, String>builder()
                        .put(MOVIE_ID, startingMovieId)
                        .put(LIMIT, limit)
                        .build()
        );
    }

    public void fetchWatchedByUserUid(Response.Listener<String> onResponseListener, Response.ErrorListener onErrorListener, String userUID) {
        doGetRequest(RequestUrls.WEB_APP_FETCH_WATCHED_MOVIES_BY_USER_UID, onResponseListener, onErrorListener, () ->
                ImmutableMap.<String, String>builder()
                        .put(USER_UID, userUID)
                        .build()
        );
    }
}
