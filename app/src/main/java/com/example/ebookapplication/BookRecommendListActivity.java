package com.example.ebookapplication;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ebookapplication.Adapter.BookAdapter;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.databinding.ActivityBookListBinding;
import com.example.ebookapplication.databinding.ActivityBookRecommendListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookRecommendListActivity extends AppCompatActivity {
    ActivityBookRecommendListBinding binding;
    BookViewModel bookViewModel;
    BookAdapter bookAdapter;
    List<BookModel> bookRecommendList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBookRecommendListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.rvBookRecommendList.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(this);
        binding.rvBookRecommendList.setAdapter(bookAdapter);

        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookViewModel.class);
        bookViewModel.getBooksFirebase(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    bookRecommendList = task.getResult().toObjects(BookModel.class);
                    bookAdapter.setBooks(bookRecommendList);
                    for (BookModel book : bookRecommendList) {
                        Log.d(TAG, book.toString());
                    }
                } else {
                    Log.w(TAG, "Error getting books by author", task.getException());
                }
            }
        });


    }
}