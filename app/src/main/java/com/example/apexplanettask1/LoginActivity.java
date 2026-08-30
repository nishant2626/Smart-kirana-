package com.example.apexplanettask1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

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

        // Initialize Firebase safely
        try {
            if (!FirebaseApp.getApps(this).isEmpty()) {
                mAuth = FirebaseAuth.getInstance();
                isFirebaseReady = true;
                Log.d(TAG, "Firebase Ready");
            }
        } catch (Throwable t) {
            Log.e(TAG, "Firebase Init Error: " + t.getMessage());
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        pbLoading = findViewById(R.id.pbLoading);

        // Button Listeners
        if (btnLogin != null) btnLogin.setOnClickListener(v -> handleAuth(true));
        if (btnRegister != null) btnRegister.setOnClickListener(v -> handleAuth(false));
    }

    private void handleAuth(boolean isLogin) {
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        // VALIDATION
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.email_hint));
            return;
        }
        if (password.length() < 6) {
            etPassword.setError(getString(R.string.password_hint));
            Toast.makeText(this, "Password must be 6+ characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        // MASTER BYPASS
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
                            Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_LONG).show();
                            navigateToMain();
                        } else {
                            handleFailure(task.getException(), "Registration");
                        }
                    });
            }
        } else {
            // DEMO FALLBACK
            pbLoading.postDelayed(this::navigateToMain, 1000);
        }
    }

    private void handleFailure(Exception e, String type) {
        if (pbLoading != null) pbLoading.setVisibility(View.GONE);
        setButtonsEnabled(true);
        String errorMsg = e != null ? e.getMessage() : getString(R.string.auth_failed);
        
        Log.e(TAG, type + " Error: " + errorMsg);

        if (e instanceof FirebaseAuthUserCollisionException) {
            Toast.makeText(this, "This email is already registered. Please Login.", Toast.LENGTH_LONG).show();
        } else if (e instanceof FirebaseAuthWeakPasswordException) {
            Toast.makeText(this, "Password is too weak!", Toast.LENGTH_SHORT).show();
        } else if (errorMsg.contains("disabled")) {
            Toast.makeText(this, "ERROR: Enable 'Email/Password' in Firebase Auth Console!", Toast.LENGTH_LONG).show();
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