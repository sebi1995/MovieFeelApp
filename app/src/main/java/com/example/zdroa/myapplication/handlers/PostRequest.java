package com.example.zdroa.myapplication.handlers;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class PostRequest extends StringRequest {
    private Map<String, String> params;

    public PostRequest(String url, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
    }

    public PostRequest setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }
}
