package com.example.zdroa.myapplication.models.moviesubmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Video {

    @SerializedName("results")
    @Expose
    private List<Result> videos = new ArrayList<>();

    public List<Result> getVideos() {
        return videos;
    }

    public Video setVideos(List<Result> results) {
        this.videos = results;
        return this;
    }
}