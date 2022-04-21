package com.example.zdroa.myapplication.models;

import com.example.zdroa.myapplication.handlers.UserSessionHandler.UserType;
import com.example.zdroa.myapplication.utilities.PersonType;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

public class User {

    private Integer uid;
    private String firstname;
    private String surname;
    private OffsetDateTime dateOfBirth;
    private String emailAddress;
    private String password;
    private String key;
    private List<Movie> watchedMoviesList;
    private UserType userType;
    private PersonType personType;

    public User() {

    }

    public User(Integer uid) {
        this.uid = uid;
    }

    public User(Integer uid, String firstname, String surname, OffsetDateTime dateOfBirth, String emailAddress, String password, String key, List<Movie> watchedMoviesList, UserType userType, PersonType personType) {
        this.uid = uid;
        this.firstname = firstname;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
        this.password = password;
        this.key = key;
        this.watchedMoviesList = watchedMoviesList;
        this.userType = userType;
        this.personType = personType;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Integer getUid() {
        return uid;
    }

    public List<Movie> getWatchedMoviesList() {
        return watchedMoviesList == null ? Collections.emptyList() : watchedMoviesList;
    }

    public OffsetDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public String getSurname() {
        return surname;
    }

    public UserType getUserType() {
        return userType;
    }

    public User setDateOfBirth(OffsetDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public User setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public User setKey(String key) {
        this.key = key;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setPersonType(PersonType personType) {
        this.personType = personType;
        return this;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public User setUid(Integer uid) {
        this.uid = uid;
        return this;
    }

    public User setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public User setWatchedMoviesList(List<Movie> watchedMoviesList) {
        this.watchedMoviesList = watchedMoviesList;
        return this;
    }
}
