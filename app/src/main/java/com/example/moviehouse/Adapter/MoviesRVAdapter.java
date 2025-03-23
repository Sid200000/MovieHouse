package com.example.moviehouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviehouse.MovieDetails;
import com.example.moviehouse.R;
import com.example.moviehouse.Room.RoomMovie;

import java.util.List;

public class MoviesRVAdapter extends RecyclerView.Adapter<MoviesRVAdapter.moviesRVAdapter> {

    List<RoomMovie> movieList;
    Context ctx;
    public MoviesRVAdapter(Context ctx, List<RoomMovie> nowPlaying){
        this.ctx = ctx;
        this.movieList = nowPlaying;
        System.out.println("MovieAdapter : "+nowPlaying.size());
    }
    @NonNull
    @Override
    public MoviesRVAdapter.moviesRVAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_rv, parent, false);
        return new moviesRVAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesRVAdapter.moviesRVAdapter holder, int position) {
        RoomMovie movie = movieList.get(position);
        Glide.with(ctx)
                .load("https://image.tmdb.org/t/p/w500"+movie.getPoster_path()) // Replace with your image URL
                .placeholder(R.drawable.fauget) // Optional: Placeholder image while loading
                .error(R.drawable.fauget) // Optional: Error image if loading fails
                .into(holder.movieImage);
        holder.movieName.setText(movie.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, MovieDetails.class);
                intent.putExtra("MOVIE", movie);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class moviesRVAdapter extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView movieName;
        public moviesRVAdapter(@NonNull View itemView) {
            super(itemView);
            this.movieImage = itemView.findViewById(R.id.movie_image_holder);
            this.movieName = itemView.findViewById(R.id.movie_name);
        }
    }
}
