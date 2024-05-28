package com.example.ebookapplication.Activities;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
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
import com.example.ebookapplication.R;
import com.example.ebookapplication.ViewModel.BookRatingViewModel;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.databinding.ActivityBookDetailBinding;
import com.example.ebookapplication.databinding.ActivityBookRatingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class BookRatingActivity extends AppCompatActivity {
    BookRatingViewModel bookRatingViewModel;
    BookModel bookModel;
    ActivityBookRatingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBookRatingBinding.inflate(getLayoutInflater());
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
        bookRatingViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookRatingViewModel.class);
    }

    private void initUI() {
        binding.tvBookName.setText(bookModel.bookTitle);
        binding.rbBookRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(BookRatingActivity.this, "Bạn đang đánh giá " + rating + " sao", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btSendRatingBook.setOnClickListener(v -> {
            BookRatingModel bookRatingModel = new BookRatingModel();
            bookRatingModel.bookId = bookModel.bFirebaseId;
            bookRatingModel.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            bookRatingModel.star = Float.toString(binding.rbBookRating.getRating());
            bookRatingModel.comment = binding.etRatingComment.getText().toString();
            bookRatingViewModel.addBookRatingFirebase(bookRatingModel, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Book Rating added with userId " + bookRatingModel.userId + " bookId " + bookRatingModel.bookId);
                    } else {
                        Log.w(TAG, "Error adding book", task.getException());
                    }
                }
            });
        });
    }
}