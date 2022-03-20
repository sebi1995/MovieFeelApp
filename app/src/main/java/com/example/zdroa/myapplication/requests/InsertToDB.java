package com.example.zdroa.myapplication.requests;

import static com.example.zdroa.myapplication.requests.RequestUrls.INSERT_IDS_INTO_DB_REQUEST_URL;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsertToDB extends StringRequest {
    private Map<String, String> params;

    public InsertToDB(
            String personType,
            String ID,
            Response.Listener<String> listener) {

        super(Method.POST, INSERT_IDS_INTO_DB_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("person_type", personType);
        params.put("id", ID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
