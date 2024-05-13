package com.example.ebookapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;


import androidx.lifecycle.AndroidViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookViewModel extends AndroidViewModel {

    private AppRepo appRepo;

    public BookViewModel(@NonNull Application application) {
        super(application);
        appRepo = new AppRepo(application);
    }

    public void insertBook(BookModel bookModel) {
        appRepo.insertBook(bookModel);
    }

    public void updateBook(BookModel bookModel) {
        appRepo.updateBook(bookModel);
    }

    public void deleteBook(BookModel bookModel) {
        appRepo.deleteBook(bookModel);
    }

    public List<BookModel> getAllBooksFuture() throws ExecutionException, InterruptedException {
        return appRepo.getAllBooksFuture();
    }

    public LiveData<List<BookModel>> getAllBooksLive() {
        return appRepo.getAllBooksLive();
    }

}
