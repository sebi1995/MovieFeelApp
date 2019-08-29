package com.example.zdroa.myapplication.models;

/**
 * Created by zdroa on 18/05/2017.
 */

public enum RESTRICTED_GENRES_ENUM {

    ANXIOUS_AVOID_GENRES(new String[]{"Crime", "Drama", "Romance", "Thriller"}),
    PARANOID_AVOID_GENRES(new String[]{"Crime", "Horror", "Mystery", "Science Fiction", "Thriller"}),
    HISTRIONIC_AVOID_GENRES(new String[]{"Adventure"}),
    OBSESSIVE_AVOID_GENRES(new String[]{"Adventure"}),
    NARCISSIST_AVOID_GENRES(new String[]{"Horror", "Thriller"}),
    SCHIZOID_AVOID_GENRES(new String[]{"Crime", "Drama", "Fantasy", "Horror", "Science Fiction"}),
    DEPRESSIVE_AVOID_GENRES(new String[]{"Action", "Adventure", "Animation", "Comedy", "Fantasy", "Romance"}),
    DEPENDENT_AVOID_GENRES(new String[]{"Crime", "Drama", "Romance", "Thriller"});

    private String[] strings;

    RESTRICTED_GENRES_ENUM(String[] strings) {
        this.strings = strings;
    }

    public String[] getStrings() {
        return strings;
    }

}
