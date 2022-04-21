package com.example.zdroa.myapplication.services;

import com.example.zdroa.myapplication.models.Movie;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface MovieService {

    default Optional<Movie> convertToMovie(String jsonResponse) {
        return Optional.of(new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, (JsonDeserializer<OffsetDateTime>)
                        (json, type, context) -> OffsetDateTime.parse(json.getAsString()))
                .create()
                .fromJson(jsonResponse, Movie.class));
    }

    void fetchMoviesStartingWithIdAndLimit(HttpRequestRunner.Callback<List<Movie>> onResponseListener, int startingMovieId, int limit);

    void fetchMoviesByIds(HttpRequestRunner.Callback<List<Movie>> onResponse, List<Integer> userWatchedMoviesIds);
}
