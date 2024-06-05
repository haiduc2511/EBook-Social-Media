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

import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.BookRatingModel;
import com.example.ebookapplication.MathUtilsForDuc;
import com.example.ebookapplication.PageModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.SharedPrefManager;
import com.example.ebookapplication.ViewModel.BookRatingViewModel;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.databinding.ActivityBookDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    BookViewModel bookViewModel;
    BookModel bookModel;
    ActivityBookDetailBinding binding;
    BookRatingViewModel bookRatingViewModel;
    List<BookRatingModel> bookRatingList;

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
        bookRatingViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookRatingViewModel.class);
        bookRatingViewModel.getBookRatingsByBookIdFirebase(bookModel.bFirebaseId, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    bookRatingList = task.getResult().toObjects(BookRatingModel.class);
                    double ratingNumber = MathUtilsForDuc.getRatingAverage(bookRatingList);
                    binding.tvBookRating.setText("Rating\n\n" + ratingNumber + " ★");
                } else {
                    Log.w(TAG, "Error getting book ratings by bookId", task.getException());
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        initUI();
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
        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        String initStatus = sharedPrefManager.getData("admin_status");
        if (initStatus.equals("") || initStatus.equals("user")) {
            binding.fabAddPage.setVisibility(View.INVISIBLE);
        } else if (initStatus.equals("admin")) {
            binding.fabAddPage.setVisibility(View.VISIBLE);
        }

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