package com.example.zdroa.myapplication.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Questionnaire_Request extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://www.movie-feel.com/php_scripts/Questionnaire_Request.php";
    private Map<String, String> params;

    public Questionnaire_Request(String user_id, String time, String person_type, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);

        params = new HashMap<>();

        params.put("user_id", user_id);
        params.put("time", time);
        params.put("person_type", person_type);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
