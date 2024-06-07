package com.example.ebookapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ebookapplication.CategoryModel;
import com.example.ebookapplication.UserCategoryModel;
import com.example.ebookapplication.Utils.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserCategoryViewModel extends AndroidViewModel {
    private static final String COLLECTION_NAME = "userCategories";
    private final FirebaseFirestore db = FirebaseHelper.getInstance().getDb();


    public UserCategoryViewModel(@NonNull Application application) {
        super(application);
    }


    public void getUserCategoryByIdFirebase(String userId, String categoryId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME)
                .whereEqualTo("userId", userId)
                .whereEqualTo("categoryId", categoryId)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }
    public void addUserCategoryFirebase(UserCategoryModel userCategoryModel, OnCompleteListener<Void> onCompleteListener) {
        String id = db.collection(COLLECTION_NAME).document().getId(); // Generate a new ID
        userCategoryModel.ucFirebaseId = id;
        db.collection(COLLECTION_NAME).document(id).set(userCategoryModel).addOnCompleteListener(onCompleteListener);
    }

    public void updateUserCategoryFirebase(String ucFirebaseId, UserCategoryModel userCategoryModel, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(ucFirebaseId).set(userCategoryModel).addOnCompleteListener(onCompleteListener);
    }
    public void deleteUserCategoryFirebase(String ucFirebaseId, OnCompleteListener<Void> onCompleteListener) {
        db.collection(COLLECTION_NAME).document(ucFirebaseId).delete().addOnCompleteListener(onCompleteListener);
    }
}
