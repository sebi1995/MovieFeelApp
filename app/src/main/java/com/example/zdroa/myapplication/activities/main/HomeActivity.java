package com.example.zdroa.myapplication.activities.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.BasicActivity;
import com.example.zdroa.myapplication.MainActivity;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.main.movies.MoviesActivity;
import com.example.zdroa.myapplication.activities.main.movies.WatchedMoviesActivity;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;
import com.example.zdroa.myapplication.models.Movie;
import com.example.zdroa.myapplication.repositories.WebAppMovieRepository;
import com.example.zdroa.myapplication.services.WebAppMovieService;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements BasicActivity, ActivityNavigator {

    public UserSessionHandler userSessionHandler;
    public WebAppMovieService movieService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userSessionHandler = new UserSessionHandler(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        movieService = new WebAppMovieService(new WebAppMovieRepository(getApplicationContext()), new Logger(WebAppMovieService.class));

        movieService.fetchWatchedByUserId(
                response -> {
                    userSessionHandler.setWatchedMovies(new GsonBuilder().create().fromJson(response, new TypeToken<List<Movie>>() {
                    }.getType()));
                }, error -> {

                },
                userSessionHandler.getUid()
        );

        TextView textView = findViewById(R.id.login_area_tv_name);
        textView.setText(userSessionHandler.getFirstName() + " " + userSessionHandler.getSurname());


//            getIDSFromDB.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
//            getWatchedListFromDB.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

//        if (userSessionHandler.getUserType() == ADMIN) {
//            Button gotopop = findViewById(R.id.bGoToPopulateLists);
//            gotopop.setVisibility(View.VISIBLE);
//            gotopop.setOnClickListener(v -> startActivity(new Intent(this, PopulateListsActivity.class)));
//        }
        findViewById(R.id.login_area_b_movies).setOnClickListener(v -> launchActivity(this, MoviesActivity.class));
        findViewById(R.id.login_area_b_watchedlist).setOnClickListener(v -> launchActivity(this, WatchedMoviesActivity.class));
    }

    public void login_area_logout(View v) {
        userSessionHandler.clearSession();
        launchActivityWithFinish(this, MainActivity.class);
    }

    @Override
    public void initViews() {

    }
}
