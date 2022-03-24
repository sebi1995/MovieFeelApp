package com.example.zdroa.myapplication.activities.main.movies;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.models.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesListViewAdaptor extends BaseAdapter {

    private final Context context;
    private final List<MovieModel> movies;


    public MoviesListViewAdaptor(Context context, List<MovieModel> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public MovieModel getItem(int position) {
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
        TextView tvTitle = view.findViewById(R.id.results_layout_tv_movie_name);
        TextView tvOverview = view.findViewById(R.id.results_layout_tv_overview);
        String link_end = movies.get(position).getPosterPath();
        if( link_end != null) {
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w185" + link_end)
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.place_holder_img))
                    .into(imageView);
        }
        String title = movies.get(position).getTitle();
        if (title != null ){
            tvTitle.setTextColor(Color.BLACK);
            tvTitle.setText(title);
            tvTitle.setTextColor(Color.RED);
        }
        String overview = movies.get(position).getOverview();
        if (overview != null){
            tvOverview.setText(overview.length() > 0 ? overview : "No available information");
        }
        return view;
    }
//            if (!watched.get(position)){
//            } else {
//            tvTitle.setText("WATCHED: " + /*sTitle*/ "toimplement");
//            }
}
