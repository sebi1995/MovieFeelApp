package com.example.zdroa.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieUtils {

    public static String getMoviesRequestUrl(int filmId) {
        return "https://api.themoviedb.org/3/movie/" + filmId + "?api_key=" + AppSettings.API_KEY;
    }

    public static String getMovieYoutubeThumbnailImgUrl(String movieYoutubeThumbnailUrlPart) {
        return "http://img.youtube.com/vi/" + movieYoutubeThumbnailUrlPart + "/default.jpg";
    }


    public static boolean hasNetworkConnection(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        if (network == null) {
            return false;
        }
        NetworkCapabilities activeNetworkCapabilities = connectivityManager.getNetworkCapabilities(network);
        return activeNetworkCapabilities != null && (
                activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        activeNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
    }

    public static CharSequence getLineSeparator() {
        return System.getProperty("line.separator");
    }

    public static void putErrorMessageInTextView(TextView errorMessageTv, List<String> errorLines) {
        errorMessageTv.setTextColor(Color.RED);
        errorMessageTv.setText(convertToMultiLineString(errorLines));
    }

    public static String convertToMultiLineString(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(lines.get(0));
        for (int i = 1; i < lines.size(); i++) {
            sb.append(getLineSeparator());
            sb.append(lines.get(i));
        }
        return sb.toString();
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String convertIntegerListToString(List<Integer> watchedMoviesList) {
        StringBuilder stringBuilder = new StringBuilder(watchedMoviesList.get(0));
        for (int i = 1; i < watchedMoviesList.size(); i++) {
            stringBuilder.append(((String) getLineSeparator()));
            stringBuilder.append(watchedMoviesList.get(i));
        }
        return stringBuilder.toString();
    }

    public static List<Integer> convertStringToIntegerList(String string) {
        if (TextUtils.isEmpty(string)) {
            return Collections.emptyList();
        }
        return Stream.of(string.split((String) getLineSeparator()))
                .filter(value -> !TextUtils.isEmpty(value))
                .collect(Collectors.toList())
                .stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}
