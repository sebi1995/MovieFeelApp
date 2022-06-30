package com.example.zdroa.myapplication.activities.main.movies;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class WatchedMoviesActivity extends AppCompatActivity implements ActivityNavigator {

    private ListView moviesLv;
    private ImageView previousIv;
    private ImageView nextIv;
    private TextView pageTitleTv;
    private LoadingSpinner loadingSpinner;

    public UserSession userSession;

    public RestApiMovieService restApiMovieService;

    public UserService userService;

    List<Integer> userWatchedMoviesIds;

    MoviesViewModel moviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initViews();

        moviesViewModel = new MoviesViewModel();
        userSession = new UserSession(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        userService = new UserService(UserRepository.getInstance(getApplicationContext()));
        restApiMovieService = new RestApiMovieService(RestApiRepository.getInstance(getApplicationContext()), new Logger(RestApiMovieService.class));
        // TODO: 24/04/2022 check this
        loadingSpinner = new LoadingSpinner(this, this.getWindow(), moviesLv, findViewById(R.id.activity_movies));
        pageTitleTv.setText("Watched Movies");
        userWatchedMoviesIds = userSession.getWatchedMoviesIds();

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
        moviesLv = findViewById(R.id.movies_lvMovies);
        nextIv = findViewById(R.id.movies_next_results_image_view);
        previousIv = findViewById(R.id.movies_previous_results_image_view);
        pageTitleTv = findViewById(R.id.movies_page_title_text_view);
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
            restApiMovieService.fetchMoviesByIds(
                    result -> {
                        moviesViewModel.addToCachedMovies(result);
                        if (moviesViewModel.lessCachedMoviesThanLimitPerPage()) {
                            updateUiData(moviesViewModel.getCachedMovies());
                            disableNext();
                        } else {
                            updateUiData(moviesViewModel.getSegmentedMoviesForCurrentPageNumber());
                            enableNext();
                        }
                        if (moviesViewModel.isOnSecondPage()) {
                            enablePrevious();
                        }
                        loadingSpinner.hideAndShowParentViewAndEnableUserInput();
                    },
                    userWatchedMoviesIds);
            disablePreviousIfOnFirstPage();
        }
    }

    private void disablePreviousIfOnFirstPage() {
        if (moviesViewModel.isOnFirstPage()) {
            disablePrevious();
        }
    }

    private void updateUiData(List<Movie> data) {
        moviesLv.setAdapter(new MovieLvAdaptor(this, data));
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
