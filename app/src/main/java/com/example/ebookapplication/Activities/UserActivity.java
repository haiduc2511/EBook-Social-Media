package com.example.ebookapplication.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ebookapplication.Adapter.BookmarkAdapter;
import com.example.ebookapplication.AuthenticationActivities.MainActivity;
import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.Utils.FirebaseHelper;
import com.example.ebookapplication.Utils.SharedPrefManager;
import com.example.ebookapplication.UserModel;
import com.example.ebookapplication.ViewModel.BookRatingViewModel;
import com.example.ebookapplication.ViewModel.BookmarkViewModel;
import com.example.ebookapplication.databinding.ActivityUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class UserActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ActivityUserBinding binding;
    BookmarkViewModel bookmarkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getCurrentUser(db, mAuth);
        initUi();

    }

    private void getBookmarks(String uid) {
        binding.rvBookmarks.setLayoutManager(new GridLayoutManager(this, 3));
        BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(this);
        binding.rvBookmarks.setAdapter(bookmarkAdapter);

        bookmarkViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookmarkViewModel.class);
        bookmarkViewModel.getBookRatingsByUserIdFirebase(uid, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<BookmarkModel> bookmarkModelList = task.getResult().toObjects(BookmarkModel.class);
                    bookmarkAdapter.setBookmarks(bookmarkModelList);
                    for (BookmarkModel bookmarkModel : bookmarkModelList) {
                        Log.d(TAG, bookmarkModel.toString());
                    }
                } else {
                    Log.w(TAG, "Error getting bookmarkModel by userId", task.getException());
                }
            }
        });
    }

    public void initUi() {
        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        String initStatus = sharedPrefManager.getData("admin_status");
        if (initStatus.equals("") || initStatus.equals("user")) {
            binding.btAddCategory.setVisibility(View.INVISIBLE);
            binding.btAddBook.setVisibility(View.INVISIBLE);
        } else if (initStatus.equals("admin")) {
            binding.btAddCategory.setVisibility(View.VISIBLE);
            binding.btAddBook.setVisibility(View.VISIBLE);
        }
        binding.btAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddBookActivity.class);
            startActivity(intent);
        });

        binding.fabLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        binding.button.setOnClickListener(v -> {
            String status = sharedPrefManager.getData("admin_status");
            if (status.equals("") || status.equals("user")) {
                sharedPrefManager.saveData("admin_status", "admin");
                Toast.makeText(this, "Status changed from user to admin", Toast.LENGTH_SHORT).show();
                binding.btAddCategory.setVisibility(View.VISIBLE);
                binding.btAddBook.setVisibility(View.VISIBLE);
            } else if (status.equals("admin")) {
                sharedPrefManager.saveData("admin_status", "user");
                Toast.makeText(this, "Status changed from admin to user", Toast.LENGTH_SHORT).show();
                binding.btAddBook.setVisibility(View.INVISIBLE);
                binding.btAddCategory.setVisibility(View.INVISIBLE);
            }
        });
        binding.btAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCategoryActivity.class);
            startActivity(intent);
        });
    }

    public void getCurrentUser(FirebaseFirestore db, FirebaseAuth mAuth) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        db.collection("users").document(firebaseUser.getUid()).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            UserModel user = document.toObject(UserModel.class);
                            binding.setUserModel(user);
                            getBookmarks(user.uId);
                        } else {
                            Log.d("Firestore", "No such document");
                        }
                    } else {
                        Log.d("Firestore", "get failed with ", task.getException());
                    }
                });
    }
}