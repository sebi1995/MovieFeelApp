package com.example.zdroa.myapplication.repositories;

import static com.example.zdroa.myapplication.utils.AppSettings.API_HTTP_REQUEST_MAX_NUMBER_OF_RETRIES;
import static com.example.zdroa.myapplication.utils.AppSettings.API_HTTP_REQUEST_TIMEOUT_AFTER_SECONDS;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class WebAppRequestHandler {

    public static final String USER_UID = "uid";
    public static final String FIRSTNAME = "firstname";
    public static final String SURNAME = "surname";
    public static final String EMAIL_ADDRESS = "emailAddress";
    public static final String PASSWORD = "password";
    public static final String DATE_OF_BIRTH = "dateOfBirth";
    public static final String MOVIE_ID = "movieId";
    public static final String MOVIE_IDS = "movieIds";
    public static final String LIMIT = "limit";

    private final Context context;

    protected WebAppRequestHandler(Context context) {
        this.context = context;
    }

    protected void doGetRequest(String url, Response.Listener<String> responseListener, Response.ErrorListener errorListener, Supplier<Map<String, String>> params) {
        addToQueue(createStringRequest(Request.Method.GET, url, responseListener, errorListener, params));
    }

    protected void doPostRequest(String url, Response.Listener<String> responseListener, Response.ErrorListener errorListener, Supplier<Map<String, String>> params) {
        addToQueue(createStringRequest(Request.Method.POST, url, responseListener, errorListener, params));
    }

    protected void doPutRequest(String url, Response.Listener<String> responseListener, Response.ErrorListener errorListener, Supplier<Map<String, String>> params) {
        addToQueue(createStringRequest(Request.Method.PUT, url, responseListener, errorListener, params));
    }

    protected void doDeleteRequest(String url, Response.Listener<String> responseListener, Response.ErrorListener errorListener, Supplier<Map<String, String>> params) {
        addToQueue(createStringRequest(Request.Method.DELETE, url, responseListener, errorListener, params));
    }

    protected StringRequest createStringRequest(int requestType, String url, Response.Listener<String> onResponseStringListener, Response.ErrorListener errorListener, Supplier<Map<String, String>> paramsSupplier) {
        return new StringRequest(requestType, url,onResponseStringListener, errorListener) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                return paramsSupplier.get();
            }
        };
    }

    private void addToQueue(StringRequest request) {
        Volley.newRequestQueue(context).add(request).setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(API_HTTP_REQUEST_TIMEOUT_AFTER_SECONDS), //After the set time elapses the request will timeout
                API_HTTP_REQUEST_MAX_NUMBER_OF_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
