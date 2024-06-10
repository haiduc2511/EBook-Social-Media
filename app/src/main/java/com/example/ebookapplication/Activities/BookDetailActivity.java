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
import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.CategoryModel;
import com.example.ebookapplication.UserCategoryModel;
import com.example.ebookapplication.Utils.FirebaseHelper;
import com.example.ebookapplication.Utils.MathUtilsForDuc;
import com.example.ebookapplication.R;
import com.example.ebookapplication.Utils.SharedPrefManager;
import com.example.ebookapplication.ViewModel.BookRatingViewModel;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.ViewModel.BookmarkViewModel;
import com.example.ebookapplication.ViewModel.CategoryViewModel;
import com.example.ebookapplication.ViewModel.UserCategoryViewModel;
import com.example.ebookapplication.databinding.ActivityBookDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    BookViewModel bookViewModel;
    BookModel bookModel;
    ActivityBookDetailBinding binding;
    BookRatingViewModel bookRatingViewModel;
    BookmarkViewModel bookmarkViewModel;
    CategoryViewModel categoryViewModel;
    UserCategoryViewModel userCategoryViewModel;
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
        categoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CategoryViewModel.class);
        userCategoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserCategoryViewModel.class);
        bookmarkViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookmarkViewModel.class);
        categoryViewModel.getCategoryByIdFirebase(bookModel.bookCategory, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    binding.setCategoryModel(task.getResult().toObjects(CategoryModel.class).get(0));
                }
            }
        });
        bookRatingViewModel.getBookRatingsByBookIdFirebase(bookModel.bFirebaseId, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    bookRatingList = task.getResult().toObjects(BookRatingModel.class);
                    double ratingNumber = MathUtilsForDuc.getRatingAverage(bookRatingList);
                    DecimalFormat df = new DecimalFormat("#.##");
                    binding.tvBookRating.setText("Rating\n\n" + df.format(ratingNumber) + " ★");
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
                binding.setBookModel(bookModel);
                Toast.makeText(BookDetailActivity.this, "" + bookModel.numberOfPages, Toast.LENGTH_SHORT).show();
            }
        });
        //Toast.makeText(this, bookModel.toString(), Toast.LENGTH_SHORT).show();
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
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String categoryId = bookModel.bookCategory;
            userCategoryViewModel.getUserCategoryByUserIdAndCategoryIdFirebase(userId, categoryId, new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        if (task.getResult().toObjects(UserCategoryModel.class).size() != 0) {
                            UserCategoryModel userCategoryModel = task.getResult().toObjects(UserCategoryModel.class).get(0);
                            userCategoryModel.userCategoryPoints += 5;
                            userCategoryViewModel.updateUserCategoryFirebase(userCategoryModel.ucFirebaseId, userCategoryModel, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(BookDetailActivity.this, "update userCategory successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            UserCategoryModel userCategoryModel = new UserCategoryModel();
                            userCategoryModel.categoryId = categoryId;
                            userCategoryModel.userId = userId;
                            userCategoryViewModel.addUserCategoryFirebase(userCategoryModel, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(BookDetailActivity.this, "Added new userCategory successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        
                    }
                }
            });
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

        binding.fabAddBookmark.setOnClickListener(v -> {
            bookmarkViewModel.getBookmarksByUserIdAndBookIdFirebase(FirebaseHelper.getInstance().getAuth().getCurrentUser().getUid(), bookModel.bFirebaseId, new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<BookmarkModel> bookmarkModelList = task.getResult().toObjects(BookmarkModel.class);
                        if (bookmarkModelList.size() == 0) {
                            Intent intent = new Intent(BookDetailActivity.this, AddBookmarkActivity.class);
                            intent.putExtra("book", bookModel);
                            startActivity(intent);
                        } else {
                            Toast.makeText(BookDetailActivity.this, "You've already added a bookmark", Toast.LENGTH_SHORT).show();
                        }
                        
                    } else {
                        Log.w(TAG, "Error getting bookmarkModel by userId", task.getException());
                    }
                }
            });
            
        });



    }
}