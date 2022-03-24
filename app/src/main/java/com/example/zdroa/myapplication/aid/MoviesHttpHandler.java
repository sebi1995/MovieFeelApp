package com.example.zdroa.myapplication.aid;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zdroa.myapplication.utils.MovieUtils;

public class MoviesHttpHandler {

    private final RequestQueue QUEUE;

    public MoviesHttpHandler(Context context) {
        this.QUEUE = Volley.newRequestQueue(context);
    }

    public void makeMovieAndYoutubeLinkRequest(Integer movieId, Response.Listener<String> successListener) {
        makeStandardGetMovieRequest(
                MovieUtils.getMoviesRequestUrlWithYoutubeVideos(movieId),
                successListener
        );
    }

    public void makeMovieRequest(Integer movieId, Response.Listener<String> successListener) {
        makeStandardGetMovieRequest(
                MovieUtils.getMoviesRequestUrl(movieId),
                successListener
        );

    }

    private void makeStandardGetMovieRequest(String url, Response.Listener<String> successListener) {
        QUEUE.add(new StringRequest(Request.Method.GET, url, successListener, this.errorListener));
    }

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // TODO: 25/08/2019 log the error, save it to a table in the db
            System.out.println(error.toString());
        }
    };

}

