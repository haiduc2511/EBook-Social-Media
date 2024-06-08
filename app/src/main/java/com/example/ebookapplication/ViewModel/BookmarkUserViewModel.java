package com.example.ebookapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.BookmarkUserModel;
import com.example.ebookapplication.UserModel;
import com.example.ebookapplication.Utils.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class BookmarkUserViewModel extends AndroidViewModel {
    private static final String COLLECTION_NAME = "bookmarkUsers";
    private final FirebaseFirestore db = FirebaseHelper.getInstance().getDb();


    public BookmarkUserViewModel(@NonNull Application application) {
        super(application);
    }


    public void addBookmarkUserFirebase(BookmarkUserModel bookmarkUserModel, OnCompleteListener<Void> onCompleteListener) {
        String id = db.collection(COLLECTION_NAME).document().getId(); // Generate a new ID
        bookmarkUserModel.buFirebaseId = id;
        db.collection(COLLECTION_NAME).document(id).set(bookmarkUserModel).addOnCompleteListener(onCompleteListener);
    }


    // Read all bookmarks
    public void getBookmarkUsersFirebase(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);
    }


    // Update a bookmarkModel
    public void updateBookmarkUserFirebase(String id, BookmarkModel bookmarkModel, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).set(bookmarkModel).addOnCompleteListener(onCompleteListener);
    }

    // Delete a bookmarkModel
    public void deleteBookmarkUserFirebase(String id, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).delete().addOnCompleteListener(onCompleteListener);
    }

}