package com.example.zdroa.myapplication.utils;

import android.util.Log;

import javax.inject.Inject;

public class Logger {

    private final Class<?> mClass;

    public Logger(Class<?> mClass) {
        this.mClass = mClass;
    }

    public void logError(String errorMessage) {
        if (AppSettings.LOGGING_ENABLED) {
            Log.e(mClass.getSimpleName(), getMessage(errorMessage));
        }
    }

    public void logError(Exception exception) {
        if (AppSettings.LOGGING_ENABLED) {
            Log.e(mClass.getSimpleName(), getMessage(exception));
        }
    }

    public void logInfo(String errorMessage) {
        if (AppSettings.LOGGING_ENABLED) {
            Log.i(mClass.getSimpleName(), getMessage(errorMessage));
        }
    }

    public void logInfo(Exception exception) {
        if (AppSettings.LOGGING_ENABLED) {
            Log.i(mClass.getSimpleName(), getMessage(exception));
        }
    }

    private String getMessage(Object o) {
        if (o instanceof Exception) {
            Exception exception = (Exception) o;
            return exception.getClass().getSimpleName() + " -> " + exception.getMessage() + ".";
        } else {
            return "message -> " + o + ".";
        }
    }

}
