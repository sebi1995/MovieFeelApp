package com.example.zdroa.myapplication.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionHandler {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private String user_id = "user_id";
    private String user_title = "user_title";
    private String user_surname = "user_surname";
    private String userFirstname = "user_firstname";
    private String userDob = "userDob";
    private String userEmail = "userEmail";
    private String userEmailOrPassword = "userEmailOrPassword";
    private String userUsername = "userUsername";
    private String userPassword = "userPassword";
    private String userKey = "userKey";
    private String personType = "personType";
    private String userType = "userType";

    /**
     * @param context getApplicationContext();
     */
    public SessionHandler(Context context) {
        preferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
    }

    public void setId(int id) {
        putIntValue(user_id, id);
    }

    public String getID() {
        return preferences.getString(user_id, null);
    }

    public void setUserTitle(String title) {
        putStringValue(user_title, title);
    }

    public String getUserTitle() {
        return preferences.getString(user_title, null);
    }

    public void setUserSurname(String surname) {
        putStringValue(user_surname, surname);
    }

    public String getUserSurname() {
        return preferences.getString(user_surname, null);
    }

    public void setUserFirstname(String firstname) {
        putStringValue(userFirstname, firstname);
    }

    public String getUserFirstname() {
        return preferences.getString(userFirstname, null);
    }

    public void setUserDob(String value) {
        putStringValue(userDob, value);
    }

    public String getDob() {
        return preferences.getString(userDob, null);
    }

    public void setUserEmail(String value) {
        putStringValue(userEmail, value);
    }

    public String getUserEmail() {
        return preferences.getString(userEmail, null);
    }

    public void setUserEmailOrPassword(String value) {
        putStringValue(userEmailOrPassword, value);
    }

    public String getEmailOrPassword() {
        return preferences.getString(userEmailOrPassword, null);
    }

    public void setUserUsername(String value) {
        putStringValue(userUsername, value);
    }

    public String getUsername() {
        return preferences.getString(userUsername, null);
    }

    public void setUserPassword(String value) {
        putStringValue(userPassword, value);
    }

    public String getPassword() {
        return preferences.getString(userPassword, null);
    }

    public void setUserKey(String value) {
        putStringValue(userKey, value);
    }

    public String getUserKey() {
        return preferences.getString(userKey, null);
    }

    public void setPersonType(String value) {
        putStringValue(personType, value);
    }

    public boolean getPersonType() {
        return preferences.getBoolean(personType, false);
    }

    public void setUserType(String value) {
        putStringValue(userType, value);
    }

    public String getUserType() {
        return preferences.getString(userType, null);
    }

    private void putIntValue(String key, Integer value) {
        editor.putInt(key, value);
        editor.commit();
    }

    private void putStringValue(String key, String value) {
        editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void putBooleanValue(String key, Boolean value) {
        editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public void clearAll() {
        editor.clear();
        editor.commit();
    }
}
