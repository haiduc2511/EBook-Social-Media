package com.example.ebookapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ebookapplication.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    ActivityStartBinding binding;
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
        binding.button.setOnClickListener(v -> {
            Toast.makeText(this, "vô BookList Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StartActivity.this, BookListActivity.class);
            startActivity(intent);
        });

        binding.floatingActionButton.setOnClickListener(v -> {
            Toast.makeText(this, "vô User Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StartActivity.this, UserActivity.class);
            startActivity(intent);
        });

        binding.button3.setOnClickListener(v -> {
            Toast.makeText(this, "vô Add Book Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StartActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

    }


}