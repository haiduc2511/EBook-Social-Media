package com.example.ebookapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ebookapplication.databinding.ActivityBookListBinding;

import java.util.List;

public class BookListActivity extends AppCompatActivity {
    ActivityBookListBinding binding;
    BookViewModel bookViewModel;
    BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBookListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.rvBookList.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(this);
        binding.rvBookList.setAdapter(bookAdapter);

        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookViewModel.class);
        BookModel bookModel = new BookModel();
        bookModel.bookTitle = "duma";
        bookModel.authorName = "duma ten tac gia";
        bookModel.numberOfPages = 1;
        bookViewModel.insertBook(bookModel);
        bookViewModel.getAllBooksLive().observe(BookListActivity.this, new Observer<List<BookModel>>() {
            @Override
            public void onChanged(List<BookModel> bookModelList) {
                if (bookModelList != null) {
                    bookAdapter.setBooks(bookModelList);
                }
            }
        });


    }
}