package com.example.zdroa.myapplication.repositories;

import android.content.Context;

import com.android.volley.Response;
import com.example.zdroa.myapplication.utils.RequestUrls;

public class RestApiRepository extends WebAppRequestHandler {

    public RestApiRepository(Context context) {
        super(context);
    }

    public void fetchById(Response.Listener<String> onResponseListener, Response.ErrorListener onErrorListener, Integer movieId) {
        doGetRequest(RequestUrls.generateMovieDatabaseAPIUrl(movieId), onResponseListener, onErrorListener, null);
    }

}
