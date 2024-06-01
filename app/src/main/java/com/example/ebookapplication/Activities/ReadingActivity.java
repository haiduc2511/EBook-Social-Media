package com.example.ebookapplication.Activities;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ebookapplication.Adapter.BookAdapter;
import com.example.ebookapplication.Adapter.PageAdapter;
import com.example.ebookapplication.BookModel;
import com.example.ebookapplication.PageModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.ViewModel.BookViewModel;
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
    List<PageModel> pages;


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

    }

    private void initViewPager() {
        pageAdapter = new PageAdapter(this, binding.tvPageNumber);
        binding.vpPages.setAdapter(pageAdapter);
        BookModel bookModel = getIntent().getParcelableExtra("book");
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

    }
}