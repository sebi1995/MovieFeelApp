package com.example.zdroa.myapplication.aid;

import com.google.gson.Gson;

public class GsonHandler<T> {

    public T stringToObject(String jsonString, Class<T> c) {
        return new Gson().fromJson(jsonString, c);
    }

    public String objectToString(T c){
        return new Gson().toJson(c);
    }

}
