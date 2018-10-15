package com.example.zdroa.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

class ListViewAdaptor extends BaseAdapter {

    private Context mContext;
    private HashMap<Integer, String> urls, titles, movie_overview;
    private HashMap<Integer, Boolean> watched;


    ListViewAdaptor(Context context,
                    HashMap<Integer, String> URLS,
                    HashMap<Integer, String> TITLES,
                    HashMap<Integer, String> OVERVIEW,
                    HashMap<Integer, Boolean> watchedList) {

        mContext = context;
        urls = URLS;
        titles = TITLES;
        movie_overview = OVERVIEW;
        watched = watchedList;
    }

    @Override
    public int getCount() {
        return urls.size();
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
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivPosters);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.results_layout_tv_movie_name);
        TextView tvOverview = (TextView) convertView.findViewById(R.id.results_layout_tv_overview);

        String link_end = urls.get(position);
        String sTitle = titles.get(position);
        String sOverview = movie_overview.get(position);
        if (sOverview != null && sTitle != null && link_end != null) {

            Drawable d = mContext.getResources().getDrawable(R.drawable.place_holder_img);
            Picasso.with(mContext)
                    .load("http://image.tmdb.org/t/p/w185" + link_end)
                    .placeholder(d)
                    .into(imageView);

            tvTitle.setTextColor(Color.BLACK);
            if (!watched.get(position)){
                tvTitle.setText(sTitle);
            } else {
                tvTitle.setText("WATCHED: " + sTitle);
                tvTitle.setTextColor(Color.RED);
            }


            if (sOverview.length() == 0) {
                tvOverview.setText("No available information");
            } else tvOverview.setText(sOverview);
        }
        return convertView;

    }
}
