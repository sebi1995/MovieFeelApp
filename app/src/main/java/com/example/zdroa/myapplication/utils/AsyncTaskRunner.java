package com.example.zdroa.myapplication.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {

    private final static Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
    private final static Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R> {
        void onComplete(R result);
    }

    public static <R> void execute(Callable<R> request, Callback<R> onResponse) {
        executor.execute(() -> {
            final R result;
            try {
                result = request.call();
                handler.post(() -> onResponse.onComplete(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}