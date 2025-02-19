package com.example.ebookapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ebookapplication.Dao.BookDao;
import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.Dao.PageDao;
import com.example.ebookapplication.PageModel;

@Database(entities = {BookModel.class, PageModel.class}, exportSchema = false, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "database.db";
    public static AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract BookDao bookDao();
    public abstract PageDao pageDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }

        return instance;
    }

}