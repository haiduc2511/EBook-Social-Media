package com.example.ebookapplication.AuthenticationActivities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ebookapplication.R;
import com.example.ebookapplication.UserModel;
import com.example.ebookapplication.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUI();


    }

    private void initUI() {


        binding.btRegister.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            String password = binding.etPassword.getText().toString();
            createAccount(email, password);
        });

    }
    public void createUser() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            UserModel user = new UserModel();
            user.uId = uid;
            user.userName = binding.etUsername.getText().toString();
            user.userEmail = binding.etEmail.getText().toString();
            user.userFollowers = 0;
            user.userAge = 20;
            user.userGender = "male";

            db.collection("users").document(uid).set(user)
                    .addOnSuccessListener(aVoid -> {
                        // User data successfully written!
                        Log.d("Firestore", "DocumentSnapshot successfully written!");
                    })
                    .addOnFailureListener(e -> {
                        // Failed to write user data
                        Log.w("Firestore", "Error writing document", e);
                    });

        }
    }
    private void createAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                if (firebaseUser != null) {
                    createUser();
                }
                Toast.makeText(RegisterActivity.this, "Create account successfully", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        }). addOnCanceledListener(RegisterActivity.this, new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(RegisterActivity.this, "Create account failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}