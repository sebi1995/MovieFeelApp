package com.example.zdroa.myapplication.utilities;

import static android.graphics.Color.RED;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class MoviesListViewAdaptor extends BaseAdapter {

    private final Context context;
    private final List<Movie> movies;
    private final List<Integer> watchedMoviesIds;


    public MoviesListViewAdaptor(Context context, List<Movie> movies, List<Integer> watchedMoviesIds) {
        this.context = context;
        this.movies = movies;
        this.watchedMoviesIds = watchedMoviesIds;
    }

    // TODO: 17/04/2022 check if this is needed?
    public MoviesListViewAdaptor(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
        this.watchedMoviesIds = Collections.emptyList();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = View.inflate(context, R.layout.results_layout, null);
        }

        ImageView imageView = view.findViewById(R.id.ivPosters);
        Movie movie = movies.get(position);
        String posterPathEndPart = movie.getPosterPath();

        if (posterPathEndPart != null) {
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w185" + posterPathEndPart)
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.place_holder_img))
                    .into(imageView);
        }

        if (watchedMoviesIds.contains(movie.getId())) {
            view.findViewById(R.id.results_layout_watched_movies_bar_view).setBackgroundColor(RED);
        }
        ((TextView) view.findViewById(R.id.results_layout_tv_movie_name)).setText(movie.getTitle());
        String overview = movie.getOverview();
        ((TextView) view.findViewById(R.id.results_layout_tv_overview)).setText(!TextUtils.isEmpty(overview) ? overview : "No available information");

        return view;
    }
}
