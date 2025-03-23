package com.example.moviehouse;

import com.example.moviehouse.Model.MovieList;

// This class contains movieList and the category
public class Pair{
    MovieList movieList;
    int category;
    Pair(MovieList movieList, int category){
        this.movieList = movieList;
        this.category = category;
    }
}
