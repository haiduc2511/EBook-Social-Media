package com.example.ebookapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.NoteModel;
import com.example.ebookapplication.Utils.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class NoteViewModel extends AndroidViewModel {
    private static final String COLLECTION_NAME = "notes";
    private final FirebaseFirestore db = FirebaseHelper.getInstance().getDb();


    public NoteViewModel(@NonNull Application application) {
        super(application);
    }


    public void addNoteFirebase(NoteModel noteModel, OnCompleteListener<Void> onCompleteListener) {
        String id = db.collection(COLLECTION_NAME).document().getId(); // Generate a new ID
        noteModel.nFirebaseId = id;
        db.collection(COLLECTION_NAME).document(id).set(noteModel).addOnCompleteListener(onCompleteListener);
    }


    // Read all notes
    public void getNotesFirebase(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);
    }

    // Read all notes from bookmark
    public void getNotesByBookmarkIdFirebase(String bmId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME).whereEqualTo("bmFirebaseId", bmId).get().addOnCompleteListener(onCompleteListener);
    }

    public void getNotesByBookmarkIdAndPageIdFirebase(String bmId, String pId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME)
                .whereEqualTo("bookmarkId", bmId)
                .whereEqualTo("pageId", pId)
                .get().addOnCompleteListener(onCompleteListener);
    }

    // Update a bookmarkModel
    public void updateNoteFirebase(String id, NoteModel noteModel, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).set(noteModel).addOnCompleteListener(onCompleteListener);
    }

    // Delete a bookmarkModel
    public void deleteNoteFirebase(String id, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).delete().addOnCompleteListener(onCompleteListener);
    }

}