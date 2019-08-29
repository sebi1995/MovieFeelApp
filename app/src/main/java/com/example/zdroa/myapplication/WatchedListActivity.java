package com.example.zdroa.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.example.zdroa.myapplication.aid.MoviesHttpHandler;
import com.example.zdroa.myapplication.session.SessionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WatchedListActivity extends AppCompatActivity {

    private static HashMap<Integer, Integer> watchedList;

    private static String API_KEY = "b692b9da86f1cf0c1b623ea6e2770101";
    private int sPos = 0, endPos = 20, currentPageNum = 1;

    private ListView listView;
    private ImageView ivPrevious, ivNext, ivExit;
    private Boolean previous_b, next_b = true;
    private TextView tvPageNum, tvResNum;

    private static HashMap<Integer, Integer> IDS;
    private int IDS_SIZE, IDS_REMAINDER, TOTAL_PAGES;
    private static HashMap<Integer, String> IDS_AFTER_QUERY, TITLES, POSTERS, ADULT, OVERVIEW, RUNTIME, STATUS,
            RELEASE_DATE, VOTE_AVERAGE, ORIGINAL_LANGUAGE;
    private static HashMap<Integer, Boolean> WATCHED;
    private static ArrayList<HashMap<Integer, String>> GENRES, PRODUCTION_COUNTRIES, SPOKEN_LANGUAGES, YOUTUBE_LINKS;

    public static Activity fa;

    SessionHandler sessionHandler;
    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watched_list);
        fa = this;

        sessionHandler = new SessionHandler(getApplicationContext());

        listView = (ListView) findViewById(R.id.watched_lvMovies);
        ivPrevious = (ImageView) findViewById(R.id.watched_iv_previous_results);
        ivNext = (ImageView) findViewById(R.id.watched_iv_next_results);
        ivExit = (ImageView) findViewById(R.id.watched_b_exit_activity);
        tvPageNum = (TextView) findViewById(R.id.watched_layout_tv_page_no);
        tvResNum = (TextView) findViewById(R.id.watched_layout_tv_result_no);

