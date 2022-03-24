package com.example.zdroa.myapplication.activities.main.movies;

import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.ADULT;
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
import static com.example.zdroa.myapplication.utils.MovieJsonFieldNames.YOUTUBE_LINKS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.main.movies.details.DetailsActivity;
import com.example.zdroa.myapplication.models.MovieModel;
import com.example.zdroa.myapplication.models.moviesubmodels.ProductionCountry;
import com.example.zdroa.myapplication.models.moviesubmodels.Result;
import com.example.zdroa.myapplication.models.moviesubmodels.SpokenLanguage;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;
import com.example.zdroa.myapplication.tasks.implementation.MovieGetter;
import com.example.zdroa.myapplication.tasks.HttpRequestRunner;
import com.example.zdroa.myapplication.utils.AppSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MoviesActivity extends AppCompatActivity {


    private List<MovieModel> cachedData = new ArrayList<>();

    private final AtomicInteger page = new AtomicInteger(1);

    private ListView listView;
    private ImageView previousIv;
    private ImageView nextIv;
    private ProgressBar progressBar;

    private UserSessionHandler session_handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        session_handler = new UserSessionHandler(getApplicationContext());

        RelativeLayout layout = findViewById(R.id.movies_ll);
        progressBar = new ProgressBar(MoviesActivity.this, null, android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar, params);
        hideProgressBar();
        // TODO: 22/03/2022 get watched movies and somehow show if a movie is watched

        listView = findViewById(R.id.movies_lvMovies);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            MovieModel movie = cachedData.get(position + ((page.get() - 1) * AppSettings.MOVIES_PER_PAGE_LIMIT));
            startActivity(
                    new Intent(MoviesActivity.this, DetailsActivity.class)
                            .putExtra(ID, movie.getId())
                            .putExtra(TITLE, movie.getTitle())
                            .putExtra(POSTER_PATH, movie.getPosterPath())
                            .putExtra(ADULT, movie.getAdult())
                            .putExtra(OVERVIEW, movie.getOverview())
                            .putExtra(RUNTIME, movie.getRuntime())
                            .putExtra(RELEASE_STATUS, movie.getStatus())
                            .putExtra(RELEASE_DATE, movie.getReleaseDate())
                            .putExtra(VOTE_AVERAGE, movie.getVoteAverage())
                            .putExtra(ORIGINAL_LANGUAGE, movie.getOriginalLanguage())
                            .putStringArrayListExtra(PRODUCTION_COUNTRIES, (ArrayList<String>) movie.getProductionCountries().stream().map(ProductionCountry::getName).collect(Collectors.toList()))
                            .putStringArrayListExtra(SPOKEN_LANGUAGES, (ArrayList<String>) movie.getSpokenLanguages().stream().map(SpokenLanguage::getName).collect(Collectors.toList()))
                            .putStringArrayListExtra(YOUTUBE_LINKS, (ArrayList<String>) movie.getYoutubeVideos().getVideos().stream().map(Result::getUrlEnd).collect(Collectors.toList()))
            );
        });

        nextIv = findViewById(R.id.movies_iv_next_results);
        nextIv.setOnClickListener(view -> loadDataIntoListView(page.incrementAndGet()));

        previousIv = findViewById(R.id.movies_iv_previous_results);
        previousIv.setOnClickListener(view -> loadDataIntoListView(page.decrementAndGet()));

        loadDataIntoListView(page.get());

        setNextIvActive();
        setPreviousIvInactive();

        findViewById(R.id.movies_b_exit_activity).setOnClickListener(v -> finish());
    }


    private void loadDataIntoListView(int page) {
        showProgressBar();
        if (page * AppSettings.MOVIES_PER_PAGE_LIMIT <= cachedData.size()) {
            int offset = (page - 1) * AppSettings.MOVIES_PER_PAGE_LIMIT;
            updateListView(cachedData.subList(offset, offset + AppSettings.MOVIES_PER_PAGE_LIMIT));
            hideProgressBar();
        } else {
            HttpRequestRunner.executeAsync(new MovieGetter(getLastFetchedMovie()), results -> {
                cachedData.addAll(results);
                updateListView(results);
                hideProgressBar();
            });
        }
        if (page == 1) {
            setPreviousIvInactive();
        } else if (page == 2) {
            setPreviousIvActive();
        }
    }

    private void updateListView(List<MovieModel> data) {
        listView.setAdapter(new MoviesListViewAdaptor(this, data));
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private Integer getLastFetchedMovie() {
        return cachedData.isEmpty() ? 0 : cachedData.get(cachedData.size() - 1).getId();
    }

    private void setNextIvActive() {
        nextIv.setImageResource(R.mipmap.ic_next_results_active);
        nextIv.setEnabled(true);
    }

    private void setNextIvInactive() {
        nextIv.setImageResource(R.mipmap.ic_next_results_inactive);
        nextIv.setEnabled(false);
    }

    private void setPreviousIvActive() {
        previousIv.setImageResource(R.mipmap.ic_previous_results_active);
        previousIv.setEnabled(true);
    }

    private void setPreviousIvInactive() {
        previousIv.setImageResource(R.mipmap.ic_previous_results_inactive);
        previousIv.setEnabled(false);
    }
}
