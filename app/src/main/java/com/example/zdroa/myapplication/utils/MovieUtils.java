package com.example.zdroa.myapplication.utils;

public class MovieUtils {

    public static String getMoviesRequestUrlWithYoutubeVideos(int filmId) {
        return getMoviesRequestUrl(filmId) + "&append_to_response=videos";
    }

    public static String getMoviesRequestUrl(int filmId) {
        return "https://api.themoviedb.org/3/movie/" + filmId + "?api_key=" + AppSettings.API_KEY;
    }

    public static String getMovieYoutubeThumbnailImgUrl(String movieYoutubeThumbnailUrlPart) {
        return "http://img.youtube.com/vi/" + movieYoutubeThumbnailUrlPart + "/default.jpg";
    }

}
