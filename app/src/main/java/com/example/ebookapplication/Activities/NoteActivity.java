package com.example.ebookapplication.Activities;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebookapplication.Adapter.NoteAdapter;
import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.BookmarkUserModel;
import com.example.ebookapplication.NoteModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.UserModel;
import com.example.ebookapplication.Utils.FirebaseHelper;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.ViewModel.BookmarkUserViewModel;
import com.example.ebookapplication.ViewModel.NoteViewModel;
import com.example.ebookapplication.databinding.ActivityNoteBinding;
import com.example.ebookapplication.databinding.ActivityUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    ActivityNoteBinding binding;
    NoteViewModel noteViewModel;
    BookmarkModel bookmarkModel;
    UserModel userModel;
    BookmarkUserViewModel bookmarkUserViewModel;

    BookmarkUserModel bookmarkUserModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getNotesDataAndAdapter();
        getInterationData();

    }

    private void getInterationData() {
        bookmarkUserViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BookmarkUserViewModel.class);
        bookmarkUserViewModel.getBookmarkUserByBookmarkIdAndUserIdFirebase(bookmarkModel.bmFirebaseId, FirebaseHelper.getInstance().getAuth().getUid(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<BookmarkUserModel> bookmarkUserModels = task.getResult().toObjects(BookmarkUserModel.class);
                    if (bookmarkUserModels != null && bookmarkUserModels.size() != 0) {
                        bookmarkUserModel = bookmarkUserModels.get(0);
                        initUI();

                    } else {
                        bookmarkUserModel = new BookmarkUserModel();
                        bookmarkUserModel.userId = FirebaseHelper.getInstance().getAuth().getUid();
                        bookmarkUserModel.bookmarkId = bookmarkModel.bmFirebaseId;
                        bookmarkUserModel.liked = false;
                        bookmarkUserModel.saved = false;
                        bookmarkUserViewModel.addBookmarkUserFirebase(bookmarkUserModel, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(NoteActivity.this, "You haven't interacted with this bookmark", Toast.LENGTH_SHORT).show();
                                initUI();
                            }
                        });
                    }
                } else {
                    Log.w(TAG, "Error getting bookmarkUser by bookmarkId and userId", task.getException());
                }
            }
        });
    }

    @Override
    protected void onPause() {
        bookmarkUserViewModel.updateBookmarkUserFirebase(bookmarkUserModel.buFirebaseId, bookmarkModel, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.w(TAG, bookmarkUserModel.toString(), task.getException());
                Toast.makeText(NoteActivity.this, "updated interaction", Toast.LENGTH_SHORT).show();
            }
        });
        super.onPause();
    }

    private void initUI() {
        binding.floatingActionButton.setOnClickListener(v -> {
            if (bookmarkUserModel.saved) {
                binding.floatingActionButton.setImageResource(R.drawable.baseline_bookmark_border_24);
                bookmarkUserModel.saved = false;
            } else {
                binding.floatingActionButton.setImageResource(R.drawable.baseline_bookmark_24);
                bookmarkUserModel.saved = true;
            }
        });
        binding.floatingActionButton2.setOnClickListener(v -> {
            if (bookmarkUserModel.liked) {
                binding.floatingActionButton2.setImageResource(R.drawable.baseline_favorite_border_24);
                bookmarkUserModel.liked = false;
            } else {
                binding.floatingActionButton2.setImageResource(R.drawable.baseline_favorite_24);
                bookmarkUserModel.liked = true;
            }
        });
    }

    private void getNotesDataAndAdapter() {
        bookmarkModel = getIntent().getParcelableExtra("bookmark");

        binding.setBookmarkModel(bookmarkModel);

        noteViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(NoteViewModel.class);
        NoteAdapter noteAdapter = new NoteAdapter(this);
        binding.rvNoteList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNoteList.setAdapter(noteAdapter);
        noteViewModel.getNotesByBookmarkIdFirebase(bookmarkModel.bmFirebaseId, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<NoteModel> noteModelList = task.getResult().toObjects(NoteModel.class);
                    noteAdapter.setNotes(noteModelList);
                    for (NoteModel noteModel : noteModelList) {
                        Toast.makeText(NoteActivity.this, noteModel.noteContent, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, noteModel.toString());
                    }
                } else {
                    Log.w(TAG, "Error getting note by bookmarkId", task.getException());
                }
            }
        });
    }
}