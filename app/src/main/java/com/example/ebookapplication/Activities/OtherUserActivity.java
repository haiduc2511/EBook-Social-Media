package com.example.ebookapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ebookapplication.AuthenticationActivities.LoginActivity;
import com.example.ebookapplication.R;
import com.example.ebookapplication.SharedPrefManager;
import com.example.ebookapplication.UserModel;
import com.example.ebookapplication.databinding.ActivityOtherUserBinding;
import com.example.ebookapplication.databinding.ActivityUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OtherUserActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ActivityOtherUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityOtherUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUIAndGetUser(db);

    }

    public void initUIAndGetUser(FirebaseFirestore db) {
        String userId = getIntent().getStringExtra("user");
        db.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            UserModel user = document.toObject(UserModel.class);
                            binding.tvOtherUserInformation.setText(user.toString());
                        } else {
                            Log.d("Firestore", "No such user document");
                        }
                    } else {
                        Log.d("Firestore", "get failed with getting other user", task.getException());
                    }
                });
    }
}