package com.example.apexplanettask1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 101;
    private TextView tvProductsCount, tvLowStockCount;
    private FirebaseAuth mAuth;
    private boolean isFirebaseAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Safety inflation
        try {
            setContentView(R.layout.activity_main);
        } catch (Exception e) {
            Log.e(TAG, "Layout inflation failed");
            return;
        }

        // CRASH-PROOF: Safely check for Firebase initialization
        try {
            if (!FirebaseApp.getApps(this).isEmpty()) {
                mAuth = FirebaseAuth.getInstance();
                isFirebaseAvailable = true;
            } else {
                Log.w(TAG, "Firebase not available. Dashboard running in Demo Mode.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Firebase error: " + e.getMessage());
        }

        NotificationHelper.createNotificationChannel(this);

        // Load WelcomeFragment into the container
        try {
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, welcomeFragment);
            transaction.commit();
        } catch (Exception e) {
            Log.e(TAG, "Fragment load failed");
        }

        // Initialize Views
        tvProductsCount = findViewById(R.id.tvProductsCount);
        tvLowStockCount = findViewById(R.id.tvLowStockCount);
        MaterialButton btnAddProduct = findViewById(R.id.btnAddProduct);
        MaterialButton btnViewProducts = findViewById(R.id.btnViewProducts);
        MaterialButton btnAboutStore = findViewById(R.id.btnAboutStore);
        ImageButton btnLogout = findViewById(R.id.btnLogout);

        // Request notification permission for Android 13+
        requestNotificationPermission();

        // Navigation
        if (btnAddProduct != null) btnAddProduct.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddProductActivity.class)));
        if (btnViewProducts != null) btnViewProducts.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProductsActivity.class)));
        if (btnAboutStore != null) btnAboutStore.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AboutActivity.class)));

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                if (isFirebaseAvailable && mAuth != null) {
                    mAuth.signOut();
                }
                Toast.makeText(this, "Logged out (Demo Mode)", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            });
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchDashboardData();
    }

    private void fetchDashboardData() {
        Log.d(TAG, "Refreshing dashboard metrics...");
        try {
            RetrofitClient.getApiService().getProducts().enqueue(new Callback<Map<String, Product>>() {
                @Override
                public void onResponse(@NonNull Call<Map<String, Product>> call, @NonNull Response<Map<String, Product>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Map<String, Product> productMap = response.body();
                        int total = productMap.size();
                        int lowStock = 0;
                        
                        for (Product p : productMap.values()) {
                            if (p.getStockQuantity() <= NotificationHelper.LOW_STOCK_THRESHOLD) {
                                lowStock++;
                                NotificationHelper.sendLowStockNotification(MainActivity.this, p.getName(), p.getStockQuantity());
                            }
                        }
                        if (tvProductsCount != null) tvProductsCount.setText(String.valueOf(total));
                        if (tvLowStockCount != null) tvLowStockCount.setText(lowStock + " Items");
                    } else {
                        updateEmptyMetrics();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Map<String, Product>> call, @NonNull Throwable t) {
                    Log.e(TAG, "Fetch failed: " + t.getMessage());
                    updateEmptyMetrics();
                }
            });
        } catch (Exception e) {
            updateEmptyMetrics();
        }
    }

    private void updateEmptyMetrics() {
        if (tvProductsCount != null) tvProductsCount.setText("0");
        if (tvLowStockCount != null) tvLowStockCount.setText("0 Items");
    }
}