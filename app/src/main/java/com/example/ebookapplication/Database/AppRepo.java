package com.example.ebookapplication.Database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.ebookapplication.BookModel;
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

//    private void addBookToFirebase(BookModel bookModel) {
//        String bId = db.collection("books").document().getId();
//        bookModel.bId = Integer.parseInt(bId);
//        db.collection("books").document(bId).set(bookModel)
//                .addOnSuccessListener(aVoid -> {
//                    Log.d("Firestore", "Book successfully added!");
//                })
//                .addOnFailureListener(e -> {
//                    Log.w("Firestore", "Error adding book", e);
//                });
//    }
//
//    private void updateBookToFirebase(BookModel bookModel) {
//        db.collection("books").document(book.getId()).set(book, SetOptions.merge())
//                .addOnSuccessListener(aVoid -> {
//                    Log.d("Firestore", "Book successfully updated!");
//                })
//                .addOnFailureListener(e -> {
//                    Log.w("Firestore", "Error updating book", e);
//                });
//    }
//
//    private void deleteBookToFirebase(BookModel bookModel) {
//        db.collection("books").document(bookId).delete()
//                .addOnSuccessListener(aVoid -> {
//                    Log.d("Firestore", "Book successfully deleted!");
//                })
//                .addOnFailureListener(e -> {
//                    Log.w("Firestore", "Error deleting book", e);
//                });
//    }
}
