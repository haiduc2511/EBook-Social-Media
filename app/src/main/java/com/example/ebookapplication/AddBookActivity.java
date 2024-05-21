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
import androidx.lifecycle.ViewModelProvider;

import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.databinding.ActivityAddBookBinding;
import com.example.ebookapplication.databinding.ActivityBookListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class AddBookActivity extends AppCompatActivity {
    BookViewModel bookViewModel;
    ActivityAddBookBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookViewModel.class);

        binding.btAddBook.setOnClickListener(v -> {
            BookModel bookModel = new BookModel();
            bookModel.bookTitle = binding.etBookTitle.getText().toString();
            bookModel.authorName = binding.etAuthorName.getText().toString();
            bookModel.numberOfPages = Integer.parseInt(binding.etNumPages.getText().toString());
            bookModel.bookSummary = binding.etSummary.getText().toString();
            bookModel.bookCategory = binding.etCategory.getText().toString();
            Toast.makeText(this, bookModel.toString(), Toast.LENGTH_SHORT).show();
            bookViewModel.addBookFirebase(bookModel, new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Book added with ID: " + task.getResult().getId());
                    } else {
                        Log.w(TAG, "Error adding book", task.getException());
                    }
                }
            });
        });



    }
}