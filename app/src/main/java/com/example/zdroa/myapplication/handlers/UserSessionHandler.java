package com.example.zdroa.myapplication.handlers;

import static com.example.zdroa.myapplication.handlers.UserSessionHandler.ParamName.USER_DATE_OF_BIRTH;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.ParamName.USER_EMAIL_ADDRESS;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.ParamName.USER_FIRSTNAME;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.ParamName.USER_KEY;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.ParamName.USER_PASSWORD;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.ParamName.USER_PERSON_TYPE;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.ParamName.USER_SURNAME;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.ParamName.USER_TYPE;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.ParamName.USER_UID;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.ParamName.USER_WATCHED_MOVIES_IDS;

import android.content.SharedPreferences;

import com.example.zdroa.myapplication.models.Movie;
import com.example.zdroa.myapplication.utilities.PersonType;
import com.example.zdroa.myapplication.utils.MovieUtils;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserSessionHandler {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    protected enum ParamName {
        USER_UID("uid"),
        USER_FIRSTNAME("firstname"),
        USER_SURNAME("surname"),
        USER_DATE_OF_BIRTH("date_of_birth"),
        USER_EMAIL_ADDRESS("email_address"),
        USER_PASSWORD("password"),
        USER_KEY("key"),
        USER_PERSON_TYPE("person_type"),
        USER_TYPE("user_type"),
        USER_WATCHED_MOVIES_IDS("watched_movies_ids");

        private final String paramName;

        ParamName(String paramName) {
            this.paramName = paramName;
        }

        @Override
        public String toString() {
            return this.paramName;
        }
    }

    public UserSessionHandler(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        this.sharedPreferences = sharedPreferences;
        this.editor = editor;
    }

    public Integer getUid() {
        return getIntegerOrNull(USER_UID);
    }

    public void setUid(Integer value) {
        putIntegerOrNull(USER_UID, value);
    }

    public String getFirstName() {
        return getStringOrNull(USER_FIRSTNAME);
    }

    public void setFirstname(String value) {
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

    public String getEmailAddress() {
        return getStringOrNull(USER_EMAIL_ADDRESS);
    }

    public void setEmailAddress(String value) {
        putStringOrNull(USER_EMAIL_ADDRESS, value);
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

    public UserType getUserType() {
        return Optional.ofNullable(getStringOrNull(USER_TYPE)).map(UserType::getType).orElse(UserType.USER);
    }

    public void setUserType(String value) {
        putStringOrNull(USER_TYPE, value);
    }

    public List<Integer> getWatchedMoviesIds() {
        return MovieUtils.convertStringToIntegerList(getStringOrNull(USER_WATCHED_MOVIES_IDS));
    }

    public void setWatchedMovies(List<Movie> movies) {
        putStringOrNull(USER_WATCHED_MOVIES_IDS,
                MovieUtils.convertIntegerListToString(movies.stream().map(Movie::getId).collect(Collectors.toList())));
    }

    public boolean hasCompletedQuestionnaire() {
        return !hasNotCompletedQuestionnaire();
    }

    public boolean hasNotCompletedQuestionnaire() {
        return getPersonType() == null;
    }

    public boolean sessionExists() {
        return !sessionDoesNotExist();
    }

    public boolean sessionDoesNotExist() {
        return getUid() == null;
    }

    private Integer getIntegerOrNull(ParamName paramName) {
        int anInt = sharedPreferences.getInt(paramName.toString(), -1);
        return anInt < 0 ? null : anInt;
    }

    private void putIntegerOrNull(ParamName paramName, Integer value) {
        editor.putInt(paramName.toString(), value);
        editor.commit();
    }

    private String getStringOrNull(ParamName paramName) {
        return sharedPreferences.getString(paramName.toString(), null);
    }

    private void putStringOrNull(ParamName paramName, String value) {
        editor.putString(paramName.toString(), value);
        editor.commit();
    }

    public boolean createSession(Integer uid, String firstname, String surname, OffsetDateTime dateOfBirth, String emailAddress, String password, String key, UserType userType, PersonType personType, List<Movie> watchedMoviesList) {
        if (uid != null && firstname != null && surname != null && dateOfBirth != null && emailAddress != null && password != null && key != null && userType != null && personType != null) {
            setUid(uid);
            setFirstname(firstname);
            setSurname(surname);
            setDateOfBirth(dateOfBirth.toString());
            setEmailAddress(emailAddress);
            setPassword(password);
            setKey(key);
            setWatchedMovies(watchedMoviesList);
            setUserType(userType.toString());
            setPersonType(personType.toString());
            return true;
        } else {
            return false;
        }
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

    public enum UserType {
        ADMIN("admin"),
        USER("user");

        private final String value;

        UserType(String value) {
            this.value = value;
        }

        public static UserType getType(String type) {
            for (UserType userType : UserType.values()) {
                if (userType.value.equals(type)) {
                    return userType;
                }
            }
            return USER;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
