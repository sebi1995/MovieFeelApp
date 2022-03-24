package com.example.zdroa.myapplication.models;

import com.example.zdroa.myapplication.models.moviesubmodels.BelongsToCollection;
import com.example.zdroa.myapplication.models.moviesubmodels.Genre;
import com.example.zdroa.myapplication.models.moviesubmodels.ProductionCompany;
import com.example.zdroa.myapplication.models.moviesubmodels.ProductionCountry;
import com.example.zdroa.myapplication.models.moviesubmodels.Result;
import com.example.zdroa.myapplication.models.moviesubmodels.SpokenLanguage;
import com.example.zdroa.myapplication.models.moviesubmodels.Video;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieModel {

    @SerializedName("adult")
    @Expose
    private Boolean adult;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("belongs_to_collection")
    @Expose
    private BelongsToCollection belongsToCollection;

    @SerializedName("budget")
    @Expose
    private Integer budget;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres = new ArrayList<>();

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("popularity")
    @Expose
    private Double popularity;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("production_companies")
    @Expose
    private List<ProductionCompany> productionCompanies = new ArrayList<>();

    @SerializedName("production_countries")
    @Expose
    private List<ProductionCountry> productionCountries = new ArrayList<>();

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("revenue")
    @Expose
    private Integer revenue;

    @SerializedName("runtime")
    @Expose
    private Integer runtime;

    @SerializedName("spoken_languages")
    @Expose
    private List<SpokenLanguage> spokenLanguages = new ArrayList<>();

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("tagline")
    @Expose
    private String tagline;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("video")
    @Expose
    private Boolean video;

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    @SerializedName("videos")
    @Expose
    private Video youtubeVideos;

    public MovieModel() {

    }

    public MovieModel(Integer id) {
        this.id = id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public MovieModel setAdult(Boolean adult) {
        this.adult = adult;
        return this;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public MovieModel setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public BelongsToCollection getBelongsToCollection() {
        return belongsToCollection;
    }

    public MovieModel setBelongsToCollection(BelongsToCollection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
        return this;
    }

    public Integer getBudget() {
        return budget;
    }

    public MovieModel setBudget(Integer budget) {
        this.budget = budget;
        return this;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public MovieModel setGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public String getHomepage() {
        return homepage;
    }

    public MovieModel setHomepage(String homepage) {
        this.homepage = homepage;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public MovieModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getImdbId() {
        return imdbId;
    }

    public MovieModel setImdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public MovieModel setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
        return this;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public MovieModel setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public MovieModel setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public Double getPopularity() {
        return popularity;
    }

    public MovieModel setPopularity(Double popularity) {
        this.popularity = popularity;
        return this;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public MovieModel setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public MovieModel setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
        return this;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public MovieModel setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public MovieModel setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public MovieModel setRevenue(Integer revenue) {
        this.revenue = revenue;
        return this;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public MovieModel setRuntime(Integer runtime) {
        this.runtime = runtime;
        return this;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public MovieModel setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MovieModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getTagline() {
        return tagline;
    }

    public MovieModel setTagline(String tagline) {
        this.tagline = tagline;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public Boolean getVideo() {
        return video;
    }

    public MovieModel setVideo(Boolean video) {
        this.video = video;
        return this;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public MovieModel setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public MovieModel setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public Video getYoutubeVideos() {
        return youtubeVideos;
    }

    public MovieModel setYoutubeVideos(Video results) {
        this.youtubeVideos = results;
        return this;
    }
}
