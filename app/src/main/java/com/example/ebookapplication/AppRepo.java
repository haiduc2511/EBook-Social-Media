package com.example.ebookapplication;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppRepo {

    private AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public AppRepo(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }


    public void insertBook(BookModel bookModel) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.bookDao().insertBook(bookModel);
            }
        });

    }

    public void updateBook(BookModel bookModel) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.bookDao().updateBook(bookModel);
            }
        });
    }

    public void deleteBook(BookModel bookModel) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.bookDao().deleteBook(bookModel);
            }
        });
    }

    public List<BookModel> getAllBooksFuture() throws ExecutionException, InterruptedException {

        Callable<List<BookModel>> callable = new Callable<List<BookModel>>() {
            @Override
            public List<BookModel> call() throws Exception {
                return appDatabase.bookDao().getAllBooksFuture();
            }
        };

        Future<List<BookModel>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();


    }

    public LiveData<List<BookModel>> getAllBooksLive()  {

        return appDatabase.bookDao().getAllBooksLive();


    }
}
