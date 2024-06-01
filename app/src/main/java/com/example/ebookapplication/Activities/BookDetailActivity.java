package com.example.ebookapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.PageModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.databinding.ActivityBookDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

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

    @Override
    protected void onResume() {
        super.onResume();
        bookViewModel.getBooksByIdFirebase(bookModel.bFirebaseId, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                bookModel = task.getResult().toObjects(BookModel.class).get(0);
            }
        });
        binding.setBookModel(bookModel);
        Toast.makeText(this, bookModel.toString(), Toast.LENGTH_SHORT).show();
    }

    private void initUI() {
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
        binding.fabDownloadBook.setOnClickListener(v -> {
            bookViewModel.insertBook(bookModel);
        });
        binding.fabBookRating.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookRatingActivity.class);
            intent.putExtra("book", bookModel);
            startActivity(intent);
        });




    }
}