package com.example.zdroa.myapplication.utils;

import android.util.Log;

public class MyLogger {

    private final Class<?> aClass;

    public MyLogger(Class<?> aClass) {
        this.aClass = aClass;
    }

    public void logError(String errorMessage) {
        Log.e(aClass.getName() + ": ", errorMessage == null ? "no message" : errorMessage);
    }
}
