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
import androidx.viewpager2.widget.ViewPager2;

import com.example.ebookapplication.Adapter.BookAdapter;
import com.example.ebookapplication.Adapter.PageAdapter;
import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.PageModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.Utils.FirebaseHelper;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.ViewModel.BookmarkViewModel;
import com.example.ebookapplication.ViewModel.PageViewModel;
import com.example.ebookapplication.databinding.ActivityReadingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ReadingActivity extends AppCompatActivity {
    ActivityReadingBinding binding;
    PageAdapter pageAdapter;
    PageViewModel pageViewModel;
    BookmarkViewModel bookmarkViewModel;
    List<PageModel> pages;
    BookModel bookModel;
    BookmarkModel bookmarkModel;
    int currentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityReadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUI();
        initViewPager();
    }

    private void initUI() {
        bookModel = getIntent().getParcelableExtra("book");
        String userId = FirebaseHelper.getInstance().getAuth().getCurrentUser().getUid().toString();
        bookmarkViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookmarkViewModel.class);
        
        binding.fabAddPageNote.setOnClickListener(v -> {
            bookmarkViewModel.getBookmarksByUserIdAndBookIdFirebase(userId, bookModel.bFirebaseId, new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().toObjects(BookmarkModel.class).size() != 0) {
                            bookmarkModel = task.getResult().toObjects(BookmarkModel.class).get(0);
                            Intent intent = new Intent(ReadingActivity.this, AddNoteActivity.class);
                            intent.putExtra("bookmark", bookmarkModel);
                            intent.putExtra("page", binding.tvPageNumber.getText().toString());
//                            Toast.makeText(ReadingActivity.this, bookmarkModel.toString() + "\n" + binding.tvPageNumber.getText().toString(), Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else {
                            Toast.makeText(ReadingActivity.this, "There are no bookmarks", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.w(TAG, "Error getting bookmarkModel by bookId and userId", task.getException());
                    }
                }
            });
            
        });
    }

    private void initViewPager() {
        pageAdapter = new PageAdapter(this, binding.tvPageNumber);
        binding.vpPages.setAdapter(pageAdapter);
        String bookId = bookModel.bFirebaseId;
        pageViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PageViewModel.class);
        pageViewModel.getPagesByBookIdFirebase(bookId, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    pages = task.getResult().toObjects(PageModel.class);
                    pageAdapter.setPages(pages);
                    for (PageModel page : pages) {
                        Log.d(TAG, page.toString());
                    }
                } else {
                    Log.w(TAG, "Error getting pages by book", task.getException());
                }
            }
        });
        binding.vpPages.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position > currentPage) {
                    onSwipeRight();
                } else if (position < currentPage) {
                    onSwipeLeft();
                }
                currentPage = position;
                binding.tvPageNumber.setText(pages.get(position).pFirebaseId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }
    private void onSwipeRight() {
        Toast.makeText(this, "Swiped Right", Toast.LENGTH_SHORT).show();
    }

    private void onSwipeLeft() {
        Toast.makeText(this, "Swiped Left", Toast.LENGTH_SHORT).show();
    }
}