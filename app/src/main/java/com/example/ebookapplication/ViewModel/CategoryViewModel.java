package com.example.ebookapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ebookapplication.BookRatingModel;
import com.example.ebookapplication.CategoryModel;
import com.example.ebookapplication.PageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CategoryViewModel extends AndroidViewModel {
    private static final String COLLECTION_NAME = "categories";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    public CategoryViewModel(@NonNull Application application) {
        super(application);
    }


    public void addCategoryFirebase(CategoryModel categoryModel, OnCompleteListener<Void> onCompleteListener) {
        String id = db.collection(COLLECTION_NAME).document().getId(); // Generate a new ID
        categoryModel.cFirebaseId = id;
        db.collection(COLLECTION_NAME).document(id).set(categoryModel).addOnCompleteListener(onCompleteListener);
    }


    // Read all pages
    public void getCategoriesFirebase(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);
    }


    // Update a book
    public void updateCategoriesFirebase(String id, PageModel pageModel, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).set(pageModel).addOnCompleteListener(onCompleteListener);
    }

    // Delete a book
    public void deleteCategoriesFirebase(String id, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).delete().addOnCompleteListener(onCompleteListener);
    }

}