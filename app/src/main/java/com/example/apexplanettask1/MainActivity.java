package com.example.apexplanettask1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.button.MaterialButton;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView tvProductsCount, tvLowStockCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load WelcomeFragment into the container
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, welcomeFragment);
        transaction.commit();

        // Initialize Views
        tvProductsCount = findViewById(R.id.tvProductsCount);
        tvLowStockCount = findViewById(R.id.tvLowStockCount);
        MaterialButton btnAddProduct = findViewById(R.id.btnAddProduct);
        MaterialButton btnViewProducts = findViewById(R.id.btnViewProducts);
        MaterialButton btnAboutStore = findViewById(R.id.btnAboutStore);

        // Navigation
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddProductActivity.class));
            }
        });

        btnViewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProductsActivity.class));
            }
        });

        btnAboutStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchDashboardData();
    }

    private void fetchDashboardData() {
        Log.d(TAG, "Refreshing dashboard metrics...");
        RetrofitClient.getApiService().getProducts().enqueue(new Callback<Map<String, Product>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Product>> call, @NonNull Response<Map<String, Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Product> productMap = response.body();
                    int total = productMap.size();
                    int lowStock = 0;
                    for (Product p : productMap.values()) {
                        if (p.getStockQuantity() <= 5) {
                            lowStock++;
                        }
                    }
                    tvProductsCount.setText(String.valueOf(total));
                    tvLowStockCount.setText(lowStock + " Items");
                } else {
                    // Safe defaults for empty/null database
                    tvProductsCount.setText("0");
                    tvLowStockCount.setText("0 Items");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Product>> call, @NonNull Throwable t) {
                Log.e(TAG, "Failed to fetch dashboard data: " + t.getMessage());
                tvProductsCount.setText("--");
                tvLowStockCount.setText("--");
            }
        });
    }
}