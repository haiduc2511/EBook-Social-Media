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
import com.example.ebookapplication.NoteModel;
import com.example.ebookapplication.R;
import com.example.ebookapplication.ViewModel.BookViewModel;
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
        getDataAndAdapter();

    }

    private void getDataAndAdapter() {
        bookmarkModel = getIntent().getParcelableExtra("bookmark");
        noteViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(NoteViewModel.class);
        binding.setBookmarkModel(bookmarkModel);
        TextView textView = findViewById(R.id.textView111);
//        textView.setText("Notes from " + bookmarkModel.bookmarkName);
        binding.textView111.setText("dung dc binding r");
        NoteAdapter noteAdapter = new NoteAdapter(this);
        binding.rvNoteList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNoteList.setAdapter(noteAdapter);
//        RecyclerView recyclerView = findViewById(R.id.rv_note_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(noteAdapter);
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