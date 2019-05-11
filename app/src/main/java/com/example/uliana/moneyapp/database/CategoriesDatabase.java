package com.example.uliana.moneyapp.database;

import android.content.Context;
import android.util.Log;

import com.example.uliana.moneyapp.model.Categories;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Categories.class, exportSchema = false, version = 2)
public abstract class CategoriesDatabase extends RoomDatabase {
    private static final String LOG_TAG = CategoriesDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "categorieslist";
    private static CategoriesDatabase cInstance;

    public static CategoriesDatabase getInstance(Context context) {
        if (cInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                cInstance = Room.databaseBuilder(context.getApplicationContext(),
                        CategoriesDatabase.class, CategoriesDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return cInstance;
    }

    public abstract CategoryDao categoryDao();
}
