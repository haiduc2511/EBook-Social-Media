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
import com.example.ebookapplication.PageModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.ViewModel.PageViewModel;
import com.example.ebookapplication.databinding.ActivityAddPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AddPageActivity extends AppCompatActivity {
    ActivityAddPageBinding binding;
    PageViewModel pageViewModel;
    BookViewModel bookViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BookModel bookModel = getIntent().getParcelableExtra("book");
        pageViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PageViewModel.class);
        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookViewModel.class);
        binding.btAddPage.setOnClickListener(v -> {
            PageModel pageModel = new PageModel();
            pageModel.bookId = bookModel.bFirebaseId;
            pageModel.content = binding.etPageContent.getText().toString();
            Toast.makeText(this, pageModel.toString(), Toast.LENGTH_SHORT).show();

            pageViewModel.addPageFirebase(pageModel, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        bookModel.numberOfPages += 1;
                        bookViewModel.updateBookFirebase(bookModel.bFirebaseId, bookModel, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Book updated number of pages after adding page");
                                } else {
                                    Log.w(TAG, "Error adding book", task.getException());
                                }
                            }
                        });

                        Log.d(TAG, "Page added");
                    } else {
                        Log.w(TAG, "Error adding page", task.getException());
                    }
                }
            });
        });
    }
}