package com.example.moviehouse;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.moviehouse.Interfaces.OnFetchCompleteListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnFetchCompleteListener {
    TextView textView;
    MoviesRepository moviesRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.movie_main_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        moviesRepository = new MoviesRepository(this, this);
        fetchAllMovies();

    }

    private void fetchAllMovies() {
        List<String[]> allURLs = new ArrayList<>();
        for(int i=1;i<5;i++){
            String[] pair = new String[2];
            pair[0] = Constants.nowPlayingURL+i;
            pair[1] = Constants.CATEGORY_NOWPLAYING;
            allURLs.add(pair);
        }
        for(int i=1;i<5;i++){
            String[] pair = new String[2];
            pair[0] = Constants.trendingURL+i;
            pair[1] = Constants.CATEGORY_TRENDING;
            allURLs.add(pair);
        }
        System.out.println(allURLs);
        moviesRepository.fetchMovies(allURLs);
    }

    @Override
    public void onFetchComplete() {
        System.out.println("Completed");
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}