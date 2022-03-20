package com.example.zdroa.myapplication.handlers;

import static com.example.zdroa.myapplication.handlers.RequestUrls.ADD_TO_WATCHED_LIST_REQUEST_URL;
import static com.example.zdroa.myapplication.handlers.RequestUrls.CHECK_USERNAME_EXISTS_URL;
import static com.example.zdroa.myapplication.handlers.RequestUrls.GET_MOVIE_IDS_REQUEST_URL;
import static com.example.zdroa.myapplication.handlers.RequestUrls.GET_WATCHED_LIST_REQUEST_URL;
import static com.example.zdroa.myapplication.handlers.RequestUrls.INSERT_IDS_INTO_DB_REQUEST_URL;
import static com.example.zdroa.myapplication.handlers.RequestUrls.LOGIN_REQUEST_URL;
import static com.example.zdroa.myapplication.handlers.RequestUrls.REGISTER_QUESTIONNAIRE_REQUEST_URL;
import static com.example.zdroa.myapplication.handlers.RequestUrls.REGISTER_REQUEST_URL;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class HttpRequestHandler {

    public static StringRequest getWatchedListByUserId(Response.Listener<String> listener, String userId) {
        return new PostRequest(GET_WATCHED_LIST_REQUEST_URL, listener)
                .setParams(
                        Map.of("user_id", userId)
                );
    }

    public static StringRequest checkUsernameExists(Response.Listener<String> listener, String username, boolean usernameChecked) {
        return new PostRequest(CHECK_USERNAME_EXISTS_URL, listener)
                .setParams(
                        Map.of("username", username,
                                "usernameChecked", String.valueOf(usernameChecked))
                );
    }

    public static StringRequest registerUser(Response.Listener<String> listener, String title, String firstname, String surname, String dob, String email, String username, String password, int key, String usernameOrEmail) {
        return new PostRequest(REGISTER_REQUEST_URL, listener)
                .setParams(
                        Map.of("title", title,
                                "firstname", firstname,
                                "surname", surname,
                                "dob", dob,
                                "email", email,
                                "username", username,
                                "password", password,
                                "key", key + "",
                                "usernameORemail", usernameOrEmail)
                );
    }

    public static StringRequest login(Response.Listener<String> listener, String username, String password) {
        return new PostRequest(LOGIN_REQUEST_URL, listener)
                .setParams(
                        Map.of("username", username,
                                "password", password)
                );
    }

    public static StringRequest getMovieIdsByPersonType(Response.Listener<String> listener, String personType) {
        return new PostRequest(GET_MOVIE_IDS_REQUEST_URL, listener)
                .setParams(
                        Map.of("person_type", personType)
                );
    }

    public static StringRequest registerQuestionnaire(Response.Listener<String> listener, String userId, String time, String personType) {
        return new PostRequest(REGISTER_QUESTIONNAIRE_REQUEST_URL, listener)
                .setParams(
                        Map.of("user_id", userId,
                                "time", time,
                                "person_type", personType)
                );
    }

    public static StringRequest addToWatchedList(Response.Listener<String> listener, String userId, String newWatchedList) {
        return new PostRequest(ADD_TO_WATCHED_LIST_REQUEST_URL, listener)
                .setParams(
                        Map.of("user_id", userId,
                                "watched_list", newWatchedList)
                );
    }

    public static StringRequest addMovieIdsToDbByPersonType(Response.Listener<String> listener, String personType, String userId) {
        return new PostRequest(INSERT_IDS_INTO_DB_REQUEST_URL, listener)
                .setParams(
                        Map.of("person_type", personType,
                                "id", userId)
                );
    }

}