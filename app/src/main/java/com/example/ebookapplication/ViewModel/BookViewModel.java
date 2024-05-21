package com.example.ebookapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;


import androidx.lifecycle.AndroidViewModel;

import com.example.ebookapplication.Database.AppRepo;
import com.example.ebookapplication.BookModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookViewModel extends AndroidViewModel {
    private static final String COLLECTION_NAME = "books";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    public void addBookFirebase(BookModel bookModel, OnCompleteListener<DocumentReference> onCompleteListener) {
        db.collection(COLLECTION_NAME).add(bookModel).addOnCompleteListener(onCompleteListener);
    }

    // Read all books
    public void getBooksFirebase(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);
    }

    // Update a book
    public void updateBookFirebase(String id, BookModel book, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).set(book).addOnCompleteListener(onCompleteListener);
    }

    // Delete a book
    public void deleteBookFirebase(String id, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).delete().addOnCompleteListener(onCompleteListener);
    }

    // Get books by author
    public void getBooksByIdFirebase(String bId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME)
                .whereEqualTo("bId", bId)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

}