package com.example.zdroa.myapplication.activities.main.movies;

import static com.example.zdroa.myapplication.utils.AppSettings.MOVIES_TO_DISPLAY_LIMIT_PER_PAGE;

import androidx.lifecycle.ViewModel;

import com.example.zdroa.myapplication.models.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MoviesViewModel extends ViewModel {

    private AtomicInteger currentPageNumber;
    private List<Movie> cachedMovies;

    public MoviesViewModel() {
        currentPageNumber = new AtomicInteger(0);
        cachedMovies = new ArrayList<>();
    }

    public int getCurrentPageNumber() {
        return currentPageNumber.get();
    }

    public int getStartIndex() {
        return getCurrentPageNumber() * MOVIES_TO_DISPLAY_LIMIT_PER_PAGE;
    }

    public int getEndIndex() {
        return getStartIndex() + MOVIES_TO_DISPLAY_LIMIT_PER_PAGE;
    }

    public boolean canProvideDataFromCache() {
        return getEndIndex() <= cachedMovies.size();
    }

    public List<Movie> getSegmentedMoviesForCurrentPageNumber() {
        return cachedMovies.subList(getStartIndex(), getEndIndex());
    }

    public void addToCachedMovies(List<Movie> movies) {
        cachedMovies.addAll(movies);
    }

    public List<Movie> getCachedMovies() {
        return cachedMovies;
    }

    public boolean isCachedMoviesListEmpty() {
        return cachedMovies.isEmpty();
    }

    public Movie getLastCachedMovie() {
        return cachedMovies.get(cachedMovies.size() - 1);
    }

    public boolean isOnFirstPage() {
        return getCurrentPageNumber() == 0;
    }

    public boolean isOnSecondPage() {
        return getCurrentPageNumber() == 1;
    }

    public void incrementPageNumber() {
        currentPageNumber.getAndIncrement();
    }

    public void decrementPageNumber() {
        currentPageNumber.getAndDecrement();
    }

    public boolean lessCachedMoviesThanLimitPerPage() {
        return cachedMovies.size() < MOVIES_TO_DISPLAY_LIMIT_PER_PAGE;
    }

    public Movie getCachedMovieForCurrentPageByPosition(int position) {
        return cachedMovies.get(getStartIndex() + position);
    }
}
