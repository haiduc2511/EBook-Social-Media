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
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.ebookapplication.Adapter.BookmarkAdapter;
import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.UserModel;
import com.example.ebookapplication.ViewModel.BookmarkViewModel;
import com.example.ebookapplication.databinding.ActivityOtherUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class OtherUserActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ActivityOtherUserBinding binding;
    BookmarkViewModel bookmarkViewModel;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityOtherUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUIAndGetUser(db);

    }

    private void getBookmarks(String uid) {
        binding.rvBookmarks.setLayoutManager(new GridLayoutManager(this, 3));
        BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(this);
        binding.rvBookmarks.setAdapter(bookmarkAdapter);

        bookmarkViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookmarkViewModel.class);
        bookmarkViewModel.getBookmarksByUserIdFirebase(uid, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().toObjects(BookmarkModel.class) != null) {
                        List<BookmarkModel> bookmarkModelList = task.getResult().toObjects(BookmarkModel.class);
                        binding.tvBookmarkNum.setText(String.valueOf(bookmarkModelList.size()) + "\n posts");
                        bookmarkAdapter.setBookmarks(bookmarkModelList);
                        for (BookmarkModel bookmarkModel : bookmarkModelList) {
                            Log.d(TAG, bookmarkModel.toString());
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting bookmarkModel by userId", task.getException());
                }
            }
        });
    }
    public void initUIAndGetUser(FirebaseFirestore db) {
        String userId = getIntent().getStringExtra("user");
        db.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            userModel = document.toObject(UserModel.class);
                            binding.setUserModel(userModel);
                            getBookmarks(userModel.uId);
                        } else {
                            Log.d("Firestore", "No such user document");
                        }
                    } else {
                        Log.d("Firestore", "get failed with getting other user", task.getException());
                    }
                });
    }
}