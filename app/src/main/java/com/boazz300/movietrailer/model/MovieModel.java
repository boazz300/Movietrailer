package com.boazz300.movietrailer.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

@Entity
public class MovieModel implements Parcelable {

    @PrimaryKey
    private int movieId;
    private String name;
    private String imageUri;
    private String backImageUri;
    private String overview;
    private String releaseDate;
    private Double popularity;
//    private String trailerUrl;

    public MovieModel() {   }

    protected MovieModel(Parcel in) {
        movieId = in.readInt();
        name = in.readString();
        imageUri = in.readString();
        backImageUri = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        popularity = in.readDouble();
    }

    public MovieModel(int movieId, String name, String imageUri, String backImageUri, String overview, String releaseDate, Double popularity) {
        this.movieId = movieId;
        this.name = name;
        this.imageUri = imageUri;
        this.backImageUri = backImageUri;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageRes() {
        return imageUri;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getBackImageUri() {
        return backImageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setBackImageUri(String backImageUri) {
        this.backImageUri = backImageUri;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "name='" + name + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", overview=" + (TextUtils.isEmpty(overview) ? "Empty" : overview.length()) +
                ", imageRes=" + (imageUri == null ? "Nun" : "OK") +
                ", backImageRes=" + (backImageUri == null ? "Nun" : "OK") +
                '}';
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(movieId);
        parcel.writeString(name);
        parcel.writeString(imageUri);
        parcel.writeString(backImageUri);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeDouble(popularity);
    }
}
