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

import com.example.ebookapplication.BookmarkModel;
import com.example.ebookapplication.NoteModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.ViewModel.BookViewModel;
import com.example.ebookapplication.ViewModel.NoteViewModel;
import com.example.ebookapplication.databinding.ActivityAddNoteBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public class AddNoteActivity extends AppCompatActivity {
    ActivityAddNoteBinding binding;
    NoteViewModel noteViewModel;
    NoteModel noteModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String pageId = getIntent().getStringExtra("page");
        BookmarkModel bookmarkModel = getIntent().getParcelableExtra("bookmark");
        Toast.makeText(this, bookmarkModel.toString() + " " + pageId, Toast.LENGTH_SHORT).show();

        noteViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(NoteViewModel.class);
        noteViewModel.getNotesByBookmarkIdAndPageIdFirebase(bookmarkModel.bmFirebaseId, pageId, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().toObjects(BookmarkModel.class).size() != 0) {
                        noteModel = task.getResult().toObjects(NoteModel.class).get(0);
                        binding.etNoteContent.setText(noteModel.noteContent);
                    }
                } else {
                    Log.w(TAG, "Error getting note by bookmarkId and pageId", task.getException());
                }
            }
        });

        noteViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(NoteViewModel.class);
        binding.btAddNote.setOnClickListener(v -> {
            if (noteModel == null) {
                noteModel = new NoteModel();
                noteModel.pageId = pageId;
                noteModel.bookmarkId = bookmarkModel.bmFirebaseId;
                noteModel.noteContent = binding.etNoteContent.getText().toString();
                noteViewModel.addNoteFirebase(noteModel, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddNoteActivity.this, "Added new noteModel successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                noteModel.noteContent = binding.etNoteContent.getText().toString();
                noteViewModel.updateNoteFirebase(noteModel.nFirebaseId, noteModel, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddNoteActivity.this, "Updated noteModel successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}