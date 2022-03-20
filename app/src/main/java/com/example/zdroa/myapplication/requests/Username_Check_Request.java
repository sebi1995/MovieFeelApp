package com.example.zdroa.myapplication.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class Username_Check_Request extends StringRequest {
    private Map<String, String> params;

    public Username_Check_Request(String username, Boolean usernameChecked, Response.Listener<String> listener) {
        super(Method.POST, RequestUrls.CHECK_USERNAME_EXISTS_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("usernameChecked", String.valueOf(usernameChecked));

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
