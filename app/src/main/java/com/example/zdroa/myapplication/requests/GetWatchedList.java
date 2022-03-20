package com.example.zdroa.myapplication.requests;

import static com.example.zdroa.myapplication.requests.RequestUrls.GET_WATCHED_LIST_REQUEST_URL;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetWatchedList extends StringRequest {
    private Map<String, String> params;

    public GetWatchedList(
            String id,
            Response.Listener<String> listener) {
        super(Method.POST, GET_WATCHED_LIST_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
