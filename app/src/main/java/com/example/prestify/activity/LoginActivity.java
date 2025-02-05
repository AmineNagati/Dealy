package com.example.prestify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prestify.R;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.User;
import com.example.prestify.util.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton, signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(LoginActivity.this);
                User user = db.userDao().login(email, password);
                runOnUiThread(() -> {
                    if (user != null) {
                        // Créer la session
                        SessionManager sessionManager = new SessionManager(LoginActivity.this);
                        sessionManager.createLoginSession(user.getId());

                        // Rediriger vers MainActivity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish(); // Important pour empêcher le retour
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        signupButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }
}