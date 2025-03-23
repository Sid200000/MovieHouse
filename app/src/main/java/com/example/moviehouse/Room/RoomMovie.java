package com.example.moviehouse.Room;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "movies")
public class RoomMovie implements Parcelable{
    private boolean adult;
    @PrimaryKey
    private int id;
    private String overview;
    private double popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private int vote_count;

    private int catagory;

    private boolean isBookmarked;

    public RoomMovie(boolean adult, int id, String overview, double popularity, String poster_path, String release_date, String title, int vote_count, int catagory) {
        this.adult = adult;
        this.id = id;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.title = title;
        this.vote_count = vote_count;
        this.catagory = catagory;
        this.isBookmarked = false;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getCatagory() {
        return catagory;
    }

    public void setCatagory(int catagory) {
        this.catagory = catagory;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    protected RoomMovie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        vote_count = (int) in.readDouble();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isBookmarked = in.readBoolean();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeDouble(vote_count);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(isBookmarked);
        }
    }



    public static final Parcelable.Creator<RoomMovie> CREATOR = new Parcelable.Creator<RoomMovie>() {
        @Override
        public RoomMovie createFromParcel(Parcel in) {
            return new RoomMovie(in);
        }

        @Override
        public RoomMovie[] newArray(int size) {
            return new RoomMovie[size];
        }
    };
}
