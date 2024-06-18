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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ebookapplication.Adapter.BookAdapter;
import com.example.ebookapplication.Adapter.BookmarkAdapter;
import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.ViewModel.BookmarkViewModel;
import com.example.ebookapplication.databinding.ActivityBookRecommendListBinding;
import com.example.ebookapplication.databinding.ActivityBookmarkRecommendListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class BookmarkRecommendListActivity extends AppCompatActivity {

    ActivityBookmarkRecommendListBinding binding;
    BookmarkViewModel bookmarkViewModel;
    BookmarkAdapter bookmarkAdapter;
    List<BookmarkModel> bookmarkRecommendList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBookmarkRecommendListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.rvBookmarkRecommendList.setLayoutManager(new GridLayoutManager(this, 3));
        bookmarkAdapter = new BookmarkAdapter(this);
        binding.rvBookmarkRecommendList.setAdapter(bookmarkAdapter);

        bookmarkViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookmarkViewModel.class);
        bookmarkViewModel.getBookmarksFirebase(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    bookmarkRecommendList = task.getResult().toObjects(BookmarkModel.class);
                    bookmarkAdapter.setBookmarks(bookmarkRecommendList);
                    for (BookmarkModel bookmarkModel : bookmarkRecommendList) {
                        Log.d(TAG, bookmarkModel.toString());
                    }
                } else {
                    Log.w(TAG, "Error getting bookmarkModel", task.getException());
                }
            }
        });


    }
}