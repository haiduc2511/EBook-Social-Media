package com.example.ebookapplication.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.databinding.ActivityBookDetailBinding;

public class BookDetailActivity extends AppCompatActivity {
    BookViewModel bookViewModel;
    BookModel bookModel;
    ActivityBookDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBookDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getData();
        initUI();

    }

    private void getData() {
        bookModel = getIntent().getParcelableExtra("book");
        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookViewModel.class);
    }

    private void initUI() {
        binding.tvBookTitle.setText("" + bookModel.bookTitle);
        binding.tvAuthorName.setText("" + bookModel.authorName);
        binding.tvPageNumber.setText("" + bookModel.numberOfPages);
        binding.tvBookCategory.setText("" + bookModel.bookCategory);
        binding.tvBookRating.setText("chua biet de tam la 4.5 sao");
        binding.tvBookSummary.setText("" + bookModel.bookSummary);
        binding.fabAddPage.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPageActivity.class);
            intent.putExtra("book", bookModel);
            startActivity(intent);
        });

        binding.ivBookCover.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReadingActivity.class);
            intent.putExtra("book", bookModel);
            startActivity(intent);
        });
        binding.floatingActionButton3.setOnClickListener(v -> {
            bookViewModel.insertBook(bookModel);
        });

    }
}