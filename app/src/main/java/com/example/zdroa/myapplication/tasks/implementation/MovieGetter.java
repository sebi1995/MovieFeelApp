package com.example.zdroa.myapplication.tasks.implementation;

import com.example.zdroa.myapplication.aid.HttpHandler;
import com.example.zdroa.myapplication.models.MovieModel;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.MovieUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MovieGetter implements Callable<List<MovieModel>> {

    private final Integer lastFetchedMovieId;

    public MovieGetter(Integer lastFetchedMovieId) {
        this.lastFetchedMovieId = lastFetchedMovieId;
    }

    @Override
    public List<MovieModel> call() {
        List<MovieModel> movies = new ArrayList<>();
        try {
            int id = lastFetchedMovieId + 1;
            while (movies.size() != AppSettings.MOVIES_PER_PAGE_LIMIT) {
                Thread.sleep(100);
                String jsonString = HttpHandler.makeServiceCall(MovieUtils.getMoviesRequestUrlWithYoutubeVideos(id));
                id += 1;
                if (jsonString != null) {
                    movies.add(new GsonBuilder()
                            .registerTypeAdapter(OffsetDateTime.class, (JsonDeserializer<OffsetDateTime>)
                                    (json, type, context) -> OffsetDateTime.parse(json.getAsString()))
                            .create()
                            .fromJson(jsonString, MovieModel.class));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return movies;
    }

}
