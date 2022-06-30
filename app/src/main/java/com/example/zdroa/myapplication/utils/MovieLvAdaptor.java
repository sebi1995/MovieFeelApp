package com.example.zdroa.myapplication.utils;

import static android.graphics.Color.BLACK;
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

public class MovieLvAdaptor extends BaseAdapter {

    private final Context context;
    private final List<Movie> movies;
    private final List<Integer> watchedMoviesIds;


    public MovieLvAdaptor(Context context, List<Movie> movies, List<Integer> watchedMoviesIds) {
        this.context = context;
        this.movies = movies;
        this.watchedMoviesIds = watchedMoviesIds;
    }

    // TODO: 17/04/2022 check if this is needed?
    public MovieLvAdaptor(Context context, List<Movie> movies) {
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
        TextView movieTitleTv = view.findViewById(R.id.results_layout_tv_movie_name);
        View watchedStatusView = view.findViewById(R.id.results_layout_watched_movies_bar_view);
        ImageView posterIv = view.findViewById(R.id.ivPosters);
        TextView overViewTv = view.findViewById(R.id.results_layout_tv_overview);

        Movie movie = movies.get(position);

        String posterPathEndPart = movie.getPosterPath();

        if (posterPathEndPart != null) {
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w185" + posterPathEndPart)
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.place_holder_img))
                    .into(posterIv);
        }

        movieTitleTv.setText(movie.getTitle());

        if (watchedMoviesIds.contains(movie.getId())) {
            movieTitleTv.setTextColor(RED);
            watchedStatusView.setBackgroundColor(RED);
        } else {
            movieTitleTv.setTextColor(ContextCompat.getColor(view.getContext(), R.color.appBgColor));
        }

        String overview = movie.getOverview();
        overViewTv.setText(!TextUtils.isEmpty(overview) ? overview : "No available information");

        return view;
    }
}
