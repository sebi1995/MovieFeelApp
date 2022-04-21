package com.example.zdroa.myapplication.utilities;

import static com.example.zdroa.myapplication.utilities.MovieGenre.ACTION;
import static com.example.zdroa.myapplication.utilities.MovieGenre.ADVENTURE;
import static com.example.zdroa.myapplication.utilities.MovieGenre.ANIMATION;
import static com.example.zdroa.myapplication.utilities.MovieGenre.COMEDY;
import static com.example.zdroa.myapplication.utilities.MovieGenre.CRIME;
import static com.example.zdroa.myapplication.utilities.MovieGenre.DRAMA;
import static com.example.zdroa.myapplication.utilities.MovieGenre.FANTASY;
import static com.example.zdroa.myapplication.utilities.MovieGenre.HORROR;
import static com.example.zdroa.myapplication.utilities.MovieGenre.MYSTERY;
import static com.example.zdroa.myapplication.utilities.MovieGenre.ROMANCE;
import static com.example.zdroa.myapplication.utilities.MovieGenre.SCIENCE_FICTION;
import static com.example.zdroa.myapplication.utilities.MovieGenre.THRILLER;

import java.util.Arrays;
import java.util.List;

public enum PersonType {

    DEPENDANT(CRIME, DRAMA, ROMANCE, THRILLER),
    DEPRESSIVE(ACTION, ADVENTURE, ANIMATION, COMEDY, FANTASY, ROMANCE),
    SCHIZOID(CRIME, DRAMA, FANTASY, HORROR, SCIENCE_FICTION),
    NARCISSIST(HORROR, THRILLER),
    OBSESSIVE(ADVENTURE),
    HISTRIONIC(ADVENTURE),
    PARANOID(CRIME, HORROR, MYSTERY, SCIENCE_FICTION, THRILLER),
    ANXIOUS(CRIME, DRAMA, ROMANCE, THRILLER);

    private final MovieGenre[] genresToAvoid;

    PersonType(MovieGenre... genresToAvoid) {
        this.genresToAvoid = genresToAvoid;
    }

    public static PersonType getType(String type) {
        for (PersonType personType : PersonType.values()) {
            if (personType.toString().equals(type)) {
                return personType;
            }
        }
        return null;
    }

    public List<MovieGenre> getGenresToAvoid() {
        return Arrays.asList(genresToAvoid);
    }

}