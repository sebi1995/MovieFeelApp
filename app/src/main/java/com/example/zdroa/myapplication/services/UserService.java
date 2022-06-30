package com.example.zdroa.myapplication.services;

import com.android.volley.Response;
import com.example.zdroa.myapplication.repositories.UserRepository;
import com.example.zdroa.myapplication.utilities.PersonType;
import com.example.zdroa.myapplication.utils.PasswordEncryptor;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(Response.Listener<String> responseListener, Response.ErrorListener errorListener, String firstname, String surname, String dateOfBirth, String emailAddress, String password) throws NoSuchAlgorithmException {
        userRepository.create(responseListener,
                errorListener,
                firstname,
                surname,
                dateOfBirth,
                emailAddress,
                PasswordEncryptor.encrypt(password)
        );
    }

    public void getByEmailAddressAndPassword(Response.Listener<String> responseListener, Response.ErrorListener errorListener, String emailAddress, String password) throws NoSuchAlgorithmException {
        userRepository.getByEmailAddressAndPassword(responseListener, errorListener,
                emailAddress,
                PasswordEncryptor.encrypt(password)
        );
    }

    public void removeFromWatched(Response.Listener<String> responseListener, Response.ErrorListener errorListener, Integer userUid, Integer movieId) {
        userRepository.removeFromWatched(responseListener, errorListener,
                String.valueOf(userUid),
                String.valueOf(movieId)
        );
    }

    public void addToWatchedList(Response.Listener<String> responseListener, Response.ErrorListener errorListener, Integer userUid, Integer movieId) {
        userRepository.addToWatchedList(responseListener, errorListener,
                String.valueOf(userUid),
                String.valueOf(movieId)
        );
    }

    public void registerQuestionnaire(Response.Listener<String> responseListener, Response.ErrorListener errorListener, Integer userUid, Long timerDuration, List<PersonType> personType) {
        userRepository.registerQuestionnaire(responseListener, errorListener,
                String.valueOf(userUid),
                String.valueOf(timerDuration),
                personType.toString() // TODO: 06/05/2022 currently implement??
        );
    }
}
