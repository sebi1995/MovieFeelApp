package com.example.zdroa.myapplication.aid;

import java.util.Arrays;
import java.util.List;

public class MovieUtils {

    public static final int MAX_MOVIES_RESULTS_PER_PAGE = 20;
    public static final String API_KEY = "b692b9da86f1cf0c1b623ea6e2770101";
    public static final List<String> TITLES_ENTRIES_LIST = Arrays.asList("Title", "Mr", "Mrs", "Ms", "Miss", "Dr");

    public static String getMoviesRequestUrlWithVideos(int filmId) {
        return getMoviesRequestUrl(filmId) + "&append_to_response=videos";
    }

    public static String getMoviesRequestUrl(int filmId) {
        return "https://api.themoviedb.org/3/movie/" + filmId + "?api_key=" + API_KEY;
    }

    public static final Integer[] GOOD_IDS = {2, 3, 5, 6, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 24, 25, 26, 27, 28, 30, 31, 32, 33, 35, 38, 55, 58, 59, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98};

}
