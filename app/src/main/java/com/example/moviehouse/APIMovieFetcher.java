package com.example.moviehouse;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.moviehouse.Interfaces.OnFetchCompleteListener;
import com.example.moviehouse.Model.Movie;
import com.example.moviehouse.Model.MovieList;
import com.example.moviehouse.Room.MovieRepository;
import com.example.moviehouse.Room.RoomMovie;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIMovieFetcher {

    private final Context context;
    private final OnFetchCompleteListener listener;


    public APIMovieFetcher(Context context, OnFetchCompleteListener listener) {
        this.context = context;
        this.listener = listener;
    }

    // this function makes the API call to fetch movies from remote
    public void fetchMovies(List<String[]> urls){
        boolean isInternetAvailable  = NetworkUtil.isInternetAvailable(context);
        if(isInternetAvailable){        // Checks for whether the Internet connection is available or not
            OkHttpClient client = new OkHttpClient();
            List<Pair> list = new ArrayList<>();    // List to store the movies in movielists and their category(now playing or trending)
            for(String[] url : urls){
                Request request = new Request.Builder()
                        .url(url[0])
                        .get()
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer "+Constants.APIKey)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(!response.isSuccessful()){
                            return;
                        }
                        String jsonResponse = response.body().string();
                        Gson gson = new Gson();
                        MovieList movieList = gson.fromJson(jsonResponse, MovieList.class);
                        list.add(new Pair(movieList, Integer.parseInt(url[1])));
                        System.out.println("---------------"+movieList.getResults().size());
                        if(list.size() == urls.size()) {
                            processMovies(list);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        System.out.println(e.getMessage());
                    }
                });
            }
        }
        else listener.onFetchComplete();    // Callback when there is no Internet connection

    }

    // This functions extract all the movies from List of movieList objects
    private void processMovies(List<Pair> movieList) {
        ExecutorService executorService  = Executors.newSingleThreadExecutor();
        List<RoomMovie> roomMovies = new ArrayList<>();
        executorService.execute(()->{
            try{
                for(Pair pair : movieList) {
                    MovieList movielist = pair.movieList;
                    for (int i = 0; i < movielist.getResults().size(); i++) {
                        Movie movie = movielist.getResults().get(i);
                        roomMovies.add(new RoomMovie(
                                movie.isAdult(),
                                movie.getId(),
                                movie.getOverview(),
                                movie.getPopularity(),
                                movie.getPoster_path(),
                                movie.getRelease_date(),
                                movie.getTitle(),
                                movie.getVote_count(),
                                pair.category
                        ));
                    }
                }
                saveMovies(roomMovies);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
    }

    // This function saves movies to the Room database
    private void saveMovies(List<RoomMovie> roomMovies){
        MovieRepository movieRepository = new MovieRepository(context);
        movieRepository.insertMovies(roomMovies, new MovieRepository.MovieSaveCallback() {
            @Override
            public void onSuccess() {
//                System.out.println("Inside Repo : "+(movieRepository.getNowPlaying().size() + movieRepository.getTrendingMovies().size()));
                listener.onFetchComplete(); // Callback for notifying successful insertion of movies in DB
            }
            @Override
            public void onFailure(Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

}
