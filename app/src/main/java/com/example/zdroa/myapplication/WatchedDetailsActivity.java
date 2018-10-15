package com.example.zdroa.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.zdroa.myapplication.aid.ConvertDbIdsToHashMapII;
import com.example.zdroa.myapplication.requests.AddToWatchedList;
import com.example.zdroa.myapplication.requests.GetWatchedList;
import com.example.zdroa.myapplication.session.Session_Class;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class WatchedDetailsActivity extends AppCompatActivity {

    private static String movie_id, title, status, release_date, adult, vote_average, runtime, original_language, overview, poster;
    private static boolean watched = false;
    private static ArrayList<String> comments, genres, production_countries, spoken_languages, youtube_links;
    private TextView movieWatched, noTrailersToShow;
    private ImageView trailer1, trailer2;

    private String userID;

    private static Session_Class session_class;

    private Intent intent;

    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watched_details);
        fa = this;

        intent = this.getIntent();
        session_class = new Session_Class(getApplicationContext());
        userID = session_class.getID();

        movieWatched = (TextView) findViewById(R.id.watched_details_tv_favourite);
        movieWatched.setTextColor(Color.RED);

        noTrailersToShow = (TextView) findViewById(R.id.watched_details_tv_no_trailers);

        trailer1 = (ImageView) findViewById(R.id.watched_details_iv_trailer_1);
        trailer2 = (ImageView) findViewById(R.id.watched_details_iv_trailer_2);


        new getWatchedListFromDB().execute();

        title = intent.getStringExtra("title");
        status = intent.getStringExtra("status");
        release_date = intent.getStringExtra("release_date");
        adult = intent.getStringExtra("adult");
        vote_average = intent.getStringExtra("vote_average");
        runtime = intent.getStringExtra("runtime");
        original_language = intent.getStringExtra("original_language");
        overview = intent.getStringExtra("overview");
        poster = intent.getStringExtra("poster");
        movie_id = intent.getStringExtra("id");

        genres = intent.getStringArrayListExtra("genres");
        production_countries = intent.getStringArrayListExtra("production_countries");
        spoken_languages = intent.getStringArrayListExtra("spoken_languages");
        youtube_links = intent.getStringArrayListExtra("youtube_links");


        setTextViews(intent, "title", R.id.watched_details_tv_movie_title);
        setTextViews(intent, "status", R.id.watched_details_tv_release_status);
        setTextViews(intent, "release_date", R.id.watched_details_tv_release_date);
        setTextViews(intent, "adult", R.id.watched_details_tv_adult);
        setTextViews(intent, "vote_average", R.id.watched_details_tv_vote_average);
        setTextViews(intent, "runtime", R.id.watched_details_tv_runtime);
        setTextViews(intent, "original_language", R.id.watched_details_tv_original_language);
        setTextViews(intent, "overview", R.id.watched_details_tv_overview);


        populateFromArrayLists(intent, "genres", R.id.watched_details_tv_genres);
        populateFromArrayLists(intent, "production_countries", R.id.watched_details_tv_production_countries);
        populateFromArrayLists(intent, "spoken_languages", R.id.watched_details_tv_spoken_languages);

        YouTubeChecks();
        populatePosters();

    }

    private void YouTubeChecks() {
        Space space = (Space) findViewById(R.id.watched_details_space_between_trailers);
        if (youtube_links.get(0).contains("null")) {
            trailer1.setVisibility(View.GONE);
            space.setVisibility(View.GONE);

        } else {
            trailer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(youtube_links.get(0));
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com" +
                            "/watch?v=" + youtube_links.get(0)));
                    startActivity(browserIntent);
                }
            });
            Picasso.with(this)
                    .load("http://img.youtube.com/vi/" + youtube_links.get(0) + "/default.jpg")
                    .into(trailer1);
        }

        if (youtube_links.get(1).contains("null")) {
            trailer2.setVisibility(View.GONE);
            space.setVisibility(View.GONE);
        } else {
            trailer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com" +
                            "/watch?v=" + youtube_links.get(1)));
                    startActivity(browserIntent);
                }
            });
            Picasso.with(this)
                    .load("http://img.youtube.com/vi/" + youtube_links.get(1) + "/default.jpg")
                    .into(trailer2);
        }

        if (youtube_links.get(0).contains("null") && youtube_links.get(1).contains("null")) {
            noTrailersToShow.setVisibility(View.VISIBLE);
            space.setVisibility(View.GONE);

        }
    }

    private void populatePosters() {
        if (intent != null && intent.hasExtra("poster")) {
            poster = intent.getStringExtra("poster");
            ImageView iv = (ImageView) findViewById(R.id.watched_details_iv_poster);

            Picasso.with(this)
                    .load("http://image.tmdb.org/t/p/w300/" + poster)
                    .into(iv);

        }
    }

    private void populateFromArrayLists(Intent intent, String putExtraString, int resource) {
        ArrayList<String> list = new ArrayList<>(intent.getStringArrayListExtra(putExtraString));
        TextView tv = (TextView) findViewById(resource);

        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.size() == 1) {
                    tv.append(list.get(i));
                } else {
                    switch (i) {
                        case 0:
                            tv.append(list.get(0));
                            break;
                        default:
                            tv.append(", " + list.get(i));
                    }
                }
            }
        } else tv.append("no available information.");
    }

    private void setTextViews(Intent intent, String putextraString, int resource) {
        TextView tv = (TextView) findViewById(resource);

        if (intent != null && intent.hasExtra(putextraString) && intent.getStringExtra(putextraString).length() != 0) {
            String staticString = intent.getStringExtra(putextraString);


            switch (putextraString) {
                case "title":
                    tv.setText(staticString);
                    break;
                case "status":
                    tv.setText("Release Status: " + staticString);
                    break;
                case "release_date":
                    String day = staticString.substring(staticString.length() - 2, staticString.length());
                    String month = staticString.substring(staticString.length() - 5, staticString.length() - 3);
                    String year = staticString.substring(0, 4);
                    staticString = day + "/" + month + "/" + year;
                    staticString = "Release Date: " + staticString;
                    tv.setText(staticString);
                    break;
                case "adult":
                    if (staticString == "true") {
                        tv.setText(" Yes");
                    } else {
                        tv.setText(" No");
                    }
                    break;
                case "vote_average":
                    tv.setText("Rating: " + staticString + "/" + "10");
                    break;
                case "runtime":
                    tv.setText("Runtime: " + staticString + " Minutes");
                    break;
                case "original_language":
                    Locale loc = new Locale(staticString);
                    String name = loc.getDisplayLanguage(loc);
                    tv.setText("Original Language: " + name);
                    break;
                case "overview":
                    tv.setText(staticString);
                    break;
            }
        } else tv.setText("N/A");
    }

    public void CheckIfAddedToWatched(View v) {
        if (!watched) {
            new setWatchedList().execute();
        } else {
            new removeFromWatchedList().execute();
        }
    }

    private class getWatchedListFromDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            String id = jsonResponse.getString("watched_list");
                            session_class.setVar("watched_list", id, null);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            GetWatchedList getWatchedList = new GetWatchedList(userID, responseListener);
            RequestQueue queue = Volley.newRequestQueue(fa);
            queue.add(getWatchedList);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            HashMap<Integer, Integer> hashMap = new HashMap<>(getStringOfIdsFromDB(session_class.getWatchedList()));
            System.out.println(hashMap);
            if (hashMap.containsValue(Integer.parseInt(movie_id))) {
                watched = true;
                movieWatched.setText("REMOVE FROM WATCHED LIST");
                movieWatched.setTextColor(Color.GREEN);
            } else {
                watched = false;
                movieWatched.setText("ADD TO WATCHED LIST");
                movieWatched.setTextColor(Color.RED);
            }
        }
    }

    private class setWatchedList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            String movieId = movie_id;
            String watchedList = session_class.getWatchedList();

            switch (movieId.length()) {
                case 1:
                    movieId = "0000" + movieId;
                    break;
                case 2:
                    movieId = "000" + movieId;
                    break;
                case 3:
                    movieId = "00" + movieId;
                    break;
                case 4:
                    movieId = "0" + movieId;
                    break;
            }

            watchedList += movieId;

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            String id = jsonResponse.getString("watched_list");
                            session_class.setVar("watched_list", id, null);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            AddToWatchedList addToWatchedList = new AddToWatchedList(userID, watchedList, responseListener);
            RequestQueue queue = Volley.newRequestQueue(fa);
            queue.add(addToWatchedList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            watched = true;
            movieWatched.setText("REMOVE FROM WATCHED LIST");
            movieWatched.setTextColor(Color.GREEN);
        }
    }

    private class removeFromWatchedList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            String QSTRING = null;

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            String watchedList = jsonResponse.getString("watched_list");
                            session_class.setVar("watched_list", watchedList, null);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };


            String s = movie_id;//12
            String watchedList = session_class.getWatchedList();

            switch (s.length()) {
                case 1:
                    s = "0000" + s;
                    break;
                case 2:
                    s = "000" + s;
                    break;
                case 3:
                    s = "00" + s;
                    break;
                case 4:
                    s = "0" + s;
                    break;
            }

            //s = 00012
            HashMap<Integer, Integer> map = new HashMap<>(new ConvertDbIdsToHashMapII().getHasMap(watchedList));

            if (map.containsValue(Integer.parseInt(movie_id))) {
                QSTRING = watchedList.replace(s, "");
                AddToWatchedList addToWatchedList = new AddToWatchedList(userID, QSTRING, responseListener);
                RequestQueue queue = Volley.newRequestQueue(fa);
                queue.add(addToWatchedList);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            watched = false;
            movieWatched.setText("ADD TO WATCHED LIST");
            movieWatched.setTextColor(Color.RED);
        }
    }


    private HashMap<Integer, Integer> getStringOfIdsFromDB(String STRING_LIST_OF_IDS) {
        //String s = "00001, 00002, 00003, 00004, 00005, 00006, 00007, 00008, 00009, 00010, 00011, 00012, 00013, 00100, 01000, 01000, 01212, 10000, 23000";
        String s = STRING_LIST_OF_IDS;
        s = s.replace(",", "");
        s = s.replace(" ", "");

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length() / 5; i++) {
            String s_s = s.substring(i * 5, (i * 5) + 5);

            if ("0000".equals(s_s.substring(0, 4))) {
                map.put(i, Integer.parseInt(s_s.substring(4, 5)));
            } else if ("000".equals(s_s.substring(0, 3))) {
                map.put(i, Integer.parseInt(s_s.substring(3, 5)));
            } else if ("00".equals(s_s.substring(0, 2))) {
                map.put(i, Integer.parseInt(s_s.substring(2, 5)));
            } else if ("0".equals(s_s.substring(0, 1))) {
                map.put(i, Integer.parseInt(s_s.substring(1, 5)));
            } else map.put(i, Integer.parseInt(s_s));
        }
        return map;
    }
}
