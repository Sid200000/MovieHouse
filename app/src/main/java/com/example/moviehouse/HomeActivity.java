package com.example.moviehouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviehouse.Adapter.MoviesRVAdapter;
import com.example.moviehouse.Room.MovieDatabase;
import com.example.moviehouse.Room.RoomMovie;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    MoviesRVAdapter nowPlayingMovieAdapter;
    MoviesRVAdapter trendingMovieAdapter;
    RecyclerView nowPlayingRV;
    RecyclerView trendingRV;

    CardView search_page_buttton;
    CardView bookmark_page_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.movie_home_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        search_page_buttton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchPage.class);
            startActivity(intent);
        });

        bookmark_page_button.setOnClickListener(v->{
            Intent intent = new Intent(this, SavedMovies.class);
            startActivity(intent);
        });

        trendingRV.hasFixedSize();
        trendingRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        nowPlayingRV.hasFixedSize();
        nowPlayingRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Handler uiHandler = new Handler(Looper.getMainLooper());
        new Thread(()->{
            MovieDatabase db = MovieDatabase.getInstance(this);
            List<RoomMovie> nowPlayingMovies = db.movieDao().getNowPlayingMovies();
            List<RoomMovie> trendingMovies = db.movieDao().getTrendingMovies();
            System.out.println("movieListSize : "+nowPlayingMovies.size());
            nowPlayingMovieAdapter = new MoviesRVAdapter(this, nowPlayingMovies);
            trendingMovieAdapter = new MoviesRVAdapter(this, trendingMovies);
            uiHandler.post(() ->{
                nowPlayingRV.setAdapter(nowPlayingMovieAdapter);
                trendingRV.setAdapter(trendingMovieAdapter);
            });

        }).start();

    }

    private void initViews() {
        nowPlayingRV = findViewById(R.id.nowplaying_rv);
        trendingRV = findViewById(R.id.trending_rv);
        search_page_buttton = findViewById(R.id.search_page_button);
        bookmark_page_button = findViewById(R.id.bookmark_page_button);
    }

}