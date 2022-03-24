package com.example.zdroa.myapplication.models.moviesubmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;

public class Result {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;

    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;

    @SerializedName("key")
    @Expose
    private String urlEnd;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("site")
    @Expose
    private String site;

    @SerializedName("size")
    @Expose
    private Integer size;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("official")
    @Expose
    private Boolean official;

    @SerializedName("published_at")
    @Expose
    private OffsetDateTime publishedAt;

    public Boolean getOfficial() {
        return official;
    }

    public OffsetDateTime getPublishedAt() {
        return publishedAt;
    }

    public Result setPublishedAt(OffsetDateTime publishedAt) {
        this.publishedAt = publishedAt;
        return this;
    }

    public Result setOfficial(Boolean official) {
        this.official = official;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getUrlEnd() {
        return urlEnd;
    }

    public void setUrlEnd(String urlEnd) {
        this.urlEnd = urlEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}