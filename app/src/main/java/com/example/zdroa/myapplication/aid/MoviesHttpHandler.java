package com.example.zdroa.myapplication.aid;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MoviesHttpHandler {

    private final RequestQueue QUEUE;

    public MoviesHttpHandler(Context context) {
        this.QUEUE = Volley.newRequestQueue(context);
    }

    /**
     * make request to get movie with youtube links
     *
     * @param movieId         movie id
     * @param successListener what should happen on success
     */
    public void makeMovieAndYoutubeLinkRequest(Integer movieId, Response.Listener<String> successListener) {
        makeStandardGetMovieRequest(
                MovieUtils.getMoviesRequestUrlWithVideos(movieId),
                successListener
        );
    }

    /**
     * makek request to get basic movie data
     *
     * @param movieId
     * @param successListener
     */
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

