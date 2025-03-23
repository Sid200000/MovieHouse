package com.example.moviehouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviehouse.Adapter.MoviesRVAdapter;
import com.example.moviehouse.Adapter.SearchAdapter;
import com.example.moviehouse.Room.MovieDAO;
import com.example.moviehouse.Room.MovieDatabase;
import com.example.moviehouse.Room.MovieRepository;
import com.example.moviehouse.Room.RoomMovie;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SavedMovies extends AppCompatActivity {
    RecyclerView savedMoviesRV;
    ExecutorService executorService;
    CardView homeButton;
    CardView searchButton;
    List<RoomMovie> bookMarkedMovies;

    SearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saved_movies);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();

        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            MovieRepository movieRepository = new MovieRepository(this);
            bookMarkedMovies = movieRepository.getBookMarkedMovies();
            System.out.println("BookMarked Movies : "+bookMarkedMovies.size());
            adapter = new SearchAdapter( this, bookMarkedMovies);
            savedMoviesRV.setAdapter(adapter);
        });

        homeButton.setOnClickListener(v->{
            startActivity(new Intent(this, HomeActivity.class));
        });
        searchButton.setOnClickListener(v->{
            startActivity(new Intent(this, SearchPage.class));
        });
    }

    private void initViews() {
        homeButton = findViewById(R.id.home_page_button);
        searchButton = findViewById(R.id.search_page_button);
        savedMoviesRV = findViewById(R.id.save_movies_rv);
        savedMoviesRV.hasFixedSize();
        savedMoviesRV.setLayoutManager(new LinearLayoutManager(this));
    }


}