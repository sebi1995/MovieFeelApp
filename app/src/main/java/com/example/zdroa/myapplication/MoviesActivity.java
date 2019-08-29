package com.example.zdroa.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.aid.GsonHandler;
import com.example.zdroa.myapplication.aid.MovieUtils;
import com.example.zdroa.myapplication.aid.MoviesHttpHandler;
import com.example.zdroa.myapplication.models.MovieModelWithYoutubeLinks;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {


    public Activity thisActivity;
    private ListView listView;
    private ImageView ivPrevious;
    private ImageView ivNext;
    private ImageView ivExit;
    private TextView tvPageNum;
    private TextView tvResNum;

    private List<MovieModelWithYoutubeLinks> moviesList;

    private final int PAGE_RESULTS_LIMIT_PER_PAGE = MovieUtils.MAX_MOVIES_RESULTS_PER_PAGE;
    private int numberOfMovies;
    private int moviesPageRemainderAfterSplit;
    private int numberOfPages;
    private Integer[] allMovieIds = MovieUtils.GOOD_IDS;
    private int startingMoviesPosition = 0;
    private int endingMoviesPosition = PAGE_RESULTS_LIMIT_PER_PAGE;
    private int currentPageNum = 1;

    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        thisActivity = this;
        moviesList = new ArrayList<>();

        listView = findViewById(R.id.movies_lvMovies);
        ivPrevious = findViewById(R.id.movies_iv_previous_results);
        ivNext = findViewById(R.id.movies_iv_next_results);
        ivExit = findViewById(R.id.movies_b_exit_activity);
        tvPageNum = findViewById(R.id.movies_layout_tv_page_no);
        tvResNum = findViewById(R.id.movies_layout_tv_result_no);

        disableBackButton();
        disablaNextButton();

        new GetMoviesTask().execute();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MoviesActivity.this, DetailsActivity.class);
            intent.putExtra("movieModelWYTLinks", new GsonHandler<MovieModelWithYoutubeLinks>().objectToString(moviesList.get(position)));
            startActivity(intent);
        });


    }

    public void ivPreviousBtnPreviousAction(View view) {
        startingMoviesPosition -= PAGE_RESULTS_LIMIT_PER_PAGE;
        if (currentPageNum == numberOfPages) {
            if (moviesPageRemainderAfterSplit != 0) {
                endingMoviesPosition -= moviesPageRemainderAfterSplit;
            } else {
                endingMoviesPosition -= PAGE_RESULTS_LIMIT_PER_PAGE;
            }
        } else {
            endingMoviesPosition -= PAGE_RESULTS_LIMIT_PER_PAGE;
        }
        --currentPageNum;
        new GetMoviesTask().execute();
    }

    public void ivNextBtnNextAction(View view) {
        startingMoviesPosition += PAGE_RESULTS_LIMIT_PER_PAGE;
        if (currentPageNum == (numberOfPages - 1)) {
            if (moviesPageRemainderAfterSplit > 0) {
                endingMoviesPosition += moviesPageRemainderAfterSplit;
            } else {
                endingMoviesPosition += PAGE_RESULTS_LIMIT_PER_PAGE;
            }
        } else {
            endingMoviesPosition += PAGE_RESULTS_LIMIT_PER_PAGE;
        }
        currentPageNum++;
        new GetMoviesTask().execute();
    }

    public void ivExitBtnExitAction(View view) {
        finish();
    }

    private class GetMoviesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            disablaNextButton();
            disableBackButton();

            progressDoalog = new ProgressDialog(thisActivity);
            progressDoalog.setMax(100);
            progressDoalog.setMessage("Loading....");
            progressDoalog.setTitle("Fetching movies");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDoalog.setCancelable(false);
            progressDoalog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            MoviesHttpHandler httpHandler = new MoviesHttpHandler(getApplicationContext());
            for (int i = startingMoviesPosition; i < endingMoviesPosition; i++) {
                Integer goodId = MovieUtils.GOOD_IDS[i];
                int size = moviesList.size();
                if (i > size) {
                    try {
                        httpHandler.makeMovieAndYoutubeLinkRequest(goodId, response -> {
                            MovieModelWithYoutubeLinks movieModel = new GsonHandler<MovieModelWithYoutubeLinks>().stringToObject(response, MovieModelWithYoutubeLinks.class);
                            if (movieModel != null) {
                                moviesList.add(movieModel);
                            }
                        });
                        Thread.sleep(200);
                        progressDoalog.setProgress(i * 5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            List<MovieModelWithYoutubeLinks> toPassToAdaptor = moviesList;
            if (endingMoviesPosition <= (moviesList.size() + 1)) {
                toPassToAdaptor = new ArrayList<>();
                for (int i = startingMoviesPosition; i < (endingMoviesPosition - 1); i++) {
                    toPassToAdaptor.add(moviesList.get(i));
                }
            }
            ListViewAdaptor adapter = new ListViewAdaptor(thisActivity, toPassToAdaptor);
            listView.setAdapter(adapter);

            recalculatePagesNumbers();
            progressDoalog.dismiss();
            disableEnableBackNextButtons();

            tvResNum.setBackgroundColor(Color.parseColor("#efefef"));
            tvResNum.setText("Results: " + numberOfMovies);

            tvPageNum.setBackgroundColor(Color.parseColor("#efefef"));
            tvPageNum.setText("Page : " + currentPageNum + "/" + numberOfPages);
        }


    }

    private void recalculatePagesNumbers() {
        numberOfMovies = allMovieIds.length;
        moviesPageRemainderAfterSplit = numberOfMovies % PAGE_RESULTS_LIMIT_PER_PAGE;
        numberOfPages = (numberOfMovies - moviesPageRemainderAfterSplit) / PAGE_RESULTS_LIMIT_PER_PAGE;
        if (numberOfMovies - moviesPageRemainderAfterSplit == 0 && numberOfMovies != 0) {
            numberOfPages++;
        }
        if (numberOfMovies > PAGE_RESULTS_LIMIT_PER_PAGE && moviesPageRemainderAfterSplit != 0) {
            numberOfPages++;
        }
    }

    private void disableEnableBackNextButtons() {
        //if there is 1 or 0 pages, disable back and forward buttons
        if (numberOfPages <= 1) {
            disableBackButton();
        }
        //if there is more than 1 page
        if (numberOfPages > 1) {
            //if the current page is not the last page, enable the next button
            if (currentPageNum != numberOfPages) {
                enableNextButton();
            }
            //if the current page is the last page, disable the next button
            if (currentPageNum == numberOfPages) {
                disablaNextButton();
            }
            //if the current page is not the first page, enable the back button
            if (currentPageNum > 1) {
                enableBackButton();
            } else {
                //if the current page is the first page, disable the back button
                disableBackButton();
            }
        }
    }

    private void disableBackButton() {
        ivPrevious.setEnabled(false);
        ivPrevious.setImageResource(R.mipmap.ic_previous_results_innactive);
    }

    private void enableBackButton() {
        ivPrevious.setEnabled(true);
        ivPrevious.setImageResource(R.mipmap.ic_previous_results);
    }

    private void disablaNextButton() {
        ivNext.setEnabled(false);
        ivNext.setImageResource(R.mipmap.ic_next_results_innactive);
    }

    private void enableNextButton() {
        ivNext.setEnabled(true);
        ivNext.setImageResource(R.mipmap.ic_next_results);
    }


}