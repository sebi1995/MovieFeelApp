package com.example.zdroa.myapplication.activities.main.movies;

import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.IS_ADULT;
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.MOVIE_ID;
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
import static com.example.zdroa.myapplication.activities.main.movies.MovieActivityNavigator.YOUTUBE_LINKS;
import static com.example.zdroa.myapplication.utils.AppSettings.MAX_MOVIES_PER_PAGE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.BasicActivity;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.main.movies.details.MovieDetailsActivity;
import com.example.zdroa.myapplication.handlers.LoadingSpinner;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;
import com.example.zdroa.myapplication.models.Movie;
import com.example.zdroa.myapplication.models.moviesubmodels.ProductionCountry;
import com.example.zdroa.myapplication.models.moviesubmodels.Result;
import com.example.zdroa.myapplication.models.moviesubmodels.SpokenLanguage;
import com.example.zdroa.myapplication.repositories.RestApiRepository;
import com.example.zdroa.myapplication.repositories.UserRepository;
import com.example.zdroa.myapplication.services.RestApiMovieService;
import com.example.zdroa.myapplication.services.UserService;
import com.example.zdroa.myapplication.utilities.MoviesListViewAdaptor;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class WatchedMoviesActivity extends AppCompatActivity implements BasicActivity, ActivityNavigator {

    private ListView moviesLv;
    private ImageView previousIv;
    private ImageView nextIv;
    private TextView pageTitleTv;
    private LoadingSpinner loadingSpinner;

    public UserSessionHandler userSessionHandler;

    public RestApiMovieService restApiMovieService;
    public UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initViews();

        userSessionHandler = new UserSessionHandler(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        redirectIfSessionDoesNotMeetRequirements(userSessionHandler, this);
        userService = new UserService(new UserRepository(getApplicationContext()));
        restApiMovieService = new RestApiMovieService(new RestApiRepository(getApplicationContext()), new Logger(RestApiMovieService.class));
        loadingSpinner = new LoadingSpinner(this, this.getWindow(), moviesLv, findViewById(R.id.activity_movies), true);
        pageTitleTv.setText("Watched Movies");

        AtomicInteger currentPage = new AtomicInteger(0);
        List<Movie> cachedWatchedMovies = new ArrayList<>();
        moviesLv.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = cachedWatchedMovies.get(position + (currentPage.get() * MAX_MOVIES_PER_PAGE));
            startActivity(
                    new Intent(this, MovieDetailsActivity.class)
                            .putExtra(MOVIE_ID, movie.getId())
                            .putExtra(TITLE, movie.getTitle())
                            .putExtra(POSTER_PATH, movie.getPosterPath())
                            .putExtra(IS_ADULT, movie.getAdult())
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
        List<Integer> userWatchedMoviesIds = userSessionHandler.getWatchedMoviesIds();
        previousIv.setOnClickListener(view -> loadDataIntoListView(currentPage.decrementAndGet(), userWatchedMoviesIds, cachedWatchedMovies));
        nextIv.setOnClickListener(view -> loadDataIntoListView(currentPage.incrementAndGet(), userWatchedMoviesIds, cachedWatchedMovies));
        loadDataIntoListView(currentPage.get(), userWatchedMoviesIds, cachedWatchedMovies);
    }

    @Override
    public void initViews() {
        moviesLv = findViewById(R.id.movies_lvMovies);
        nextIv = findViewById(R.id.movies_next_results_image_view);
        previousIv = findViewById(R.id.movies_previous_results_image_view);
        pageTitleTv = findViewById(R.id.movies_page_title_text_view);
    }

    private void loadDataIntoListView(int currentPageNumber, List<Integer> watchedMoviesIds, List<Movie> cachedWatchedMovies) {
        loadingSpinner.showAndHideParentViewAndDisableUserInput();
        int startingIndex = currentPageNumber * MAX_MOVIES_PER_PAGE;
        int endIndex = startingIndex + MAX_MOVIES_PER_PAGE;
        if (endIndex <= cachedWatchedMovies.size()) {
            updateListView(cachedWatchedMovies.subList(startingIndex, endIndex), watchedMoviesIds);
            loadingSpinner.hideAndShowParentViewAndEnableUserInput();
            if (currentPageNumber == 1) {
                setPreviousEnabled();
            }
        } else {
            restApiMovieService.fetchMoviesByIds(
                    movies -> {
                        cachedWatchedMovies.addAll(movies);
                        //if there are more than 20 results, get the first 20 and enable next button
                        if (cachedWatchedMovies.size() > MAX_MOVIES_PER_PAGE) {
                            updateListView(cachedWatchedMovies.subList(startingIndex, endIndex), watchedMoviesIds);
                            setNextEnabled();
                        } else {
                            //less than 20 results, get all results and disable next button
                            updateListView(cachedWatchedMovies, watchedMoviesIds);
                            setNextDisabled();
                        }
                        loadingSpinner.hideAndShowParentViewAndEnableUserInput();
                        if (currentPageNumber == 1) {
                            setPreviousEnabled();
                        }
                    },
                    watchedMoviesIds
            );
        }
        if (currentPageNumber == 0) {
            setPreviousDisabled();
        }
    }

    private void updateListView(List<Movie> data, List<Integer> watchedMoviesIds) {
        moviesLv.setAdapter(new MoviesListViewAdaptor(this, data, watchedMoviesIds));
    }

    private void setNextEnabled() {
        nextIv.setImageResource(R.mipmap.ic_next_results_enabled);
        nextIv.setEnabled(true);
    }

    private void setNextDisabled() {
        nextIv.setImageResource(R.mipmap.ic_next_results_disabled);
        nextIv.setEnabled(false);
    }

    private void setPreviousEnabled() {
        previousIv.setImageResource(R.mipmap.ic_previous_results_enabled);
        previousIv.setEnabled(true);
    }

    private void setPreviousDisabled() {
        previousIv.setImageResource(R.mipmap.ic_previous_results_disabled);
        previousIv.setEnabled(false);
    }
}
