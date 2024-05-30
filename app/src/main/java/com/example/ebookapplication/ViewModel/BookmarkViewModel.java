package com.example.ebookapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.CategoryModel;
import com.example.ebookapplication.PageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class BookmarkViewModel extends AndroidViewModel {
    private static final String COLLECTION_NAME = "bookmarks";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    public BookmarkViewModel(@NonNull Application application) {
        super(application);
    }


    public void addBookmarkFirebase(BookmarkModel bookmarkModel, OnCompleteListener<Void> onCompleteListener) {
        String id = db.collection(COLLECTION_NAME).document().getId(); // Generate a new ID
        bookmarkModel.bmFirebaseId = id;
        db.collection(COLLECTION_NAME).document(id).set(bookmarkModel).addOnCompleteListener(onCompleteListener);
    }


    // Read all bookmarks
    public void getBookmarksFirebase(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);
    }


    // Update a bookmarkModel
    public void updateBookmarkFirebase(String id, BookmarkModel bookmarkModel, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).set(bookmarkModel).addOnCompleteListener(onCompleteListener);
    }

    // Delete a bookmarkModel
    public void deleteBookmarkFirebase(String id, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).delete().addOnCompleteListener(onCompleteListener);
    }

}