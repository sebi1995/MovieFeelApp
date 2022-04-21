package com.example.zdroa.myapplication;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.zdroa.myapplication.activities.accountmanagement.LoginActivity;
import com.example.zdroa.myapplication.activities.accountmanagement.RegisterActivity;
import com.example.zdroa.myapplication.activities.main.movies.MoviesActivity;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;
import com.example.zdroa.myapplication.utils.AppSettings;

public class MainActivity extends AppCompatActivity implements BasicActivity, ActivityNavigator {

    private UserSessionHandler userSessionHandler;
    private CardView loginCv;
    private CardView registerCv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        userSessionHandler = new UserSessionHandler(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        userSessionHandler.clearSession();// TODO: 21/04/2022 remove

        if (AppSettings.LOGIN_SYSTEM_ENABLED) {
            redirectIfSessionDoesNotMeetRequirements(userSessionHandler, this);
        } else {
            launchActivityWithFinish(this, MoviesActivity.class);
        }

        loginCv.setOnClickListener(view -> launchActivity(this, LoginActivity.class));
        registerCv.setOnClickListener(view -> launchActivity(this, RegisterActivity.class));
    }

    @Override
    public void initViews() {
        loginCv = findViewById(R.id.main_go_to_login_card_view);
        registerCv = findViewById(R.id.main_go_to_register_card_view);
    }
}