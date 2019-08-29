package com.example.zdroa.myapplication.session;

import android.content.Context;
import android.content.SharedPreferences;

public class Session_Class {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public Session_Class(Context cntx) {
        prefs = cntx.getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setVar(String type, String var, Boolean utype) {
        switch (type) {
            case "user_id":
                editor.putString("user_id", var);
                break;
            case "user_title":
                editor.putString("user_title", var);
                break;
            case "user_surname":
                editor.putString("user_surname", var);
                break;
            case "user_firstname":
                editor.putString("user_firstname", var);
                break;
            case "user_dob":
                editor.putString("user_dob", var);
                break;
            case "user_email":
                editor.putString("user_email", var);
                break;
            case "user_emailorusername":
                editor.putString("user_emailorusername", var);
                break;
            case "user_username":
                editor.putString("user_username", var);
                break;
            case "user_password":
                editor.putString("user_password", var);
                break;
            case "user_key":
                editor.putString("user_key", var);
                break;
            case "user_type_bool":
                editor.putBoolean("user_type_bool", utype);
                break;
            case "user_type":
                editor.putString("user_type", var);
                break;
            case "ids_list":
                editor.putString("ids_list", var);
                break;
            case "watched_list":
                editor.putString("watched_list", var);
                break;
        }
        editor.commit();
    }

/* *
*
* success
* user_id
* user_title
* user_surname
* user_firstname
* user_dob
* user_email
* user_emailorusername
* user_username
* user_password
* user_key
*
* */

    public String getID() {
        return prefs.getString("user_id", null);
    }
    public String getTitle() {
        return prefs.getString("user_title", null);
    }
    public String getSurname() {
        return prefs.getString("user_surname", null);
    }
    public String getFirstname() {
        return prefs.getString("user_firstname", null);
    }
    public String getDob() {
        return prefs.getString("user_dob", null);
    }
    public String getEmail() {
        return prefs.getString("user_email", null);
    }
    public String getEmailOrPassword() {
        return prefs.getString("user_emailorusername", null);
    }
    public String getUsername() {
        return prefs.getString("user_username", null);
    }
    public String getPassword() {
        return prefs.getString("user_password", null);
    }
    public String getUserKey() {
        return prefs.getString("user_key", null);
    }
    public String getIdsList(){ return prefs.getString("ids_list", null);}
    public String getWatchedList(){return prefs.getString("watched_list", null);}

    public boolean getPersonType() {
        boolean getbool = prefs.getBoolean("user_type", false);
        boolean ret = false;
        if(getbool){
            ret = true;
        }
        return ret;
    }

    public String getUserType(){
        return prefs.getString("user_type", null);
    }

    public void clearAll(){
        editor.clear();
        editor.commit();
    }
}
