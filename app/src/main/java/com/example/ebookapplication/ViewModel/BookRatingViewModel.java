package com.example.ebookapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ebookapplication.BookRatingModel;
import com.example.ebookapplication.Database.AppRepo;
import com.example.ebookapplication.PageModel;
import com.example.ebookapplication.Utils.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookRatingViewModel extends AndroidViewModel {
    private static final String COLLECTION_NAME = "bookRatings";
    private final FirebaseFirestore db = FirebaseHelper.getInstance().getDb();


    public BookRatingViewModel(@NonNull Application application) {
        super(application);
    }


    public void addBookRatingFirebase(BookRatingModel bookRatingModel, OnCompleteListener<Void> onCompleteListener) {
        String id = db.collection(COLLECTION_NAME).document().getId(); // Generate a new ID
        bookRatingModel.rFirebaseId = id;
        db.collection(COLLECTION_NAME).document(id).set(bookRatingModel).addOnCompleteListener(onCompleteListener);
    }

    // Read all pages from book
    public void getBookRatingsByBookIdFirebase(String bId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME).whereEqualTo("bookId", bId).get().addOnCompleteListener(onCompleteListener);
    }

    // Update a book
    public void updateBookRatingFirebase(String id, PageModel pageModel, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).set(pageModel).addOnCompleteListener(onCompleteListener);
    }

    // Delete a book
    public void deleteBookRatingFirebase(String id, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).delete().addOnCompleteListener(onCompleteListener);
    }

}
