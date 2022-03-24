package com.example.zdroa.myapplication.activities.main;

import static com.example.zdroa.myapplication.handlers.UserSessionHandler.USER_WATCHED_MOVIES;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.example.zdroa.myapplication.MainActivity;
import com.example.zdroa.myapplication.PopulateListsActivity;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.main.watchedmovies.WatchedListActivity;
import com.example.zdroa.myapplication.activities.main.movies.MoviesActivity;
import com.example.zdroa.myapplication.activities.questionnaire.QuestionnaireActivity;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private UserSessionHandler userSession;
    private getIDSFromDB getIDSFromDB = new getIDSFromDB();
    private getWatchedListFromDB getWatchedListFromDB = new getWatchedListFromDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userSession = new UserSessionHandler(getApplicationContext());
        String personType = userSession.getPersonType();
        Integer userId = userSession.getId();

        TextView textView = (TextView) findViewById(R.id.login_area_tv_name);
        textView.setText(userSession.getTitle() + " " + userSession.getFirstName() + " " + userSession.getSurname());


        if (personType == null) {
            startActivity(new Intent(this, QuestionnaireActivity.class));
            finish();
        } else {
//            getIDSFromDB.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
//            getWatchedListFromDB.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        }
        if (userSession.getUserType().equals("admin")) {
            Button gotopop = findViewById(R.id.bGoToPopulateLists);
            gotopop.setVisibility(View.VISIBLE);
            gotopop.setOnClickListener(v -> startActivity(new Intent(this, PopulateListsActivity.class)));
        }
        findViewById(R.id.login_area_b_movies)
                .setOnClickListener(v -> startActivity(new Intent(this, MoviesActivity.class)));
        findViewById(R.id.login_area_b_watchedlist)
                .setOnClickListener(v -> startActivity(new Intent(this, WatchedListActivity.class)));
    }

    public void login_area_logout(View v) {
        userSession.clearAll();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }


    class getIDSFromDB extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        String id = jsonResponse.getString("id");
//                        session.setVar("ids_list", id, null);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
//            Volley.newRequestQueue(LoginAreaActivity.this).add(HttpRequestHandler.getMovieIdsByPersonType(responseListener, pType));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    class getWatchedListFromDB extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        userSession.setWatchedMovies(jsonResponse.getString(USER_WATCHED_MOVIES));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };

//            Volley.newRequestQueue(LoginAreaActivity.this)
//                    .add(HttpRequestHandler.getWatchedListByUserId(responseListener, userId));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
