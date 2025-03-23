package com.example.moviehouse.Adapter;

import android.content.Context;
import android.content.Intent;
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

// Adapter for Search Page
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.searchRVAdapter> {
    Context ctx;
    List<RoomMovie> list;

    public SearchAdapter(Context ctx, List<RoomMovie> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchAdapter.searchRVAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_rv, parent, false);
        return new searchRVAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.searchRVAdapter holder, int position) {
        RoomMovie movie = list.get(position);
        Glide.with(ctx)
                .load("https://image.tmdb.org/t/p/w500"+movie.getPoster_path()) // Loads Image into target
                .placeholder(R.drawable.fauget)
                .error(R.drawable.fauget)
                .into(holder.movieImage);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieDate.setText(movie.getRelease_date().substring(0,4));
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(ctx, MovieDetails.class);     // Clicking on Item takes you to MovieDetails activity
            intent.putExtra("MOVIE", movie);
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class searchRVAdapter extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView movieTitle;
        TextView movieDate;
        public searchRVAdapter(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.searchrv_movie_image);
            movieTitle = itemView.findViewById(R.id.searchrv_title);
            movieDate = itemView.findViewById(R.id.searchrv_date);
        }
    }
}
