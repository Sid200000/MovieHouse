package com.example.moviehouse.Room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RoomMovie.class}, version = 3, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static volatile MovieDatabase INSTANCE;

    public abstract MovieDAO movieDao();

    public static MovieDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MovieDatabase.class, "movie_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
