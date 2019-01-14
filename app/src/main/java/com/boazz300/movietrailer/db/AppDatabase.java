package com.boazz300.movietrailer.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.boazz300.movietrailer.model.MovieModel;
import com.boazz300.movietrailer.model.VideoModel;

@Database(entities = {MovieModel.class, VideoModel.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "movies";

    static AppDatabase INSTANCE;

    public abstract MovieDao movieDao();

    public abstract VideoDao videoDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}
