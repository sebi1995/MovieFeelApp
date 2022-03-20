package com.example.zdroa.myapplication.requests;

import static com.example.zdroa.myapplication.requests.RequestUrls.REGISTER_QUESTIONNAIRE_REQUEST_URL;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Questionnaire_Request extends StringRequest {
    private Map<String, String> params;

    public Questionnaire_Request(String user_id, String time, String person_type, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_QUESTIONNAIRE_REQUEST_URL, listener, null);
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
