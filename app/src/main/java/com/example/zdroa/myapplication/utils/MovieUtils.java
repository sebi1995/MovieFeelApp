package com.example.zdroa.myapplication.utils;

import static com.example.zdroa.myapplication.utils.AppSettings.API_KEY;
import static com.example.zdroa.myapplication.utils.AppSettings.SYSTEM_LINE_SEPARATOR;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zdroa.myapplication.handlers.UserSession;
import com.example.zdroa.myapplication.models.Movie;
import com.example.zdroa.myapplication.models.User;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieUtils {

    public static String getMoviesRequestUrl(int filmId) {
        return "https://api.themoviedb.org/3/movie/" + filmId + "?api_key=" + API_KEY;
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
            sb.append(SYSTEM_LINE_SEPARATOR);
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
            stringBuilder.append(SYSTEM_LINE_SEPARATOR);
            stringBuilder.append(watchedMoviesList.get(i));
        }
        return stringBuilder.toString();
    }

    public static List<Integer> convertStringToIntegerList(String string) {
        if (TextUtils.isEmpty(string)) {
            return Collections.emptyList();
        }
        return Stream.of(string.split(SYSTEM_LINE_SEPARATOR))
                .filter(value -> !TextUtils.isEmpty(value))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public static Optional<Movie> convertJsonToMovie(String jsonResponse) {
        return Optional.of(new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, (JsonDeserializer<OffsetDateTime>)
                        (json, type, context) -> OffsetDateTime.parse(json.getAsString()))
                .create()
                .fromJson(jsonResponse, Movie.class));
    }

    public static User getTestUser() {
        return new User(1)
                .setFirstname("sebastian")
                .setSurname("zdroana")
                .setDateOfBirth(OffsetDateTime.MAX)
                .setEmailAddress("s.zdroana@gmail.com")
                .setPassword("test")
                .setKey("adw#f3.3@2]f;awfAFWFd")
                .setWatchedMoviesList(Arrays.asList(
                        new Movie(1),
                        new Movie(2),
                        new Movie(3),
                        new Movie(5),
                        new Movie(9),
                        new Movie(14),
                        new Movie(15),
                        new Movie(16),
                        new Movie(20),
                        new Movie(22),
                        new Movie(33),
                        new Movie(55)))
                .setUserType(UserSession.UserType.USER)
//                .setPersonType(PersonType.PARANOID)
                .setLastActiveTime(OffsetDateTime.now());
    }

    public static void hideKeyboard(Activity activity) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public static boolean isUserOver18(OffsetDateTime dateOfBirth) {
        OffsetDateTime minDateOfBirthToBe18 = OffsetDateTime.now().minusYears(18);
        return Duration.between(dateOfBirth, minDateOfBirthToBe18).getSeconds() > 0;
    }

    public static void showLongToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
