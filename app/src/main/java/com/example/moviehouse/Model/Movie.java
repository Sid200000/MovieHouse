package com.example.moviehouse.Model;

import androidx.annotation.NonNull;

import java.util.List;

import io.realm.kotlin.types.RealmObject;
import io.realm.kotlin.types.annotations.PrimaryKey;

public class Movie implements RealmObject {
    private boolean adult;
    private String backdrop_path;
    private List<Integer> genre_ids;
    @PrimaryKey
    private int id;     // Movie Id
    private String original_language;
    private String original_title;
    private String overview;    // Movie Description
    private double popularity;
    private String poster_path;     // Image Location of the Movie Poster
    private String release_date;
    private String title;       // Title of Movie
    private boolean video;
    private double vote_average;
    private int vote_count;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
