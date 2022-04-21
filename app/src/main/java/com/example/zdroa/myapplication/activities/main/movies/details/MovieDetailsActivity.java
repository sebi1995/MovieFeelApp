package com.example.zdroa.myapplication.activities.main.movies.details;

import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.GENRES;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.IS_ADULT;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.ORIGINAL_LANGUAGE;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.OVERVIEW;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.POSTER_PATH;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.PRODUCTION_COUNTRIES;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.RELEASE_DATE;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.RELEASE_STATUS;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.RUNTIME;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.SPOKEN_LANGUAGES;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.TITLE;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.VOTE_AVERAGE;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.BasicActivity;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;
import com.example.zdroa.myapplication.repositories.UserRepository;
import com.example.zdroa.myapplication.services.UserService;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;
import com.example.zdroa.myapplication.utils.MovieUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity implements BasicActivity {


    private UserSessionHandler userSessionHandler;
    TextView movieTitleTv;
    TextView releaseStatusTv;
    TextView releaseDateTv;
    TextView isAdultTv;
    TextView voteAverageTv;
    TextView runTimeTv;
    TextView originalLanguageTv;
    TextView overViewTv;
    TextView genresTv;
    TextView productionCountriesTv;
    TextView spokenLanguagesTv;
    Space space;
    TextView addToWatchedListTv;
    TextView noTrailersToShow;
    ImageView trailer1;
    ImageView trailer2;
    UserService userService;
    Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
        userSessionHandler = new UserSessionHandler(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        userService = new UserService(new UserRepository(getApplicationContext()));
        logger = new Logger(MovieDetailsActivity.class);

        addToWatchedListTv.setTextColor(Color.RED);

//        new getWatchedListFromDB().execute();

        Intent intent = this.getIntent();
        movieTitleTv.setText(MovieActivityNavigator.extractBeautifiedValue(TITLE, intent));
        releaseStatusTv.setText(MovieActivityNavigator.extractBeautifiedValue(RELEASE_STATUS, intent));
        releaseDateTv.setText(MovieActivityNavigator.extractBeautifiedValue(RELEASE_DATE, intent));
        isAdultTv.setText(MovieActivityNavigator.extractBeautifiedValue(IS_ADULT, intent));
        voteAverageTv.setText(MovieActivityNavigator.extractBeautifiedValue(VOTE_AVERAGE, intent));
        runTimeTv.setText(MovieActivityNavigator.extractBeautifiedValue(RUNTIME, intent));
        originalLanguageTv.setText(MovieActivityNavigator.extractBeautifiedValue(ORIGINAL_LANGUAGE, intent));
        overViewTv.setText(MovieActivityNavigator.extractBeautifiedValue(OVERVIEW, intent));
        genresTv.setText(MovieActivityNavigator.extractBeautifiedValue(GENRES, intent));
        productionCountriesTv.setText(MovieActivityNavigator.extractBeautifiedValue(PRODUCTION_COUNTRIES, intent));
        spokenLanguagesTv.setText(MovieActivityNavigator.extractBeautifiedValue(SPOKEN_LANGUAGES, intent));
        initTrailersSection(intent);
        addToWatchedListTv.setOnClickListener(view -> {
//            userSessionHandler.getWatchedMoviesIds()
            Integer movieId = MovieActivityNavigator.extractMovieId(intent);
            if (movieId > 0) {
                boolean foundAndRemovedMovieIdFromWatched = false;
                for (int i = 0; i < userSessionHandler.getWatchedMoviesIds().size(); i++) {
                    if (userSessionHandler.getWatchedMoviesIds().get(i).equals(movieId)) {
                        userService.removeFromWatched(
                                response -> {
                                    userSessionHandler.getWatchedMoviesIds().remove(i);
                                    addToWatchedListTv.setText("ADD TO WATCHED LIST");
                                    addToWatchedListTv.setTextColor(Color.RED);
                                }, error -> {
                                    logger.logError(error);
                                },
                                userSessionHandler.getUid(),
                                movieId);
                    }
                    foundAndRemovedMovieIdFromWatched = true;
                    break;
                }
                if (!foundAndRemovedMovieIdFromWatched) {
                    userService.addToWatchedList(
                            response -> {
                                userSessionHandler.getWatchedMoviesIds().add(movieId);
                                addToWatchedListTv.setText("REMOVE FROM WATCHED LIST");
                                addToWatchedListTv.setTextColor(Color.GREEN);
                            },
                            error -> {
                                logger.logError(error);
                            },
                            userSessionHandler.getUid(),
                            movieId

                    );
                }
            }
        });
    }

    private void initTrailersSection(Intent intent) {
        List<String> youtubeLinks = intent.getStringArrayListExtra("youtube_links");
        if (youtubeLinks.isEmpty()) {
            trailer1.setVisibility(View.GONE);
            space.setVisibility(View.GONE);
            trailer2.setVisibility(View.GONE);
            noTrailersToShow.setVisibility(View.VISIBLE);
        } else if (youtubeLinks.size() == 1) {
            space.setVisibility(View.GONE);
            trailer2.setVisibility(View.GONE);
            trailer1.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeLinks.get(0)));
                startActivity(browserIntent);
            });
            Picasso.with(this)
                    .load(
                            MovieUtils.getMovieYoutubeThumbnailImgUrl(youtubeLinks.get(0))
                    )
                    .into(trailer1);
        } else {
            trailer1.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeLinks.get(0)));
                startActivity(browserIntent);
            });
            Picasso.with(this).load(MovieUtils.getMovieYoutubeThumbnailImgUrl(youtubeLinks.get(0))).into(trailer1);
            trailer2.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeLinks.get(1)));
                startActivity(browserIntent);
            });
            Picasso.with(this).load(MovieUtils.getMovieYoutubeThumbnailImgUrl(youtubeLinks.get(1))).into(trailer2);
        }

        String poster = intent.getStringExtra(POSTER_PATH);
        ImageView posterIv = findViewById(R.id.details_iv_poster);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w300/" + poster).into(posterIv);
    }

    @Override
    public void initViews() {
        addToWatchedListTv = findViewById(R.id.details_add_to_watched_list_text_view);
        noTrailersToShow = findViewById(R.id.details_no_trailers_text_view);
        trailer1 = findViewById(R.id.details_trailer_1_image_view);
        trailer2 = findViewById(R.id.details_trailer_2_image_view);
        movieTitleTv = findViewById(R.id.details_movie_title_text_view);
        releaseStatusTv = findViewById(R.id.details_release_status_text_view);
        releaseDateTv = findViewById(R.id.details_release_date_text_view);
        isAdultTv = findViewById(R.id.details_is_adult_text_view);
        voteAverageTv = findViewById(R.id.details_vote_average_text_view);
        runTimeTv = findViewById(R.id.details_runtime_text_view);
        originalLanguageTv = findViewById(R.id.details_original_language_text_view);
        overViewTv = findViewById(R.id.details_overview_text_view);
        genresTv = findViewById(R.id.details_genres_text_view);
        productionCountriesTv = findViewById(R.id.details_production_countries_text_view);
        spokenLanguagesTv = findViewById(R.id.details_spoken_languages_text_view);
        space = findViewById(R.id.details_between_trailers_space);
    }

    public void CheckIfAddedToWatched(View v) {
        // TODO: 22/04/2022 add implementation
//        if (!watched) {
//            new setWatchedList().execute();
//        } else {
//            new removeFromWatchedList().execute();
//        }
    }

