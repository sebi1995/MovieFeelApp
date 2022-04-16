package com.example.zdroa.myapplication;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.activities.accountmanagement.LoginActivity;
import com.example.zdroa.myapplication.activities.accountmanagement.RegisterActivity;
import com.example.zdroa.myapplication.activities.main.movies.MoviesActivity;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;
import com.example.zdroa.myapplication.repositories.MovieRepository;
import com.example.zdroa.myapplication.services.MovieService;
import com.example.zdroa.myapplication.utils.AppSettings;

public class MainActivity extends AppCompatActivity implements ActivityNavigator {

    public UserSessionHandler userSessionHandler;
    private MovieService movieService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userSessionHandler = new UserSessionHandler(getApplicationContext());
        movieService = new MovieService(new MovieRepository(getApplicationContext()));

        if (AppSettings.LOGIN_SYSTEM_ENABLED) {
            redirectIfSessionDoesNotMeetRequirements(userSessionHandler, this);
        } else {
            launchActivityWithFinish(this, MoviesActivity.class);
        }

        findViewById(R.id.main_go_to_login_card_view).setOnClickListener(view -> launchActivity(this, LoginActivity.class));
        findViewById(R.id.main_go_to_register_card_view).setOnClickListener(view -> launchActivity(this, RegisterActivity.class));
    }
}