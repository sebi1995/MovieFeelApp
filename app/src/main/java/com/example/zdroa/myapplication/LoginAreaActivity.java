package com.example.zdroa.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.zdroa.myapplication.handlers.HttpRequestHandler;
import com.example.zdroa.myapplication.session.Session_Class;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginAreaActivity extends AppCompatActivity {

    private Session_Class session;
    private String pType;
    private String userID;
    private getIDSFromDB getIDSFromDB = new getIDSFromDB();
    private getWatchedListFromDB getWatchedListFromDB = new getWatchedListFromDB();

    private Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_area);

        fa = this;

        session = new Session_Class(getApplicationContext());
        pType = session.getUserType();
        userID = session.getID();

        TextView textView = (TextView) findViewById(R.id.login_area_tv_name);
        textView.setText(session.getTitle() + " " + session.getFirstname() + " " + session.getSurname());


        if (pType == null) {
            finish();
            startActivity(new Intent(LoginAreaActivity.this, QuestionnaireActivity.class));
        } else {
            getIDSFromDB.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            getWatchedListFromDB.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        }
        if (session.getUsername().equals("admin")) {
            Button gotopop = (Button) findViewById(R.id.bGoToPopulateLists);
            gotopop.setVisibility(View.VISIBLE);
            gotopop.setOnClickListener(v -> startActivity(new Intent(fa, PopulateListsActivity.class)));
        }
        findViewById(R.id.login_area_b_movies)
                .setOnClickListener(v -> startActivity(new Intent(fa, MoviesActivity.class)));
        findViewById(R.id.login_area_b_watchedlist)
                .setOnClickListener(v -> startActivity(new Intent(fa, WatchedListActivity.class)));
    }

    public void login_area_logout(View v) {
        session.clearAll();
        finish();
        startActivity(new Intent(fa, MainActivity.class));
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
                        session.setVar("ids_list", id, null);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            Volley.newRequestQueue(LoginAreaActivity.this).add(HttpRequestHandler.getMovieIdsByPersonType(responseListener, pType));
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
                        String id = jsonResponse.getString("watched_list");
                        session.setVar("watched_list", id, null);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };

            Volley.newRequestQueue(LoginAreaActivity.this)
                    .add(HttpRequestHandler.getWatchedListByUserId(responseListener, userID));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
