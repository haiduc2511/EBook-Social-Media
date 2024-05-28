package com.example.ebookapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ebookapplication.AuthenticationActivities.LoginActivity;
import com.example.ebookapplication.R;
import com.example.ebookapplication.UserModel;
import com.example.ebookapplication.databinding.ActivityUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ActivityUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getCurrentUser(db, mAuth);
        initUi();

    }

    public void initUi() {
        binding.fabLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
    public void getCurrentUser(FirebaseFirestore db, FirebaseAuth mAuth) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        db.collection("users").document(firebaseUser.getUid()).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            UserModel user = document.toObject(UserModel.class);
                            binding.textView.setText(user.uId + "\n" +
                                    user.userName + "\n" +
                                    user.userAge + "\n" +
                                    user.userEmail + "\n" +
                                    user.userGender + "\n" +
                                    user.userFollowers + "\n" +
                                    firebaseUser.getUid()
                            );
                        } else {
                            Log.d("Firestore", "No such document");
                        }
                    } else {
                        Log.d("Firestore", "get failed with ", task.getException());
                    }
                });
    }
}