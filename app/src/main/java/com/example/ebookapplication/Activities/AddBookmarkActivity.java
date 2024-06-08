package com.example.ebookapplication.Activities;

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
import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.BookmarkUserModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.Utils.FirebaseHelper;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.ViewModel.BookmarkUserViewModel;
import com.example.ebookapplication.ViewModel.BookmarkViewModel;
import com.example.ebookapplication.databinding.ActivityAddBookBinding;
import com.example.ebookapplication.databinding.ActivityAddBookmarkBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AddBookmarkActivity extends AppCompatActivity {
    ActivityAddBookmarkBinding binding;
    BookmarkUserViewModel bookmarkUserViewModel;
    BookmarkViewModel bookmarkViewModel;
    BookModel bookModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddBookmarkBinding.inflate(getLayoutInflater());
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
        bookmarkUserViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookmarkUserViewModel.class);
        bookmarkViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookmarkViewModel.class);
    }
    private void initUI() {
        binding.btAddBookmark.setOnClickListener(v -> {
            BookmarkModel bookmarkModel = new BookmarkModel();
            bookmarkModel.bookId = bookModel.bFirebaseId;
            bookmarkModel.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            bookmarkModel.bookmarkName = binding.etBookmarkName.getText().toString();
            String bookmarkId = bookmarkViewModel.addBookmarkFirebase(bookmarkModel, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(AddBookmarkActivity.this, "Add bookmark to Firebase successfully", Toast.LENGTH_SHORT).show();
                }
            });
            BookmarkUserModel bookmarkUserModel = new BookmarkUserModel();
            bookmarkUserModel.bookmarkId = bookmarkId;
            bookmarkUserModel.userId = FirebaseHelper.getInstance().getAuth().getCurrentUser().getUid();
            bookmarkUserViewModel.addBookmarkUserFirebase(bookmarkUserModel, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(AddBookmarkActivity.this, "Add bookmarkUser to Firebase successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        });
    }
}