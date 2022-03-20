package com.example.zdroa.myapplication.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetIDSFromDB extends StringRequest {
    private Map<String, String> params;

    public GetIDSFromDB(
            String personType,
            Response.Listener<String> listener) {

        super(Method.POST, RequestUrls.GET_IDS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("person_type", personType);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
