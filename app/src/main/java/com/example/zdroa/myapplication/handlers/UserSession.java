package com.example.zdroa.myapplication.handlers;

import static com.example.zdroa.myapplication.handlers.UserSession.ParamName.LAST_ACTIVE_TIME;
import static com.example.zdroa.myapplication.handlers.UserSession.ParamName.USER_DATE_OF_BIRTH;
import static com.example.zdroa.myapplication.handlers.UserSession.ParamName.USER_EMAIL_ADDRESS;
import static com.example.zdroa.myapplication.handlers.UserSession.ParamName.USER_FIRSTNAME;
import static com.example.zdroa.myapplication.handlers.UserSession.ParamName.USER_KEY;
import static com.example.zdroa.myapplication.handlers.UserSession.ParamName.USER_PASSWORD;
import static com.example.zdroa.myapplication.handlers.UserSession.ParamName.USER_PERSON_TYPE;
import static com.example.zdroa.myapplication.handlers.UserSession.ParamName.USER_SURNAME;
import static com.example.zdroa.myapplication.handlers.UserSession.ParamName.USER_TYPE;
import static com.example.zdroa.myapplication.handlers.UserSession.ParamName.USER_UID;
import static com.example.zdroa.myapplication.handlers.UserSession.ParamName.USER_WATCHED_MOVIES_IDS;
import static com.example.zdroa.myapplication.handlers.UserSession.SessionCreationStatus.SESSION_CREATED;
import static com.example.zdroa.myapplication.handlers.UserSession.SessionCreationStatus.SESSION_CREATED_QUESTIONNAIRE_NOT_COMPLETED;
import static com.example.zdroa.myapplication.handlers.UserSession.SessionCreationStatus.SESSION_NOT_CREATED;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.zdroa.myapplication.models.Movie;
import com.example.zdroa.myapplication.utilities.PersonType;
import com.example.zdroa.myapplication.utils.MovieUtils;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserSession {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public UserSession(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
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

    public void setPersonType(PersonType personType) {
        putStringOrNull(USER_PERSON_TYPE, personType == null ? "" : personType.toString());
    }
    public void setPersonType(List<PersonType> personType) {
        // TODO: 06/05/2022 test???
        putStringOrNull(USER_PERSON_TYPE, personType == null ? "" : personType.stream().map(pType -> String.valueOf(pType.getIndex())).reduce((s, s2) -> s + s2).get());
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

    public Optional<OffsetDateTime> getLastActiveTime() {
        return Optional.ofNullable(getStringOrNull(LAST_ACTIVE_TIME)).map(OffsetDateTime::parse);
    }

    public void setLastActiveTime(OffsetDateTime offsetDateTime) {
        putStringOrNull(LAST_ACTIVE_TIME, offsetDateTime.toString());
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

    public SessionCreationStatus create(Integer uid, String firstname, String surname, OffsetDateTime dateOfBirth, String emailAddress, String password, String key, UserType userType, PersonType personType, List<Movie> watchedMoviesList, OffsetDateTime lastActiveTime) {
        if (uid != null && firstname != null && surname != null && dateOfBirth != null && emailAddress != null && password != null && key != null && userType != null) {
            setUid(uid);
            setFirstname(firstname);
            setSurname(surname);
            setDateOfBirth(dateOfBirth.toString());
            setEmailAddress(emailAddress);
            setPassword(password);
            setKey(key);
            setWatchedMovies(watchedMoviesList);
            setUserType(userType.toString());
            setLastActiveTime(lastActiveTime);
            if (personType != null) {
                setPersonType(personType);
                return SESSION_CREATED;
            } else {
                return SESSION_CREATED_QUESTIONNAIRE_NOT_COMPLETED;
            }
        } else {
            return SESSION_NOT_CREATED;
        }
    }

    public enum SessionCreationStatus {
        SESSION_CREATED,
        SESSION_NOT_CREATED,
        SESSION_CREATED_QUESTIONNAIRE_NOT_COMPLETED;
    }

    public void clear() {
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
        USER_WATCHED_MOVIES_IDS("watched_movies_ids"),
        LAST_ACTIVE_TIME("last_active_time");

        private final String paramName;

        ParamName(String paramName) {
            this.paramName = paramName;
        }

        @NonNull
        @Override
        public String toString() {
            return this.paramName;
        }
    }
}