//    private class getWatchedListFromDB extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            Response.Listener<String> responseListener = new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONObject jsonResponse = new JSONObject(response);
//                        boolean success = jsonResponse.getBoolean("success");
//                        if (success) {
//                            String id = jsonResponse.getString("watched_list");
//                            session_class.setVar("watched_list", id, null);
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            Volley.newRequestQueue(fa).add(HttpRequestHandler.getWatchedListByUserId(responseListener, userID));
//
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            HashMap<Integer, Integer> hashMap = new HashMap<>(getStringOfIdsFromDB(session_class.getWatchedList()));
//            if (hashMap.containsValue(Integer.parseInt(movie_id))) {
//                watched = true;
//                movieWatched.setText("REMOVE FROM WATCHED LIST");
//                movieWatched.setTextColor(Color.GREEN);
//            } else {
//                watched = false;
//                movieWatched.setText("ADD TO WATCHED LIST");
//                movieWatched.setTextColor(Color.RED);
//            }
//        }
//    }
//    private class setWatchedList extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            String movieId = movie_id;
//            String watchedList = session_class.getWatchedList();
//
//            switch (movieId.length()) {
//                case 1:
//                    movieId = "0000" + movieId;
//                    break;
//                case 2:
//                    movieId = "000" + movieId;
//                    break;
//                case 3:
//                    movieId = "00" + movieId;
//                    break;
//                case 4:
//                    movieId = "0" + movieId;
//                    break;
//            }
//
//            watchedList += movieId;
//
//            Response.Listener<String> responseListener = new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONObject jsonResponse = new JSONObject(response);
//                        boolean success = jsonResponse.getBoolean("success");
//
//                        if (success) {
//                            String id = jsonResponse.getString("watched_list");
//                            session_class.setVar("watched_list", id, null);
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//
//            Volley.newRequestQueue(fa).add(HttpRequestHandler.addToWatchedList(responseListener, userID, watchedList));
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            watched = true;
//            movieWatched.setText("REMOVE FROM WATCHED LIST");
//            movieWatched.setTextColor(Color.GREEN);
//        }
//    }

//    private class removeFromWatchedList extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            String QSTRING = null;
//
//            Response.Listener<String> responseListener = new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONObject jsonResponse = new JSONObject(response);
//                        boolean success = jsonResponse.getBoolean("success");
//
//                        if (success) {
//                            String watchedList = jsonResponse.getString("watched_list");
//                            session_class.setVar("watched_list", watchedList, null);
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//
//
//            String s = movie_id;//12
//            String watchedList = session_class.getWatchedList();
//
//            switch (s.length()) {
//                case 1:
//                    s = "0000" + s;
//                    break;
//                case 2:
//                    s = "000" + s;
//                    break;
//                case 3:
//                    s = "00" + s;
//                    break;
//                case 4:
//                    s = "0" + s;
//                    break;
//            }
//
//            //s = 00012
//            HashMap<Integer, Integer> map = new HashMap<>(new ConvertDbIdsToHashMapII().getHasMap(watchedList));
//
//            if (map.containsValue(Integer.parseInt(movie_id))) {
//                Volley.newRequestQueue(fa).add(HttpRequestHandler.addToWatchedList(responseListener, userID, watchedList.replace(s, "")));
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            watched = false;
//            movieWatched.setText("ADD TO WATCHED LIST");
//            movieWatched.setTextColor(Color.RED);
//        }
//    }


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
