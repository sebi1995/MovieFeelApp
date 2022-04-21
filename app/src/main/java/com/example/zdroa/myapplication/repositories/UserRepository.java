package com.example.zdroa.myapplication.repositories;

import android.content.Context;

import com.android.volley.Response;
import com.example.zdroa.myapplication.utils.RequestUrls;
import com.google.common.collect.ImmutableMap;

public class UserRepository extends WebAppRequestHandler {

    public UserRepository(Context context) {
        super(context);
    }

    public void getByEmailAddressAndPassword(Response.Listener<String> responseListener, Response.ErrorListener errorListener, String emailAddress, String password) {
        doGetRequest(RequestUrls.WEB_APP_USER_FETCH_BY_EMAIL_AND_PASSWORD_URL, responseListener, errorListener, () ->
                ImmutableMap.<String, String>builder()
                        .put(EMAIL_ADDRESS, emailAddress)
                        .put(PASSWORD, password)
                        .build()
        );
    }

    public void create(Response.Listener<String> responseListener, Response.ErrorListener errorListener, String firstname, String surname, String dateOfBirth, String emailAddress, String password) {
        doPostRequest(RequestUrls.WEB_APP_USER_REGISTER_URL, responseListener, errorListener, () ->
                ImmutableMap.<String, String>builder()
                        .put(FIRSTNAME, firstname)
                        .put(SURNAME, surname)
                        .put(DATE_OF_BIRTH, dateOfBirth)
                        .put(EMAIL_ADDRESS, emailAddress)
                        .put(PASSWORD, password)
                        .build()
        );
    }

    public void removeFromWatched(Response.Listener<String> responseListener, Response.ErrorListener errorListener, String userUid, String movieId) {
        doPostRequest(RequestUrls.WEB_APP_USER_REMOVE_MOVIE_FROM_WATCHED_BY_USER_UID_AND_MOVIE_ID, responseListener, errorListener, () ->
                ImmutableMap.<String, String>builder()
                        .put(USER_UID, userUid)
                        .put(MOVIE_ID, movieId)
                        .build());
    }

    public void addToWatchedList(Response.Listener<String> responseListener, Response.ErrorListener errorListener, String userUid, String movieId) {
        doPostRequest(RequestUrls.WEB_APP_USER_ADD_MOVIE_FROM_WATCHED_BY_USER_UID_AND_MOVIE_ID, responseListener, errorListener, () ->
                ImmutableMap.<String, String>builder()
                        .put(USER_UID, userUid)
                        .put(MOVIE_ID, movieId)
                        .build());
    }
}