//        watchedList = new HashMap<>(new ConvertDbIdsToHashMapII().getHasMap(sessionHandler);
        // TODO: 25/08/2019 get watched list

        IDS_SIZE = watchedList.size();
        IDS_REMAINDER = IDS_SIZE % 20;

        new GetMoviesTask().execute();
        previous_b = false;

        if ((((IDS_SIZE - IDS_REMAINDER) / 20) + 1) == 1) {
            TOTAL_PAGES = (((IDS_SIZE - IDS_REMAINDER) / 20) + 1);
            ivNext.setEnabled(false);
        } else if ((((IDS_SIZE - IDS_REMAINDER) / 20) - 1) == 0) {
            ivNext.setEnabled(false);
            TOTAL_PAGES = (((IDS_SIZE - IDS_REMAINDER) / 20) + 1);
        }

        tvResNum.setBackgroundColor(Color.parseColor("#efefef"));
        tvResNum.setText("Results: " + IDS_SIZE);

        tvPageNum.setBackgroundColor(Color.parseColor("#efefef"));
        tvPageNum.setText("Page : " + currentPageNum + "/" + TOTAL_PAGES);

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WatchedListActivity.this, WatchedDetailsActivity.class);

                intent.putExtra("id", IDS_AFTER_QUERY.get(position));
                intent.putExtra("title", TITLES.get(position));
                intent.putExtra("poster", POSTERS.get(position));
                intent.putExtra("adult", ADULT.get(position));
                intent.putExtra("overview", OVERVIEW.get(position));
                intent.putExtra("runtime", RUNTIME.get(position));
                intent.putExtra("status", STATUS.get(position));
                intent.putExtra("release_date", RELEASE_DATE.get(position));
                intent.putExtra("vote_average", VOTE_AVERAGE.get(position));
                intent.putExtra("original_language", ORIGINAL_LANGUAGE.get(position));
                intent.putStringArrayListExtra("genres", new ArrayList<>(GENRES.get(position).values()));
                intent.putStringArrayListExtra("production_countries", new ArrayList<>(PRODUCTION_COUNTRIES.get(position).values()));
                intent.putStringArrayListExtra("spoken_languages", new ArrayList<>(SPOKEN_LANGUAGES.get(position).values()));
                intent.putStringArrayListExtra("youtube_links", new ArrayList<>(YOUTUBE_LINKS.get(position).values()));

                startActivity(intent);
            }
        });


        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous_b = true;

                if (IDS_SIZE - IDS_REMAINDER == endPos) {
                    sPos = sPos + 20;
                    endPos = endPos + IDS_REMAINDER;
                    new GetMoviesTask().execute();
                    next_b = false;
                } else {
                    sPos = sPos + 20;
                    endPos = endPos + 20;
                    new GetMoviesTask().execute();

                }
                currentPageNum = currentPageNum + 1;
                tvPageNum.setBackgroundColor(Color.parseColor("#efefef"));
                tvPageNum.setText("Page : " + currentPageNum + "/" + (((IDS_SIZE - IDS_REMAINDER) / 20) + 1));
            }
        });

        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_b = true;

                if (sPos == 20) {
                    sPos = 0;
                    endPos = 20;
                    new GetMoviesTask().execute();

                    previous_b = false;
                    next_b = true;
                } else if (sPos == IDS_SIZE - IDS_REMAINDER) {
                    sPos = sPos - 20;
                    endPos = endPos - 2;
                    new GetMoviesTask().execute();
                } else {
                    sPos = sPos - 20;
                    endPos = endPos - 20;
                    new GetMoviesTask().execute();
                }
                currentPageNum = currentPageNum - 1;
                tvPageNum.setBackgroundColor(Color.parseColor("#efefef"));
                tvPageNum.setText("Page : " + currentPageNum + "/" + (((IDS_SIZE - IDS_REMAINDER) / 20) + 1));
            }
        });
    }

    private class GetMoviesTask extends AsyncTask<Void, Void, Void> {

        private int n = 0;
        private int starting_position = sPos;
        private int ending_position = endPos;

        @Override
        protected Void doInBackground(Void... params) {

            HashMap<Integer, Integer> map_ids = new HashMap<>();

            for (int i = starting_position; i < ending_position; i++) {
                map_ids.put(n, watchedList.get(i));
                n++;
            }

            IDS = new HashMap<>(map_ids);

            populateTreeMaps();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ListViewAdaptor adapter = new ListViewAdaptor(fa, new ArrayList<>());
            listView.setAdapter(adapter);

            if ((((IDS_SIZE - IDS_REMAINDER) / 20) + 1) == 1) {
                next_b = false;
            }
            ivNext.setEnabled(next_b);
            if (next_b) {
                ivNext.setImageResource(R.mipmap.ic_next_results);
            } else ivNext.setImageResource(R.mipmap.ic_next_results_innactive);

            ivPrevious.setEnabled(previous_b);
            if (previous_b) {
                ivPrevious.setImageResource(R.mipmap.ic_previous_results);
            } else ivPrevious.setImageResource(R.mipmap.ic_previous_results_innactive);

            progressDoalog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ivNext.setEnabled(false);
            ivPrevious.setEnabled(false);

            progressDoalog = new ProgressDialog(fa);
            progressDoalog.setMax(100);
            progressDoalog.setMessage("Loading....");
            progressDoalog.setTitle("Fetching movies");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDoalog.setCancelable(false);
            progressDoalog.show();

            ivNext.setImageResource(R.mipmap.ic_next_results);
            ivPrevious.setImageResource(R.mipmap.ic_previous_results_innactive);
        }

        private Void populateTreeMaps() {
            MoviesHttpHandler httpHandler = new MoviesHttpHandler(getApplicationContext());

            IDS_AFTER_QUERY = new HashMap<>();
            TITLES = new HashMap<>();
            POSTERS = new HashMap<>();
            ADULT = new HashMap<>();
            OVERVIEW = new HashMap<>();
            RUNTIME = new HashMap<>();
            STATUS = new HashMap<>();
            RELEASE_DATE = new HashMap<>();
            VOTE_AVERAGE = new HashMap<>();
            ORIGINAL_LANGUAGE = new HashMap<>();

            GENRES = new ArrayList<>();
            PRODUCTION_COUNTRIES = new ArrayList<>();
            SPOKEN_LANGUAGES = new ArrayList<>();
            YOUTUBE_LINKS = new ArrayList<>();

            WATCHED = new HashMap<>();

            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(100);
                    if (IDS.get(i) != null) {

                        String jsonString = "";

                        httpHandler.makeMovieAndYoutubeLinkRequest(IDS.get(i), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);
                            }
                        });


                        if (jsonString != null) {
                            try {
                                //POPULATE ALL THE GRAPHS HERE
                                WATCHED.put(i, false);
                                IDS_AFTER_QUERY.put(i, String.valueOf(IDS.get(i)));
                                getInfo(jsonString, i, TITLES, "title", false, false);
                                getInfo(jsonString, i, POSTERS, "poster_path", false, false);
                                getInfo(jsonString, i, ADULT, "adult", true, false);
                                getInfo(jsonString, i, OVERVIEW, "overview", false, false);
                                getInfo(jsonString, i, RUNTIME, "runtime", false, true);
                                getInfo(jsonString, i, STATUS, "status", false, false);
                                getInfo(jsonString, i, RELEASE_DATE, "release_date", false, false);
                                getInfo(jsonString, i, VOTE_AVERAGE, "vote_average", false, true);
                                getInfo(jsonString, i, ORIGINAL_LANGUAGE, "original_language", false, false);

                                GENRES.add(getArrayItems(jsonString, "genres"));
                                PRODUCTION_COUNTRIES.add(getArrayItems(jsonString, "production_countries"));
                                SPOKEN_LANGUAGES.add(getArrayItems(jsonString, "spoken_languages"));
                                YOUTUBE_LINKS.add(getYoutubeLinks(jsonString));


                            } catch (JSONException e) {
                                return null;
                            }
                        }
                        progressDoalog.setProgress(i * 5);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        private void getInfo(String JSONString, Integer i, HashMap<Integer, String> map, String resultString, boolean Aduld, boolean Int) throws JSONException {
            JSONObject jsonObject = new JSONObject(JSONString);

            if (Aduld) {
                boolean adult = jsonObject.getBoolean(resultString);
                map.put(i, String.valueOf(adult));
            } else if (Int) {
                int runtime = jsonObject.getInt(resultString);
                map.put(i, String.valueOf(runtime));
            } else {
                String string = jsonObject.getString(resultString);
                map.put(i, string);
            }
        }

        private HashMap<Integer, String> getArrayItems(String JSONString, String array_name) throws JSONException {
            HashMap<Integer, String> inner_map = new HashMap<>();
            JSONObject jsonObject = new JSONObject(JSONString);

            JSONArray jsonArray = jsonObject.getJSONArray(array_name);


            for (int p = 0; p < jsonArray.length(); p++) {
                JSONObject jsOBbj = jsonArray.getJSONObject(p);

                inner_map.put(p, jsOBbj.getString("name"));

            }

            return inner_map;
        }

        private HashMap<Integer, String> getYoutubeLinks(String JSONString) throws JSONException {
            HashMap<Integer, String> inner_map = new HashMap<>();
            JSONObject object = new JSONObject(JSONString);
            if (object.has("videos")) {
                JSONObject object1 = object.getJSONObject("videos");

                JSONArray jsonArray = object1.getJSONArray("results");

                if (jsonArray.length() != 0) {
                    if (jsonArray.length() == 1) {
                        JSONObject object2 = jsonArray.getJSONObject(0);

                        inner_map.put(0, object2.getString("key"));
                        inner_map.put(1, "null");
                    } else {
                        JSONObject object2 = jsonArray.getJSONObject(0);
                        JSONObject object3 = jsonArray.getJSONObject(1);

                        inner_map.put(0, object2.getString("key"));
                        inner_map.put(1, object3.getString("key"));
                    }
                } else {
                    inner_map.put(0, "null");
                    inner_map.put(1, "null");
                }
            } else {
                inner_map.put(0, "null");
                inner_map.put(1, "null");
            }
            return inner_map;
        }
    }
}
