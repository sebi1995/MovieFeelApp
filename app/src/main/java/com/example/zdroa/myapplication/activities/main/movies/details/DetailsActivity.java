package com.example.zdroa.myapplication.activities.main.movies.details;

import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.ADULT;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.GENRES;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.ID;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.ORIGINAL_LANGUAGE;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.OVERVIEW;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.POSTER_PATH;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.PRODUCTION_COUNTRIES;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.RELEASE_DATE;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.RELEASE_STATUS;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.RUNTIME;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.SPOKEN_LANGUAGES;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.TITLE;
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.VOTE_AVERAGE;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.MovieJsonFieldNames;
import com.example.zdroa.myapplication.utils.MovieUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class DetailsActivity extends AppCompatActivity {


    public static Activity fa;
private final     UserSessionHandler userSession = new UserSessionHandler(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        fa = this;
        Intent intent = this.getIntent();
        Integer userId = userSession.getId();
        TextView movieWatched = findViewById(R.id.details_tv_favourite);
        movieWatched.setTextColor(Color.RED);
        TextView noTrailersToShow = findViewById(R.id.details_tv_no_trailers);
        ImageView trailer1 = findViewById(R.id.details_iv_trailer_1);
        ImageView trailer2 = findViewById(R.id.details_iv_trailer_2);

//        new getWatchedListFromDB().execute();

        Integer movieId = intent.getIntExtra(ID, -1);
        List<String> youtubeLinks = intent.getStringArrayListExtra("youtube_links");

        populateTextView(intent, TITLE, R.id.details_tv_movie_title);
        populateTextView(intent, RELEASE_STATUS, R.id.details_tv_release_status);
        populateTextView(intent, RELEASE_DATE, R.id.details_tv_release_date);
        populateTextView(intent, ADULT, R.id.details_tv_adult);
        populateTextView(intent, VOTE_AVERAGE, R.id.details_tv_vote_average);
        populateTextView(intent, RUNTIME, R.id.details_tv_runtime);
        populateTextView(intent, ORIGINAL_LANGUAGE, R.id.details_tv_original_language);
        populateTextView(intent, OVERVIEW, R.id.details_tv_overview);

        populateFromArrayLists(intent, GENRES, R.id.details_tv_genres);
        populateFromArrayLists(intent, PRODUCTION_COUNTRIES, R.id.details_tv_production_countries);
        populateFromArrayLists(intent, SPOKEN_LANGUAGES, R.id.details_tv_spoken_languages);

        Space space = (Space) findViewById(R.id.details_space_between_trailers);
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

    private enum TextViewOutputValueConverter {
        RUNTIME(MovieJsonFieldNames.RUNTIME, Integer.class) {
            @Override
            public String getExtra(Intent intent) {
                return getExtra(intent, extra -> extra + " minutes");
            }
        },
        ADULT(MovieJsonFieldNames.ADULT, Boolean.class) {
            @Override
            public String getExtra(Intent intent) {
                return getExtra(intent, extra -> ((Boolean) extra) ? "Yes" : "No");
            }
        },
        VOTE_AVERAGE(MovieJsonFieldNames.VOTE_AVERAGE, Double.class) {
            @Override
            public String getExtra(Intent intent) {
                return getExtra(intent, extra -> extra + "/10");
            }
        },
        ORIGINAL_LANGUAGE(MovieJsonFieldNames.ORIGINAL_LANGUAGE, String.class) {
            @Override
            public String getExtra(Intent intent) {
                return getExtra(intent, extra -> {
                    Locale loc = new Locale((String) extra);
                    return loc.getDisplayLanguage(loc);
                });

            }
        },
        RELEASE_DATE(MovieJsonFieldNames.RELEASE_DATE, String.class) {
            @Override
            public String getExtra(Intent intent) {
                return getExtra(intent, extra -> {
                    String string = ((String) extra);
                    String day = string.substring(string.length() - 2);
                    String month = string.substring(string.length() - 5, string.length() - 3);
                    String year = string.substring(0, 4);
                    return day + "/" + month + "/" + year;
                });
            }
        },
        DEFAULT;

        private String name;
        private final Class<?> mClass;

        TextViewOutputValueConverter() {
            this.name = null;
            this.mClass = null;
        }

        TextViewOutputValueConverter(String name, Class<?> mClass) {
            this.name = name;
            this.mClass = mClass;
        }

        private static final Map<Class<?>, IntentConverter> map = Map.of(
                Boolean.class, (intent, valueName, function) -> {
                    boolean booleanExtra = intent.getBooleanExtra(valueName, false);
                    return function.apply(booleanExtra);
                },
                Integer.class, (intent, valueName, function) -> {
                    int integerExtra = intent.getIntExtra(valueName, -1);
                    if (integerExtra < 0) {
                        return AppSettings.NO_INFO_AVAILABLE;
                    }
                    return function.apply(integerExtra);
                },
                Double.class, (intent, valueName, function) -> {
                    double doubleExtra = intent.getDoubleExtra(valueName, -1);
                    if (doubleExtra < 0) {
                        return AppSettings.NO_INFO_AVAILABLE;
                    }
                    return function.apply(doubleExtra);
                }
        );

        private String getExtraValue(Intent mIntent, Function<Object, String> mFunction) {
            IntentConverter converter = null;
            if (mClass != null) {
                converter = map.get(mClass);
            }
            if (converter == null) {
                converter = (intent, valueName, function) -> {
                    String extra = intent.getStringExtra(valueName);
                    if (extra == null) {
                        return AppSettings.NO_INFO_AVAILABLE;
                    }
                    return function.apply(extra);
                };
            }
            return converter.getStringValue(mIntent, getName(), mFunction) + ".";
        }

        public String getExtra(Intent intent, Function<Object, String> function) {
            return getExtraValue(intent, function);
        }

        public String getExtra(Intent intent) {
            return getExtraValue(intent, o -> ((String) o));
        }

        public static TextViewOutputValueConverter getTextViewOutputValueConverter(String name) {
            for (TextViewOutputValueConverter converter : values()) {
                if (name.equals(converter.getName())) {
                    return converter;
                }
            }
            return DEFAULT.setName(name);
        }

        public String getName() {
            return this.name;
        }

        private TextViewOutputValueConverter setName(String name) {
            this.name = name;
            return this;
        }

        private interface IntentConverter {
            String getStringValue(Intent intent, String valueName, Function<Object, String> function);
        }
    }

    private void populateTextView(Intent intent, String valueName, int viewId) {
        TextView textView = findViewById(viewId);
        TextViewOutputValueConverter textViewOutputValueConverter = TextViewOutputValueConverter.getTextViewOutputValueConverter(valueName);
        String text = textViewOutputValueConverter.getExtra(intent);
        textView.setText(text);
    }


    private void populateFromArrayLists(Intent intent, String putExtraString, int resource) {
        List<String> data = intent.getStringArrayListExtra(putExtraString);
        TextView tv = (TextView) findViewById(resource);
        if (data != null && !data.isEmpty()) {
            boolean hasAppendedFirstO = false;
            for (String o : data) {
                if (hasAppendedFirstO) {
                    tv.append(System.getProperty("line.separator"));
                    tv.append(o);
                } else {
                    tv.append(o);
                    hasAppendedFirstO = true;
                }
            }
        } else {
            tv.append("no available information.");
        }
    }


    public void CheckIfAddedToWatched(View v) {
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
