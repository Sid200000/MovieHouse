package com.example.moviehouse.Room;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MovieRepository {



    public interface MovieSaveCallback {
        void onSuccess();
        void onFailure(Exception e);
    }
    private MovieDAO movieDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MovieRepository(Context context) {
        MovieDatabase db = MovieDatabase.getInstance(context);
        movieDao = db.movieDao();
    }

    public void insertMovies(List<RoomMovie> movies, MovieSaveCallback callback) {
        executorService.execute(() -> {
            try {
                movieDao.clearMovies();
                movieDao.insertMovies(movies); // Insert into RoomDB
                callback.onSuccess();  // ✅ Notify success
            } catch (Exception e) {
                callback.onFailure(e); // ❌ Notify failure
            }
        });
    }

    public List<RoomMovie> getAllMovies() {
        return movieDao.getAllMovies();
    }

    public List<RoomMovie> getNowPlaying(){
        return movieDao.getNowPlayingMovies();
    }

    public List<RoomMovie> getTrendingMovies(){
        return movieDao.getTrendingMovies();
    }

    public void toogleBookmark(int id, MovieSaveCallback callback) {
        executorService.execute(() -> {
            try {
                movieDao.toggleBookMark(id);// ToggleBookMark
                callback.onSuccess();  // ✅ Notify success
            } catch (Exception e) {
                callback.onFailure(e); // ❌ Notify failure
            }
        });
    }

    public List<RoomMovie> getBookMarkedMovies(){
        return movieDao.getBookmarkedMovies();
    }
    public boolean getBookmarkStatus(int id){
       return movieDao.getBookmarkStatus(id);
    }
}
