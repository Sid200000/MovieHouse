package com.example.moviehouse;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.moviehouse.Model.Movie;
import com.example.moviehouse.Room.MovieDatabase;
import com.example.moviehouse.Room.MovieRepository;
import com.example.moviehouse.Room.RoomMovie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MovieDetails extends AppCompatActivity {
//    private ActivityMovieDetailsBinding binding;
    ImageView movieImage;
    TextView movieTitle;
    TextView movieDesc;
    TextView movieReleaseDate;
    TextView movieVoteCount;
    ImageView bookmarkMovie;

    MovieRepository movieRepository;

    ExecutorService executorService;

    RoomMovie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.movie_details_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+movie.getPoster_path()) // Replace with your image URL
                .placeholder(R.drawable.fauget) // Optional: Placeholder image while loading
                .error(R.drawable.fauget) // Optional: Error image if loading fails
                .into(movieImage);
//        binding.movieDetailsTitle.setText(movie.getTitle());
//        binding.movieDetailsDesc.setText(movie.getOverview());
//        binding.movieDetailsReleaseDate.setText(movie.getRelease_date());
//        binding.movieDetailsVoteCount.setText(movie.getVote_count());

        movieTitle.setText(movie.getTitle());
        movieDesc.setText("Synopsis : " + movie.getOverview());
        movieReleaseDate.setText("Release Year : " +movie.getRelease_date().substring(0,4));

        bookmarkMovie.setImageResource(R.drawable.bookmark_i);
        movieVoteCount.setText("Vote Count : "+movie.getVote_count());
        bookmarkMovie.setOnClickListener(v->{   // Button to toggle Bookmark of this movie
            movieRepository.toogleBookmark(movie.getId(), new MovieRepository.MovieSaveCallback() {
                @Override
                public void onSuccess() {
                    setBookMarkImage(movieRepository.getBookmarkStatus(movie.getId()));
                }

                @Override
                public void onFailure(Exception e) {

                }
            });

        });
    }

    private void setBookMarkImage(boolean status) { // Updates the ui after fetching bookmark status
        if(status) {
//            System.out.println("Status : "+status);
            runOnUiThread(() -> bookmarkMovie.setImageResource(R.drawable.bookmark_a));
        }
        else bookmarkMovie.setImageResource(R.drawable.bookmark_i);
    }

    void init(){
        executorService = Executors.newSingleThreadExecutor();
        movie = getIntent().getParcelableExtra("MOVIE");    // Gets the data of the clicked movie through Intent
        movieImage = findViewById(R.id.movie_details_image);
        movieTitle = findViewById(R.id.movie_details_title);
        movieDesc = findViewById(R.id.movie_details_desc);
        movieReleaseDate = findViewById(R.id.movie_details_release_date);
        movieVoteCount = findViewById(R.id.movie_details_vote_count);
        bookmarkMovie = findViewById(R.id.movie_details_bookmark_buton);
        executorService.execute(()->{
            movieRepository = new MovieRepository(this);
        });
        if(movieRepository != null)
            setBookMarkImage(movieRepository.getBookmarkStatus(movie.getId()));

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}