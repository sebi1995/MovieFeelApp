package com.example.zdroa.myapplication.activities.main.movies;

import android.app.Activity;
import android.content.Intent;

import com.example.zdroa.myapplication.activities.main.movies.details.MovieDetailsActivity;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;
import com.example.zdroa.myapplication.utils.MovieUtils;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class MovieActivityNavigator {

    public static final String MOVIE_ID = "id";
    public static final String TITLE = "title";
    public static final String POSTER_PATH = "poster_path";
    public static final String IS_ADULT = "is_adult";
    public static final String OVERVIEW = "overview";
    public static final String RUNTIME = "runtime";
    public static final String RELEASE_STATUS = "release_status";
    public static final String RELEASE_DATE = "release_date";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String ORIGINAL_LANGUAGE = "original_language";
    public static final String GENRES = "genres";
    public static final String PRODUCTION_COUNTRIES = "production_countries";
    public static final String SPOKEN_LANGUAGES = "spoken_languages";
    public static final String YOUTUBE_LINKS = "youtube_links";
    public static final String DEFAULT_NO_VALUE = "this is for when there is no specific beautifier available for the given value";

    private static final Logger LOGGER = new Logger(MovieActivityNavigator.class);

    public static void startMovieDetailsActivityWithParams(Activity initiatingActivity, Integer id, String title, String posterPath, Boolean isAdult, String overview, Integer runtime, String releaseStatus, String releaseDate, Double voteAverage, String originalLanguage, List<String> genres, List<String> productionCountries, List<String> spokenLanguages, List<String> youtubeLinks) {
        Intent intent = new Intent(initiatingActivity, MovieDetailsActivity.class)
                .putExtra(MOVIE_ID, id)
                .putExtra(TITLE, title)
                .putExtra(POSTER_PATH, posterPath)
                .putExtra(IS_ADULT, isAdult)
                .putExtra(OVERVIEW, overview)
                .putExtra(RUNTIME, runtime)
                .putExtra(RELEASE_STATUS, releaseStatus)
                .putExtra(RELEASE_DATE, releaseDate)
                .putExtra(VOTE_AVERAGE, voteAverage)
                .putExtra(ORIGINAL_LANGUAGE, originalLanguage)
                .putStringArrayListExtra(GENRES, (ArrayList<String>) genres)
                .putStringArrayListExtra(PRODUCTION_COUNTRIES, (ArrayList<String>) productionCountries)
                .putStringArrayListExtra(SPOKEN_LANGUAGES, (ArrayList<String>) spokenLanguages)
                .putStringArrayListExtra(YOUTUBE_LINKS, (ArrayList<String>) youtubeLinks);
        initiatingActivity.startActivity(intent);
    }

    private static final ImmutableMap<String, Function<Intent, Object>> BEAUTIFIER_MAP = ImmutableMap.<String, Function<Intent, Object>>builder()
            .put(IS_ADULT, intent -> intent.getBooleanExtra(IS_ADULT, false))
            .put(VOTE_AVERAGE, intent -> intent.getDoubleExtra(VOTE_AVERAGE, -1))
            .put(RUNTIME, intent -> intent.getIntExtra(RUNTIME, -1))
            .put(GENRES, intent -> intent.getStringArrayListExtra(GENRES))
            .put(PRODUCTION_COUNTRIES, intent -> intent.getStringArrayListExtra(PRODUCTION_COUNTRIES))
            .put(SPOKEN_LANGUAGES, intent -> intent.getStringArrayListExtra(SPOKEN_LANGUAGES))
            .put(YOUTUBE_LINKS, intent -> intent.getStringArrayListExtra(YOUTUBE_LINKS))
            .build();

    public static String extractBeautifiedValue(String valueName, Intent intent) {
        Function<Intent, Object> fn = BEAUTIFIER_MAP.get(valueName);
        return MovieDetailsValueBeautifier.getBeautifier(valueName)
                .beautify(fn == null ? intent.getStringExtra(valueName) : fn.apply(intent));
    }

    public static Integer extractMovieId(Intent intent) {
        return intent.getIntExtra(MOVIE_ID, -1);
    }

    private enum MovieDetailsValueBeautifier {
        TITLE(MovieActivityNavigator.TITLE) {
            @Override
            public String beautify(Object objectToBeautify) {
                if (!(objectToBeautify instanceof String)) {
                    return AppSettings.NO_INFO_AVAILABLE;
                }
                return (String) objectToBeautify;
            }
        },
        RUNTIME(MovieActivityNavigator.RUNTIME) {
            @Override
            public String beautify(Object objectToBeautify) {
                if (!(objectToBeautify instanceof Integer)) {
                    return AppSettings.NO_INFO_AVAILABLE;
                }
                Integer value = ((Integer) objectToBeautify);
                return appendDot(value + " minutes");
            }
        },
        ADULT(MovieActivityNavigator.IS_ADULT) {
            @Override
            public String beautify(Object objectToBeautify) {
                if (!(objectToBeautify instanceof Boolean)) {
                    return AppSettings.NO_INFO_AVAILABLE;
                }
                Boolean value = (Boolean) objectToBeautify;
                return appendDot(value ? "Yes" : "No");
            }
        },
        VOTE_AVERAGE(MovieActivityNavigator.VOTE_AVERAGE) {
            @Override
            public String beautify(Object objectToBeautify) {
                if (!(objectToBeautify instanceof Double)) {
                    return AppSettings.NO_INFO_AVAILABLE;
                }
                Double value = ((Double) objectToBeautify);
                return appendDot(value + "/10");
            }
        },
        ORIGINAL_LANGUAGE(MovieActivityNavigator.ORIGINAL_LANGUAGE) {
            @Override
            public String beautify(Object objectToBeautify) {
                if (!(objectToBeautify instanceof String)) {
                    return AppSettings.NO_INFO_AVAILABLE;
                }
                String value = (String) objectToBeautify;
                Locale loc = new Locale(value);
                return appendDot(loc.getDisplayLanguage(loc));
            }
        },
        RELEASE_DATE(MovieActivityNavigator.RELEASE_DATE) {
            @Override
            public String beautify(Object objectToBeautify) {
                if (!(objectToBeautify instanceof String)) {
                    return AppSettings.NO_INFO_AVAILABLE;
                }
                String value = (String) objectToBeautify;
                String day = value.substring(value.length() - 2);
                String month = value.substring(value.length() - 5, value.length() - 3);
                String year = value.substring(0, 4);
                return appendDot(day + "/" + month + "/" + year);
            }
        },
        GENRES(MovieActivityNavigator.GENRES) {
            @Override
            public String beautify(Object objectToBeautify) {
                if (!(objectToBeautify instanceof List) || ((List<?>) objectToBeautify).isEmpty()) {
                    LOGGER.logError("erroqwdkpqwokdpkd");
                    return AppSettings.NO_INFO_AVAILABLE;
                }
                return MovieUtils.convertToMultiLineString((List<String>) objectToBeautify);
            }
        },
        DEFAULT(MovieActivityNavigator.DEFAULT_NO_VALUE) {
            @Override
            public String beautify(Object objectToBeautify) {
                if (objectToBeautify == null) {
                    return AppSettings.NO_INFO_AVAILABLE;
                }
                return appendDot((String) objectToBeautify);
            }
        };


        private String value;

        MovieDetailsValueBeautifier(String value) {
            this.value = value;
        }

        public abstract String beautify(Object objectToBeautify);

        public static MovieDetailsValueBeautifier getBeautifier(String value) {
            for (MovieDetailsValueBeautifier converter : values()) {
                if (converter.value.equals(value)) {
                    return converter;
                }
            }
            return DEFAULT;
        }

        public String appendDot(String string) {
            return string + ".";
        }
    }
}

