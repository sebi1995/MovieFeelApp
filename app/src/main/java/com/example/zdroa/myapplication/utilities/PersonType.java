package com.example.zdroa.myapplication.utilities;

import static com.example.zdroa.myapplication.utils.MovieGenre.ACTION;
import static com.example.zdroa.myapplication.utils.MovieGenre.ADVENTURE;
import static com.example.zdroa.myapplication.utils.MovieGenre.ANIMATION;
import static com.example.zdroa.myapplication.utils.MovieGenre.COMEDY;
import static com.example.zdroa.myapplication.utils.MovieGenre.CRIME;
import static com.example.zdroa.myapplication.utils.MovieGenre.DRAMA;
import static com.example.zdroa.myapplication.utils.MovieGenre.FANTASY;
import static com.example.zdroa.myapplication.utils.MovieGenre.HORROR;
import static com.example.zdroa.myapplication.utils.MovieGenre.MYSTERY;
import static com.example.zdroa.myapplication.utils.MovieGenre.ROMANCE;
import static com.example.zdroa.myapplication.utils.MovieGenre.SCIENCE_FICTION;
import static com.example.zdroa.myapplication.utils.MovieGenre.THRILLER;

import com.example.zdroa.myapplication.utils.MovieGenre;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PersonType {

    ANXIOUS(1, CRIME, DRAMA, ROMANCE, THRILLER),
    PARANOID(2, CRIME, HORROR, MYSTERY, SCIENCE_FICTION, THRILLER),
    HISTRIONIC(3, ADVENTURE),
    OBSESSIVE(4, ADVENTURE),
    NARCISSIST(5, HORROR, THRILLER),
    SCHIZOID(6, CRIME, DRAMA, FANTASY, HORROR, SCIENCE_FICTION),
    DEPRESSIVE(7, ACTION, ADVENTURE, ANIMATION, COMEDY, FANTASY, ROMANCE),
    DEPENDANT(8, CRIME, DRAMA, ROMANCE, THRILLER);

    private final MovieGenre[] genresToAvoid;
    private final Integer index;

    PersonType(Integer index, MovieGenre... genresToAvoid) {
        this.index = index;
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

    public static PersonType getByIndex(int index) {
        for (PersonType personType : values()) {
            if (personType.getIndex() == index) {
                return personType;
            }
        }
        return null;
    }

    public List<MovieGenre> getGenresToAvoid() {
        return Arrays.asList(genresToAvoid);
    }

    public Integer getIndex() {
        return index;
    }
}