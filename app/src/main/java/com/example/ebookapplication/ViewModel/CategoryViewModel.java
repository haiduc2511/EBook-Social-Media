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


    public void getCategoryByIdFirebase(String cFirebaseId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME)
                .whereEqualTo("cFirebaseId", cFirebaseId)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }
    public void addCategoryFirebase(CategoryModel categoryModel, OnCompleteListener<Void> onCompleteListener) {
        String id = db.collection(COLLECTION_NAME).document().getId(); // Generate a new ID
        categoryModel.cFirebaseId = id;
        db.collection(COLLECTION_NAME).document(id).set(categoryModel).addOnCompleteListener(onCompleteListener);
    }


    // Read all categories
    public void getCategoriesFirebase(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME).get().addOnCompleteListener(onCompleteListener);
    }


    // Update a category
    public void updateCategoryFirebase(String id, CategoryModel categoryModel, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).set(categoryModel).addOnCompleteListener(onCompleteListener);
    }

    // Delete a category
    public void deleteCategoryFirebase(String id, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(id).delete().addOnCompleteListener(onCompleteListener);
    }

}