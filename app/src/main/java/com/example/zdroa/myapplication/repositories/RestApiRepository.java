package com.example.zdroa.myapplication.repositories;

import android.content.Context;

import com.android.volley.Response;
import com.example.zdroa.myapplication.handlers.WebAppRequestHandler;
import com.example.zdroa.myapplication.utils.RequestUrls;

public class RestApiRepository extends WebAppRequestHandler {

    private static RestApiRepository instance;

    public static RestApiRepository getInstance(Context context) {
        if (instance == null){
            instance = new RestApiRepository(context);
        }
        return instance;
    }

    private RestApiRepository(Context context) {
        super(context);
    }

    public void fetchById(Response.Listener<String> onResponseListener, Response.ErrorListener onErrorListener, Integer movieId) {
        doGetRequest(RequestUrls.generateMovieDatabaseAPIUrl(movieId), onResponseListener, onErrorListener, null);
    }

}
