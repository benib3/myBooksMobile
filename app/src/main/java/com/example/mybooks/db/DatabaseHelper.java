package com.example.mybooks.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Books.class},version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    public abstract BooksDao booksDao();

    private static DatabaseHelper INSTANCE;
    public static DatabaseHelper getDbInstance(Context context){

        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, "DB_BOOK")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }

}
