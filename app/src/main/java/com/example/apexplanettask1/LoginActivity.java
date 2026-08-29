package com.example.apexplanettask1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "SmartKirana_Auth";
    private TextInputEditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private ProgressBar pbLoading;
    private FirebaseAuth mAuth;
    private boolean isFirebaseReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            setContentView(R.layout.activity_login);
        } catch (Exception e) {
            Log.e(TAG, "Layout inflation failed: " + e.getMessage());
            return;
        }

        // 1. Force Initialize Firebase
        try {
            FirebaseApp.initializeApp(this);
            mAuth = FirebaseAuth.getInstance();
            isFirebaseReady = true;
            Log.d(TAG, "Firebase Initialized Successfully");
        } catch (Exception e) {
            Log.e(TAG, "Firebase Init Error: " + e.getMessage());
            Toast.makeText(this, "Firebase Connection Error. Check google-services.json", Toast.LENGTH_LONG).show();
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        pbLoading = findViewById(R.id.pbLoading);

        // LOGIN BUTTON
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> handleAuth(true));
        }

        // REGISTER BUTTON
        if (btnRegister != null) {
            btnRegister.setOnClickListener(v -> handleAuth(false));
        }
    }

    private void handleAuth(boolean isLogin) {
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        // 1. Validation
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email (e.g. name@mail.com)");
            Toast.makeText(this, "Invalid Email Format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            Toast.makeText(this, "Password too short! Use 6+ characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        // MASTER BYPASS (Always works for presentation)
        if (email.equals("admin@store.com") && password.equals("admin123")) {
            navigateToMain();
            return;
        }

        if (pbLoading != null) pbLoading.setVisibility(View.VISIBLE);
        setButtonsEnabled(false);

        if (isFirebaseReady && mAuth != null) {
            if (isLogin) {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) navigateToMain();
                        else handleFailure(task.getException(), "Login");
                    });
            } else {
                // REGISTRATION
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Registration Successful! Account Created.", Toast.LENGTH_LONG).show();
                            navigateToMain();
                        } else {
                            handleFailure(task.getException(), "Registration");
                        }
                    });
            }
        } else {
            // DEMO FALLBACK
            Toast.makeText(this, "Demo Mode Access", Toast.LENGTH_SHORT).show();
            pbLoading.postDelayed(this::navigateToMain, 800);
        }
    }

    private void handleFailure(Exception e, String type) {
        if (pbLoading != null) pbLoading.setVisibility(View.GONE);
        setButtonsEnabled(true);
        
        String errorMsg = e != null ? e.getMessage() : "Unknown Connection Error";
        Log.e(TAG, type + " Error: " + errorMsg);

        if (e instanceof FirebaseAuthUserCollisionException) {
            Toast.makeText(this, "Email already exists! Please Login instead.", Toast.LENGTH_LONG).show();
        } else if (e instanceof FirebaseAuthWeakPasswordException) {
            Toast.makeText(this, "Password is too weak!", Toast.LENGTH_SHORT).show();
        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
            Toast.makeText(this, "Invalid credentials. Check your email/password.", Toast.LENGTH_LONG).show();
        } else if (errorMsg.contains("disabled")) {
            Toast.makeText(this, "CRITICAL: Email Login is DISABLED in Firebase. Go to Auth -> Sign-in Method and Enable it.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, type + " Failed: " + errorMsg, Toast.LENGTH_LONG).show();
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        if (btnLogin != null) btnLogin.setEnabled(enabled);
        if (btnRegister != null) btnRegister.setEnabled(enabled);
    }

    private void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}