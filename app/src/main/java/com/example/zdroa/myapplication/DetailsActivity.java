package com.example.zdroa.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.aid.GsonHandler;
import com.example.zdroa.myapplication.models.MovieModelWithYoutubeLinks;
import com.example.zdroa.myapplication.models.moviesubmodels.Genre;
import com.example.zdroa.myapplication.models.moviesubmodels.ProductionCountry;
import com.example.zdroa.myapplication.models.moviesubmodels.SpokenLanguage;
import com.example.zdroa.myapplication.models.moviesubmodels.YoutubeVideos;
import com.example.zdroa.myapplication.session.SessionHandler;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    private static String movie_id;
    private static String poster;
    //    private static boolean watched = false;
    private static YoutubeVideos youtube_links;
    private TextView movieWatched;
    private TextView tvNoTrailersToShowMessage;
    private ImageView trailer1;
    private ImageView trailer2;

    private String userID;

    private static SessionHandler sessionHandler;

    private Intent intent;

    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        fa = this;

        intent = getIntent();

        movieWatched = findViewById(R.id.details_tv_favourite);
        movieWatched.setTextColor(Color.RED);

        tvNoTrailersToShowMessage = findViewById(R.id.details_tv_no_trailers);

        trailer1 = findViewById(R.id.details_iv_trailer_1);
        trailer2 = findViewById(R.id.details_iv_trailer_2);

        MovieModelWithYoutubeLinks movieModel = new GsonHandler<MovieModelWithYoutubeLinks>().stringToObject(intent.getStringExtra("movieModelWYTLinks"), MovieModelWithYoutubeLinks.class);


        poster = movieModel.getPosterPath();
        movie_id = movieModel.getId().toString();
        youtube_links = movieModel.getYoutubeVideos();


        ((TextView) findViewById(R.id.details_tv_movie_title)).setText(movieModel.getTitle());
        ((TextView) findViewById(R.id.details_tv_release_status)).setText("Release Status: " + movieModel.getStatus());
        ((TextView) findViewById(R.id.details_tv_release_date)).setText(movieModel.getReleaseDate());
        ((TextView) findViewById(R.id.details_tv_adult)).setText(movieModel.getAdult().toString());
        ((TextView) findViewById(R.id.details_tv_vote_average)).setText("Rating: " + movieModel.getVoteAverage() + "/" + "10");
        ((TextView) findViewById(R.id.details_tv_runtime)).setText("Runtime: " + movieModel.getRuntime() + " minutes");
        Locale loc = new Locale(movieModel.getOriginalLanguage());
        ((TextView) findViewById(R.id.details_tv_original_language)).setText("Original Language: " + loc.getDisplayLanguage(loc));
        ((TextView) findViewById(R.id.details_tv_overview)).setText(movieModel.getOverview());

        populateGenres(movieModel.getGenres());
        populateProductionCountries(movieModel.getProductionCountries());
        populateSpokenLanguages(movieModel.getSpokenLanguages());
        populateVideos(movieModel.getYoutubeVideos());

        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w300/" + movieModel.getPosterPath())
                .into((ImageView) findViewById(R.id.details_iv_poster));
    }

    private void populateSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        if (!spokenLanguages.isEmpty()) {
            TextView textView = findViewById(R.id.details_tv_spoken_languages);
            String name = spokenLanguages.get(0).getName();
            int size = spokenLanguages.size();
            if (size == 1) {
                textView.setText("Genre: " + name);
            } else if (size > 1) {
                textView.setText("Genres: " + name);
                for (int i = 1; i < size; i++) {
                    textView.append(", " + spokenLanguages.get(i).getName());
                }
            } else {
                textView.setText(R.string.INFORMATION_NOT_AVAILABLE);
            }
        }
    }

    private void populateProductionCountries(List<ProductionCountry> productionCountries) {
        if (!productionCountries.isEmpty()) {
            TextView textView = findViewById(R.id.details_tv_production_countries);
            String name = productionCountries.get(0).getName();
            int size = productionCountries.size();
            if (size == 1) {
                textView.setText("Production Country: " + name);
            } else if (size > 1) {
                textView.setText("Production Countries: " + name);
                for (int i = 1; i < size; i++) {
                    textView.append(", " + productionCountries.get(i).getName());
                }
            } else {
                textView.setText(R.string.INFORMATION_NOT_AVAILABLE);
            }
        }
    }

    private void populateGenres(List<Genre> genres) {
        if (!genres.isEmpty()) {
            TextView textView = findViewById(R.id.details_tv_genres);
            String name = genres.get(0).getName();
            int size = genres.size();
            if (size == 1) {
                textView.setText("Spoken Language: " + name);
            } else if (size > 1) {
                textView.setText("Spoken Languages: " + name);
                for (int i = 1; i < size; i++) {
                    textView.append(", " + genres.get(i).getName());
                }
            } else {
                textView.setText(R.string.INFORMATION_NOT_AVAILABLE);
            }
        }
    }

    private void populateVideos(YoutubeVideos youtubeVideos) {

        Space space = findViewById(R.id.details_space_between_trailers);

        boolean firstVideoIsThere = videoIsThere(youtubeVideos, 0);
        boolean secondVideoIsThere = videoIsThere(youtubeVideos, 1);

        if (firstVideoIsThere) {
            String key = youtubeVideos.getResults().get(0).getKey();
            trailer1.setOnClickListener(view -> {
                Uri uri = Uri.parse("http://www.youtube.com/watch?v=" + key);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browserIntent);
            });
            Picasso.with(this)
                    .load("http://img.youtube.com/vi/" + key + "/default.jpg")
                    .into(trailer1);
        } else {
            trailer1.setVisibility(View.GONE);
            space.setVisibility(View.GONE);
        }

        if (secondVideoIsThere) {
            String key = youtubeVideos.getResults().get(1).getKey();
            trailer2.setOnClickListener(view -> {
                Uri uri = Uri.parse("http://www.youtube.com/watch?v=" + key);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browserIntent);
            });
            Picasso.with(this)
                    .load("http://img.youtube.com/vi/" + key + "/default.jpg")
                    .into(trailer2);
        } else {
            trailer2.setVisibility(View.GONE);
            space.setVisibility(View.GONE);
        }

        if (!firstVideoIsThere && !secondVideoIsThere) {
            tvNoTrailersToShowMessage.setVisibility(View.VISIBLE);
            space.setVisibility(View.GONE);
        }

    }

    private boolean videoIsThere(YoutubeVideos youtubeVideos, int position) {
        if (youtubeVideos == null || youtubeVideos.getResults() == null || youtubeVideos.getResults().isEmpty()) {
            return false;
        }
        return position < youtubeVideos.getResults().size();
    }


}
