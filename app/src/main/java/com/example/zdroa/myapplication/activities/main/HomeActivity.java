package com.example.zdroa.myapplication.activities.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.MainActivity;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.main.movies.MoviesActivity;
import com.example.zdroa.myapplication.activities.main.movies.WatchedMoviesActivity;
import com.example.zdroa.myapplication.handlers.UserSession;
import com.example.zdroa.myapplication.utils.AppSettings;

import java.text.MessageFormat;

public class HomeActivity extends AppCompatActivity implements ActivityNavigator {

    UserSession userSession;
    TextView userNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        userSession = new UserSession(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        userNameTv.setText(MessageFormat.format("{0} {1}", userSession.getFirstName(), userSession.getSurname()));
    }

    public void initViews() {
        userNameTv = findViewById(R.id.home_user_name_text_view);
    }

    public void homeLogoutButtonOnClick(View v) {
        userSession.clear();
        launchActivityWithFinish(this, MainActivity.class);
    }

    public void homeGoToWatchedMoviesActivityOnClick(View view) {
        launchActivity(this, WatchedMoviesActivity.class);
    }

    public void homeGoToMoviesActivityOnClick(View view) {
        launchActivity(this, MoviesActivity.class);
    }
}
