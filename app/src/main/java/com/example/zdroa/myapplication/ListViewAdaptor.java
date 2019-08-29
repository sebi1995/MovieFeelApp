package com.example.zdroa.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zdroa.myapplication.models.MovieModelWithYoutubeLinks;
import com.squareup.picasso.Picasso;

import java.util.List;

class ListViewAdaptor extends BaseAdapter {

    private final List<MovieModelWithYoutubeLinks> movies;
    private Context mContext;
    private String posterLinkEnding;
    private String movieTitle;
    private String movieOverview;

    ListViewAdaptor(Context context, List<MovieModelWithYoutubeLinks> movies) {
        mContext = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.results_layout, null);
        }

        ImageView imageView = convertView.findViewById(R.id.ivPosters);
        TextView tvTitle = convertView.findViewById(R.id.results_layout_tv_movie_name);
        TextView tvOverview = convertView.findViewById(R.id.results_layout_tv_overview);

        MovieModelWithYoutubeLinks movieFromList = movies.get(position);
        if (movieFromList == null) {
            return convertView;
        }


        if (aNullParam(movieFromList)) return convertView;

        Drawable drawable = mContext.getResources().getDrawable(R.drawable.place_holder_img);
        Picasso.with(mContext)
                .load("http://image.tmdb.org/t/p/w185" + posterLinkEnding)
                .placeholder(drawable)
                .into(imageView);

        tvTitle.setTextColor(Color.BLACK);
        tvTitle.setText(movieTitle);

        if (movieOverview.length() == 0) {
            tvOverview.setText("No available information");
        } else {
            tvOverview.setText(movieOverview);
        }

        return convertView;

    }

    private boolean aNullParam(MovieModelWithYoutubeLinks movieFromList) {
        this.posterLinkEnding = movieFromList.getPosterPath();
        this.movieTitle = movieFromList.getTitle();
        this.movieOverview = movieFromList.getOverview();
        return this.posterLinkEnding == null &&
                this.movieTitle == null &&
                this.movieOverview == null;
    }
}
