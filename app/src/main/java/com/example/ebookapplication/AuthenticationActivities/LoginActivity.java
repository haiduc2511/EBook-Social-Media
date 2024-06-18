package com.example.ebookapplication.AuthenticationActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ebookapplication.Activities.PasswordResetActivity;
import com.example.ebookapplication.R;
import com.example.ebookapplication.Activities.StartActivity;
import com.example.ebookapplication.Utils.FirebaseHelper;
import com.example.ebookapplication.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        auth = FirebaseHelper.getInstance().getAuth();
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUI();
    }

    private void initUI() {


        binding.btLogin.setOnClickListener(v -> {
            String password = binding.etPassword.getText().toString();
            String email = binding.etEmail.getText().toString();
            Toast.makeText(LoginActivity.this, "click bt login", Toast.LENGTH_SHORT).show();
            if (password.length() < 8) {
                Toast.makeText(this, "Mật khẩu chưa đủ 8 kí tự", Toast.LENGTH_SHORT).show();
            } else {
                signIn(email, password);
            }
        });
        binding.tvLoginForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(this, PasswordResetActivity.class);
            startActivity(intent);
        });
    }

    private void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong password or email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}