package com.example.ebookapplication.Database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.PageModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppRepo {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    public BookModel getBook(int id) throws ExecutionException, InterruptedException {
        Callable<BookModel> callable = new Callable<BookModel>() {
            @Override
            public BookModel call() throws Exception {
                return appDatabase.bookDao().getBook(id);
            }
        };

        Future<BookModel> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void insertPage(PageModel pageModel) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.pageDao().insertPage(pageModel);
            }
        });

    }

    public void updatePage(PageModel pageModel) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.pageDao().updatePage(pageModel);
            }
        });
    }

    public void deletePage(PageModel pageModel) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.pageDao().deletePage(pageModel);
            }
        });
    }

    public List<PageModel> getAllPagesFuture() throws ExecutionException, InterruptedException {

        Callable<List<PageModel>> callable = new Callable<List<PageModel>>() {
            @Override
            public List<PageModel> call() throws Exception {
                return appDatabase.pageDao().getAllPagesFuture();
            }
        };

        Future<List<PageModel>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();


    }
    public List<PageModel> getAllPagesFromBookIdFuture() throws ExecutionException, InterruptedException {

        Callable<List<PageModel>> callable = new Callable<List<PageModel>>() {
            @Override
            public List<PageModel> call() throws Exception {
                return appDatabase.pageDao().getAllPagesFuture();
            }
        };

        Future<List<PageModel>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();


    }
}
