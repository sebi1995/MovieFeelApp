package com.example.zdroa.myapplication.activities.main.movies;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.handlers.LoadingSpinner;
import com.example.zdroa.myapplication.handlers.UserSession;
import com.example.zdroa.myapplication.models.Movie;
import com.example.zdroa.myapplication.models.moviesubmodels.Genre;
import com.example.zdroa.myapplication.models.moviesubmodels.ProductionCountry;
import com.example.zdroa.myapplication.models.moviesubmodels.Result;
import com.example.zdroa.myapplication.models.moviesubmodels.SpokenLanguage;
import com.example.zdroa.myapplication.repositories.RestApiRepository;
import com.example.zdroa.myapplication.repositories.UserRepository;
import com.example.zdroa.myapplication.services.RestApiMovieService;
import com.example.zdroa.myapplication.services.UserService;
import com.example.zdroa.myapplication.utils.MovieLvAdaptor;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class MoviesActivity extends AppCompatActivity implements ActivityNavigator {

    private ListView moviesLv;
    private ImageView previousIv;
    private ImageView nextIv;
    private LoadingSpinner loadingSpinner;
    public UserSession userSession;
    private List<Integer> userWatchedMovies;

    public RestApiMovieService movieService;

    public UserService userService;

    MoviesViewModel moviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initViews();
        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);

        userSession = new UserSession(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        userWatchedMovies = userSession.getWatchedMoviesIds();
        userService = new UserService(UserRepository.getInstance(getApplicationContext()));
        movieService = new RestApiMovieService(RestApiRepository.getInstance(getApplicationContext()), new Logger(RestApiMovieService.class));
        loadingSpinner = new LoadingSpinner(this, this.getWindow(), moviesLv, findViewById(R.id.activity_movies));

        // TODO: 22/03/2022 get watched movies and somehow show if a movie is watched
        moviesLv.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = moviesViewModel.getCachedMovieForCurrentPageByPosition(position);
            MovieActivityNavigator.startMovieDetailsActivityWithParams(this, movie.getId(), movie.getTitle(), movie.getPosterPath(), movie.getAdult(), movie.getOverview(), movie.getRuntime(), movie.getStatus(), movie.getReleaseDate(), movie.getVoteAverage(), movie.getOriginalLanguage(),
                    movie.getGenres().stream().map(Genre::getName).collect(Collectors.toList()),
                    movie.getProductionCountries().stream().map(ProductionCountry::getName).collect(Collectors.toList()),
                    movie.getSpokenLanguages().stream().map(SpokenLanguage::getName).collect(Collectors.toList()),
                    movie.getYoutubeVideos().getVideos().stream().map(Result::getUrlEnd).collect(Collectors.toList()));
        });
        fetchAndLoadDataIntoUiForCurrentPage();
    }

    public void initViews() {
        this.moviesLv = findViewById(R.id.movies_lvMovies);
        this.nextIv = findViewById(R.id.movies_next_results_image_view);
        this.previousIv = findViewById(R.id.movies_previous_results_image_view);
    }

    public void moviesNextResultsOnClick(View view) {
        moviesViewModel.incrementPageNumber();
        fetchAndLoadDataIntoUiForCurrentPage();
    }

    public void moviesPreviousResultsOnClick(View view) {
        moviesViewModel.decrementPageNumber();
        fetchAndLoadDataIntoUiForCurrentPage();
    }

    private void fetchAndLoadDataIntoUiForCurrentPage() {
        loadingSpinner.showAndHideParentViewAndDisableUserInput();
        if (moviesViewModel.canProvideDataFromCache()) {
            if (moviesViewModel.isOnSecondPage()) {
                enablePrevious();
            }
            updateUiData(moviesViewModel.getSegmentedMoviesForCurrentPageNumber());
            loadingSpinner.hideAndShowParentViewAndEnableUserInput();
        } else {
            movieService.fetchStartingFromIdAndLimit(resultMovies -> {
                moviesViewModel.addToCachedMovies(resultMovies);
                if (moviesViewModel.lessCachedMoviesThanLimitPerPage()) {
                    updateUiData(moviesViewModel.getCachedMovies());
                    disableNext();
                } else {
                    updateUiData(moviesViewModel.getSegmentedMoviesForCurrentPageNumber());
                    enableNext();
                }
                loadingSpinner.hideAndShowParentViewAndEnableUserInput();
                if (moviesViewModel.isOnSecondPage()) {
                    enablePrevious();
                }
            }, moviesViewModel.isCachedMoviesListEmpty() ? 0 : moviesViewModel.getLastCachedMovie().getId());
        }
        disablePreviousIfOnFirstPage();
    }

    private void disablePreviousIfOnFirstPage() {
        if (moviesViewModel.isOnFirstPage()) {
            disablePrevious();
        }
    }

    private void updateUiData(List<Movie> data) {
        moviesLv.setAdapter(new MovieLvAdaptor(this, data, userWatchedMovies));
    }

    void enableNext() {
        nextIv.setImageResource(R.mipmap.ic_next_results_enabled);
        nextIv.setEnabled(true);
    }

    void disableNext() {
        nextIv.setImageResource(R.mipmap.ic_next_results_disabled);
        nextIv.setEnabled(false);
    }

    void enablePrevious() {
        previousIv.setImageResource(R.mipmap.ic_previous_results_enabled);
        previousIv.setEnabled(true);
    }

    void disablePrevious() {
        previousIv.setImageResource(R.mipmap.ic_previous_results_disabled);
        previousIv.setEnabled(false);
    }
}