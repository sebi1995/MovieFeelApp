package com.example.zdroa.myapplication.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsertToDB extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://www.movie-feel.com/php_scripts/insertIDSIntoDB.php";
    private Map<String, String> params;

    public InsertToDB(
            String personType,
            String ID,
            Response.Listener<String> listener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("person_type", personType);
        params.put("id", ID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
