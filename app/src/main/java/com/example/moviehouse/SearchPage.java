package com.example.moviehouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviehouse.Adapter.SearchAdapter;
import com.example.moviehouse.Room.MovieDAO;
import com.example.moviehouse.Room.MovieDatabase;
import com.example.moviehouse.Room.RoomMovie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchPage extends AppCompatActivity {
    EditText searchBar;
    RecyclerView searchRecyclerView;
    CardView homeButton;
    CardView bookmarkButton;

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private SearchAdapter searchAdapter;
    private List<RoomMovie> movieList = new ArrayList<>();
    private final Handler handler = new Handler();
    private Runnable searchRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();

        executor.execute(()->{
            MovieDatabase movieDatabase = MovieDatabase.getInstance(this);
            movieList = movieDatabase.movieDao().getAllMovies();
            searchAdapter = new SearchAdapter(this, movieList);
            searchRecyclerView.hasFixedSize();
            searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            searchRecyclerView.setAdapter(searchAdapter);
        });




        homeButton.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
        });
        bookmarkButton.setOnClickListener(v->{
            startActivity(new Intent(this, SavedMovies.class));
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(searchRunnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(searchRunnable);
                searchRunnable = () -> fetchMovies(s.toString());
                handler.postDelayed(searchRunnable, 500);
            }
        });
    }

    private void fetchMovies(String query) {

        List<RoomMovie> tempList = new ArrayList<>();

        for(RoomMovie movie : movieList){
            if(movie.getTitle().contains(query))
                    tempList.add(movie);
        }
        searchAdapter = new SearchAdapter(this, tempList);
        searchRecyclerView.setAdapter(searchAdapter);
    }

    private void initViews() {
        searchBar = findViewById(R.id.search_bar);
        searchRecyclerView = findViewById(R.id.search_recycler_view);
        homeButton = findViewById(R.id.home_page_button);
        bookmarkButton = findViewById(R.id.bookmark_page_button);
    }

}