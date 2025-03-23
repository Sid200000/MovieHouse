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
    List<RoomMovie> getAllMovies();


    @Query("DELETE FROM movies WHERE isBookmarked = 0")
    void clearMovies();

    @Query("SELECT * FROM movies WHERE catagory = 1")
    List<RoomMovie> getNowPlayingMovies();

    @Query("SELECT * FROM movies WHERE catagory = 2")
    List<RoomMovie> getTrendingMovies();

    @Query("UPDATE movies SET isBookmarked = NOT isBookmarked WHERE id = :movieId")
    void toggleBookMark(int movieId);

    @Query("SELECT * FROM movies WHERE isBookmarked = 1")
    List<RoomMovie> getBookmarkedMovies();

    @Query("SELECT isBookmarked FROM movies WHERE id = :movieId")
    boolean getBookmarkStatus(int movieId);
}