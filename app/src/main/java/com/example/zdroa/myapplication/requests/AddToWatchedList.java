package com.example.zdroa.myapplication.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zdroa on 19/05/2017.
 */

public class AddToWatchedList extends StringRequest {
    private static final String WATCHED_LIST_REQUEST_URL = "http://www.movie-feel.com/php_scripts/addToWatchedList_request.php";
    private Map<String, String> params;

    public AddToWatchedList(
            String id,
            String newWatchedList,
            Response.Listener<String> listener) {

        super(Request.Method.POST, WATCHED_LIST_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", id);
        params.put("watched_list", newWatchedList);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}