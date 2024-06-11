package com.example.ebookapplication.Activities;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.CategoryModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.UserCategoryModel;
import com.example.ebookapplication.UserModel;
import com.example.ebookapplication.Utils.StringUtilsForDuc;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.ViewModel.CategoryViewModel;
import com.example.ebookapplication.ViewModel.UserCategoryViewModel;
import com.example.ebookapplication.databinding.ActivityUserInsightBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class UserInsightActivity extends AppCompatActivity {
    ActivityUserInsightBinding binding;
    UserCategoryViewModel userCategoryViewModel;
    CategoryViewModel categoryViewModel;
    List<CategoryModel> categoryModels;
    List<UserCategoryModel> userCategoryModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUserInsightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        UserModel userModel = getIntent().getParcelableExtra("user");


        userCategoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserCategoryViewModel.class);
        categoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryViewModel.class);
        categoryViewModel.getCategoriesFirebase(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    categoryModels = task.getResult().toObjects(CategoryModel.class);
                    for (CategoryModel categoryModel : categoryModels) {
                        Log.d(TAG, categoryModel.toString());
                    }
                    userCategoryViewModel.getUserCategoryByUserIdFirebase(userModel.uId, new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                userCategoryModels = task.getResult().toObjects(UserCategoryModel.class);
                                for (UserCategoryModel userCategoryModel : userCategoryModels) {
                                    Log.d(TAG, userCategoryModel.toString());
                                }
                                binding.tvUserCategory.setText(StringUtilsForDuc.listInsightToString(userCategoryModels, categoryModels));
                            } else {
                                Log.w(TAG, "Error getting bookmarkModel by userId", task.getException());
                            }
                        }
                    });
                } else {
                    Log.w(TAG, "Error getting bookmarkModel by userId", task.getException());
                }
            }
        });

    }
}