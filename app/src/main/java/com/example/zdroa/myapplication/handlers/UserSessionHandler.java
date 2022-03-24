package com.example.zdroa.myapplication.handlers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.zdroa.myapplication.utils.AppSettings;

public class UserSessionHandler {

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public static final String USER_ID = "user_id";
    public static final String USER_TITLE = "user_title";
    public static final String USER_FIRSTNAME = "user_firstname";
    public static final String USER_SURNAME = "user_surname";
    public static final String USER_DATE_OF_BIRTH = "user_date_of_birth";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_KEY = "user_key";
    public static final String USER_PERSON_TYPE = "user_person_type";
    public static final String USER_TYPE = "user_type";
    public static final String USER_IDS_LIST = "ids_list";
    public static final String USER_WATCHED_MOVIES = "watched_list";

    public UserSessionHandler(Context context) {
        prefs = context.getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public Integer getId() {
        return getIntegerOrNull(USER_ID);
    }

    public void setId(Integer value) {
        putIntegerOrNull(USER_ID, value);
    }

    public String getTitle() {
        return getStringOrNull(USER_TITLE);
    }

    public void setTitle(String value) {
        putStringOrNull(USER_TITLE, value);
    }

    public String getFirstName() {
        return getStringOrNull(USER_FIRSTNAME);
    }

    public void setFirstName(String value) {
        putStringOrNull(USER_FIRSTNAME, value);
    }

    public String getSurname() {
        return getStringOrNull(USER_SURNAME);
    }

    public void setSurname(String value) {
        putStringOrNull(USER_SURNAME, value);
    }

    public String getDateOfBirth() {
        return getStringOrNull(USER_DATE_OF_BIRTH);
    }

    public void setDateOfBirth(String value) {
        putStringOrNull(USER_DATE_OF_BIRTH, value);
    }

    public String getEmail() {
        return getStringOrNull(USER_EMAIL);
    }

    public void setEmail(String value) {
        putStringOrNull(USER_EMAIL, value);
    }

    public String getPassword() {
        return getStringOrNull(USER_PASSWORD);
    }

    public void setPassword(String value) {
        putStringOrNull(USER_PASSWORD, value);
    }

    public String getKey() {
        return getStringOrNull(USER_KEY);
    }

    public void setKey(String value) {
        putStringOrNull(USER_KEY, value);
    }

    public String getPersonType() {
        return getStringOrNull(USER_PERSON_TYPE);
    }

    public void setPersonType(String value) {
        putStringOrNull(USER_PERSON_TYPE, value);
    }

    public String getUserType() {
        return getStringOrNull(USER_TYPE);
    }

    public void setUserType(String value) {
        putStringOrNull(USER_TYPE, value);
    }

    public String getIdsList() {
        return getStringOrNull(USER_IDS_LIST);
    }

    public void setIdsList(String value) {
        putStringOrNull(USER_IDS_LIST, value);
    }

    public String getWatchedMovies() {
        return getStringOrNull(USER_WATCHED_MOVIES);
    }

    public void setWatchedMovies(String value) {
        putStringOrNull(USER_WATCHED_MOVIES, value);
    }

    private Integer getIntegerOrNull(String sharedPreferenceType) {
        int anInt = prefs.getInt(sharedPreferenceType, -1);
        return anInt < 0 ? null : anInt;
    }

    private void putIntegerOrNull(String sharedPreferenceType, Integer value) {
        editor.putInt(sharedPreferenceType, value);
        editor.commit();
    }

    private String getStringOrNull(String sharedPreferenceType) {
        return prefs.getString(sharedPreferenceType, null);
    }

    private void putStringOrNull(String sharedPreferenceType, String value) {
        editor.putString(sharedPreferenceType, value);
        editor.commit();
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }
}
