package com.example.zdroa.myapplication.utils;

public enum MovieGenre {

    ACTION("Action"),
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    COMEDY("Comedy"),
    FANTASY("Fantasy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    ROMANCE("Romance"),
    THRILLER("Thriller"),
    HORROR("Horror"),
    SCIENCE_FICTION("Science Fiction"),
    MYSTERY("Mystery");

    private final String type;

    MovieGenre(String type) {
        this.type = type;
    }

    public MovieGenre get(String type) {
        if (type == null) {
            return null;
        }
        for (MovieGenre value : values()) {
            if (value.type.toLowerCase().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
