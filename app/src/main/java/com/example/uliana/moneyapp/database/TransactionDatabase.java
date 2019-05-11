package com.example.uliana.moneyapp.database;

import android.content.Context;
import android.util.Log;

import com.example.uliana.moneyapp.model.Transaction;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Transaction.class, exportSchema = false, version = 2)
public abstract class TransactionDatabase extends RoomDatabase {
    private static final String LOG_TAG = TransactionDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "transactionlist";
    private static TransactionDatabase sInstance;

    public static TransactionDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        TransactionDatabase.class, TransactionDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract TransactionDao transactionDao();



}
