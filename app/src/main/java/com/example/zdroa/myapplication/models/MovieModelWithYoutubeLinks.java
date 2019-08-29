package com.example.zdroa.myapplication.models;

import com.example.zdroa.myapplication.models.moviesubmodels.BelongsToCollection;
import com.example.zdroa.myapplication.models.moviesubmodels.YoutubeVideos;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieModelWithYoutubeLinks extends MovieModel {

    @SerializedName("videos")
    @Expose
    private YoutubeVideos youtubeVideos;

    public YoutubeVideos getYoutubeVideos() {
        return youtubeVideos;
    }

    public void setYoutubeVideos(YoutubeVideos youtubeVideos) {
        this.youtubeVideos = youtubeVideos;
    }

}
