package com.example.moviehouse.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<RoomMovie> movies);

    @Query("SELECT * FROM movies")
    List<RoomMovie> getAllMovies();         //Query to get all the movies from the database


    @Query("DELETE FROM movies WHERE isBookmarked = 0")
    void clearMovies();                     // Query to delete non bookmarked movies

    @Query("SELECT * FROM movies WHERE catagory = 1")
    List<RoomMovie> getNowPlayingMovies();    // Query to fetch now playing movies

    @Query("SELECT * FROM movies WHERE catagory = 2")
    List<RoomMovie> getTrendingMovies();      // Query to fetch trending movies

    @Query("UPDATE movies SET isBookmarked = NOT isBookmarked WHERE id = :movieId")
    void toggleBookMark(int movieId);       // Query to toggle bookmark of a movie according to movie Id

    @Query("SELECT * FROM movies WHERE isBookmarked = 1")
    List<RoomMovie> getBookmarkedMovies();      // Query to fetch bookmarked movies

    @Query("SELECT isBookmarked FROM movies WHERE id = :movieId")
    boolean getBookmarkStatus(int movieId);    // Query to get bookmark status of a movie
}