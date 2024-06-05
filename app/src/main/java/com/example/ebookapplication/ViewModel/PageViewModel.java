package com.example.ebookapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.Database.AppRepo;
import com.example.ebookapplication.NumberUtilsForDuc;
import com.example.ebookapplication.PageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PageViewModel extends AndroidViewModel {
    private static final String COLLECTION_NAME = "pages";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private AppRepo appRepo;

    public PageViewModel(@NonNull Application application) {
        super(application);
        appRepo = new AppRepo(application);
    }

    public void insertPage(PageModel pageModel) {
        appRepo.insertPage(pageModel);
    }

    public void updatePage(PageModel pageModel) {
        appRepo.updatePage(pageModel);
    }

    public void deletePage(PageModel pageModel) {
        appRepo.deletePage(pageModel);
    }

    public List<PageModel> getAllPagesFuture() throws ExecutionException, InterruptedException {
        return appRepo.getAllPagesFuture();
    }
    public List<PageModel> getAllPagesFromBookIdFuture() throws ExecutionException, InterruptedException {
        return appRepo.getAllPagesFromBookIdFuture();
    }

    public void addPageFirebase(int numberOfPages, PageModel pageModel, OnCompleteListener<Void> onCompleteListener) {
        String id = NumberUtilsForDuc.toStringFourDigits(numberOfPages) + db.collection("pages").document().getId(); // Generate a new ID
        pageModel.pFirebaseId = id;
        db.collection(COLLECTION_NAME).document(id).set(pageModel).addOnCompleteListener(onCompleteListener);
    }

    // Read all pages from book
    public void getPagesByBookIdFirebase(String bId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME).whereEqualTo("bookId", bId).get().addOnCompleteListener(onCompleteListener);
    }

    // Update a book
    public void updatePageFirebase(String id, PageModel pageModel, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).set(pageModel).addOnCompleteListener(onCompleteListener);
    }

    // Delete a book
    public void deletePageFirebase(String id, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).delete().addOnCompleteListener(onCompleteListener);
    }

}