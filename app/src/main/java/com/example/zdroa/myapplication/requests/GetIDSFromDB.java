package com.example.zdroa.myapplication.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetIDSFromDB extends StringRequest {
    private static final String GET_IDS_REQUEST_URL = "http://www.movie-feel.com/php_scripts/ids_request.php";
    private Map<String, String> params;

    public GetIDSFromDB(
            String personType,
            Response.Listener<String> listener) {

        super(Method.POST, GET_IDS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("person_type", personType);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
