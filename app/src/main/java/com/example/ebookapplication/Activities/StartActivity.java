package com.example.ebookapplication.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ebookapplication.Adapter.BookAdapter;
import com.example.ebookapplication.Adapter.BookAdapterHorizontal;
import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.SharedPrefManager;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.databinding.ActivityStartBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StartActivity extends AppCompatActivity {
    BookViewModel bookViewModel;
    BookAdapterHorizontal bookAdapterHorizontal;
    List<BookModel> bookRecommendList;
    ActivityStartBinding binding;
    BookModel currentBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initDataAndAdapter();
        initCurrentBook();
        initUI();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initCurrentBook();
    }

    private void initCurrentBook() {
        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        String bookId = sharedPrefManager.getData("current_book");
        
        if (bookId.equals("")) {
            binding.ivRecentBookCover.setImageResource(R.drawable.im_book_cover);
            binding.tvRecentBookName.setText("You haven't start reading");
            binding.tvRecentAuthorName.setText("You haven't start reading");
        } else {
            try {
                currentBook = bookViewModel.getBook(Integer.parseInt(bookId));
                binding.tvRecentBookName.setText(currentBook.bookTitle);
                binding.tvRecentAuthorName.setText(currentBook.authorName);
                binding.btContinueReading.setOnClickListener(v -> {
                    Intent intent = new Intent(StartActivity.this, ReadingActivity.class);
                    intent.putExtra("book", currentBook);
                    startActivity(intent);
                });
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initUI() {
        binding.tvSeeMoreDownloaded.setOnClickListener(v -> {
            Toast.makeText(this, "vô BookList Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StartActivity.this, BookListActivity.class);
            startActivity(intent);
        });

        binding.fabUserActivity.setOnClickListener(v -> {
            Toast.makeText(this, "vô User Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StartActivity.this, UserActivity.class);
            startActivity(intent);
        });

//        binding.button3.setOnClickListener(v -> {
//            Toast.makeText(this, "vô Add Book Activity", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(StartActivity.this, AddBookActivity.class);
//            startActivity(intent);
//        });

        binding.tvSeeMoreRecommended.setOnClickListener(v -> {
            Toast.makeText(this, "vô lướt mạng xã hội sách Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StartActivity.this, BookRecommendListActivity.class);
            startActivity(intent);
        });
    }

    private void initDataAndAdapter() {
        binding.rvBookRecommendList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        bookAdapterHorizontal = new BookAdapterHorizontal(this);
        binding.rvBookRecommendList.setAdapter(bookAdapterHorizontal);

        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookViewModel.class);
        bookViewModel.getBooksFirebase(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    bookRecommendList = task.getResult().toObjects(BookModel.class);
                    bookAdapterHorizontal.setBooks(bookRecommendList);
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