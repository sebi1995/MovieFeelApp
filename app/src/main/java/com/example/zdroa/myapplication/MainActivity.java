package com.example.zdroa.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.activities.accountmanagement.LoginActivity;
import com.example.zdroa.myapplication.activities.accountmanagement.RegisterActivity;
import com.example.zdroa.myapplication.handlers.UserSession;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;

public class MainActivity extends AppCompatActivity implements ActivityNavigator {

    UserSession userSession;
    Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logger = new Logger(MainActivity.class);
        userSession = new UserSession(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        validateUserSessionAndRedirectToHomeOrRedirect(userSession, this);
    }

    public void mainGoToRegisterActivityOnClick(View view) {
        launchActivity(this, RegisterActivity.class);
    }

    public void mainGoToLoginActivityOnClick(View view) {
        launchActivity(this, LoginActivity.class);
    }
}