package com.example.zdroa.myapplication.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Register_Request extends StringRequest {
    private Map<String, String> params;

    public Register_Request(
            String title,
            String firstname,
            String surname,
            String dob,
            String email,
            String username,
            String password,
            int key,
            String usernameOrEmail,
            Response.Listener<String> listener) {

        super(Method.POST, RequestUrls.REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("title", title);
        params.put("firstname", firstname);
        params.put("surname", surname);
        params.put("dob", dob.toString());
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);
        params.put("key", key + "");
        params.put("usernameORemail", usernameOrEmail);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
