package com.example.ebookapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;


import androidx.lifecycle.AndroidViewModel;

import com.example.ebookapplication.Database.AppRepo;
import com.example.ebookapplication.BookModel;

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
    public BookModel getBook(int id) throws ExecutionException, InterruptedException {
        return appRepo.getBook(id);
    }

    public LiveData<List<BookModel>> getAllBooksLive() {
        return appRepo.getAllBooksLive();
    }

}
