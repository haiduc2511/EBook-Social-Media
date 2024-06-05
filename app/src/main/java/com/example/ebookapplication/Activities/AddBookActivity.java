package com.example.ebookapplication.Activities;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.ebookapplication.Adapter.CategoryAdapter;
import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.CategoryModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.ViewModel.CategoryViewModel;
import com.example.ebookapplication.databinding.ActivityAddBookBinding;
import com.example.ebookapplication.databinding.ActivityBookListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {
    BookViewModel bookViewModel;
    ActivityAddBookBinding binding;
    boolean isCategorySelected;
    CategoryModel selectedCategory;
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
        initUI();



    }

    public void initUI() {

        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookViewModel.class);

        binding.btAddBook.setOnClickListener(v -> {
            if (isCategorySelected) {
                BookModel bookModel = new BookModel();
                bookModel.bookTitle = binding.etBookTitle.getText().toString();
                bookModel.authorName = binding.etAuthorName.getText().toString();
                bookModel.numberOfPages = 0;
                bookModel.bookSummary = binding.etSummary.getText().toString();
                Toast.makeText(this, bookModel.toString(), Toast.LENGTH_SHORT).show();
                bookViewModel.addBookFirebase(bookModel, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Book added with ID");
                        } else {
                            Log.w(TAG, "Error adding book", task.getException());
                        }
                    }
                });
            }

        });

        CategoryViewModel categoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryViewModel.class);
        categoryViewModel.getCategoriesFirebase(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<CategoryModel> categoryModelList = task.getResult().toObjects(CategoryModel.class);
                CategoryAdapter categoryAdapter = new CategoryAdapter(AddBookActivity.this, android.R.layout.simple_spinner_item, categoryModelList);
                binding.spCategory.setAdapter(categoryAdapter);
            }
        });

        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isCategorySelected = true; // A selection has been made
                selectedCategory = (CategoryModel) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}