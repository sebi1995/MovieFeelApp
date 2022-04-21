package com.example.zdroa.myapplication.activities.main.movies;

import static com.example.zdroa.myapplication.utils.AppSettings.MAX_MOVIES_PER_PAGE;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.BasicActivity;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.handlers.LoadingSpinner;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;
import com.example.zdroa.myapplication.models.Movie;
import com.example.zdroa.myapplication.models.moviesubmodels.Genre;
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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MoviesActivity extends AppCompatActivity implements BasicActivity, ActivityNavigator {

    private ListView moviesLv;
    private ImageView previousIv;
    private ImageView nextIv;
    private LoadingSpinner loadingSpinner;
    public UserSessionHandler userSessionHandler;
    private List<Integer> userWatchedMovies;

    public RestApiMovieService restApiMovieService;
    public UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initViews();

        List<Movie> cachedMovies = new ArrayList<>();

        userSessionHandler = new UserSessionHandler(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        redirectIfSessionDoesNotMeetRequirements(userSessionHandler, this);
        userWatchedMovies = new ArrayList<>();
        userWatchedMovies.addAll(userSessionHandler.getWatchedMoviesIds());
        userService = new UserService(new UserRepository(getApplicationContext()));
        restApiMovieService = new RestApiMovieService(new RestApiRepository(getApplicationContext()), new Logger(RestApiMovieService.class));
        loadingSpinner = new LoadingSpinner(this, this.getWindow(), moviesLv, findViewById(R.id.activity_movies), true);

        // TODO: 22/03/2022 get watched movies and somehow show if a movie is watched
        AtomicInteger currentPage = new AtomicInteger(0);
        moviesLv.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = cachedMovies.get(position + (currentPage.get() * MAX_MOVIES_PER_PAGE));
            MovieActivityNavigator.startMovieDetailsActivityWithParams(this, movie.getId(), movie.getTitle(), movie.getPosterPath(), movie.getAdult(), movie.getOverview(), movie.getRuntime(), movie.getStatus(), movie.getReleaseDate(), movie.getVoteAverage(), movie.getOriginalLanguage(),
                    movie.getGenres().stream().map(Genre::getName).collect(Collectors.toList()),
                    movie.getProductionCountries().stream().map(ProductionCountry::getName).collect(Collectors.toList()),
                    movie.getSpokenLanguages().stream().map(SpokenLanguage::getName).collect(Collectors.toList()),
                    movie.getYoutubeVideos().getVideos().stream().map(Result::getUrlEnd).collect(Collectors.toList()));
        });

        previousIv.setOnClickListener(view -> loadDataIntoListView(cachedMovies, currentPage.decrementAndGet()));
        nextIv.setOnClickListener(view -> loadDataIntoListView(cachedMovies, currentPage.incrementAndGet()));

        loadDataIntoListView(cachedMovies, currentPage.get());
    }

    @Override
    public void initViews() {
        this.moviesLv = findViewById(R.id.movies_lvMovies);
        this.nextIv = findViewById(R.id.movies_next_results_image_view);
        this.previousIv = findViewById(R.id.movies_previous_results_image_view);
    }

    private void loadDataIntoListView(List<Movie> cachedMovies, int currentPageNumber) {
        loadingSpinner.showAndHideParentViewAndDisableUserInput();
        int startIndex = currentPageNumber * MAX_MOVIES_PER_PAGE;
        int endIndex = startIndex + MAX_MOVIES_PER_PAGE;
        boolean moreCachedMoviesThanRequested = endIndex <= cachedMovies.size();
        if (moreCachedMoviesThanRequested) {
            updateListView(cachedMovies.subList(startIndex, endIndex));
            loadingSpinner.hideAndShowParentViewAndEnableUserInput();
            enablePreviousIvIfNotFirstPage(currentPageNumber);
        } else {
            restApiMovieService.fetchMoviesStartingWithIdAndLimit(
                    resultMovies -> {
                        cachedMovies.addAll(resultMovies);

                        if (cachedMovies.size() < MAX_MOVIES_PER_PAGE) {
                            updateListViewWithLessThanMaxMoviesAndDisableNextButton(cachedMovies);
                        } else {
                            updateListViewWithSegmentedNumberOfMoviesAndEnableNextButton(cachedMovies, startIndex, endIndex);
                        }
                        loadingSpinner.hideAndShowParentViewAndEnableUserInput();
                        enablePreviousIvIfNotFirstPage(currentPageNumber);
                    },
                    cachedMovies.isEmpty() ? 1 : cachedMovies.get(cachedMovies.size() - 1).getId(),
                    MAX_MOVIES_PER_PAGE
            );
        }
        disablePreviousIvIfFirstPage(currentPageNumber);
    }

    private void updateListViewWithSegmentedNumberOfMoviesAndEnableNextButton(List<Movie> cachedMovies, int startIndex, int endIndex) {
        updateListView(cachedMovies.subList(startIndex, endIndex));
        setNextIvEnabled();
    }

    private void updateListViewWithLessThanMaxMoviesAndDisableNextButton(List<Movie> cachedMovies) {
        updateListView(cachedMovies);
        setNextIvDisabled();
    }

    private void disablePreviousIvIfFirstPage(int currentPageNumber) {
        if (currentPageNumber == 0) {
            setPreviousIvDisabled();
        }
    }

    private void enablePreviousIvIfNotFirstPage(int currentPageNumber) {
        if (currentPageNumber == 1) {
            setPreviousIvEnabled();
        }
    }

    private void updateListView(List<Movie> data) {
        moviesLv.setAdapter(new MoviesListViewAdaptor(this, data, Collections.emptyList()));
    }

    private void setNextIvEnabled() {
        nextIv.setImageResource(R.mipmap.ic_next_results_enabled);
        nextIv.setEnabled(true);
    }

    private void setNextIvDisabled() {
        nextIv.setImageResource(R.mipmap.ic_next_results_disabled);
        nextIv.setEnabled(false);
    }

    private void setPreviousIvEnabled() {
        previousIv.setImageResource(R.mipmap.ic_previous_results_enabled);
        previousIv.setEnabled(true);
    }

    private void setPreviousIvDisabled() {
        previousIv.setImageResource(R.mipmap.ic_previous_results_disabled);
        previousIv.setEnabled(false);
    }
}
