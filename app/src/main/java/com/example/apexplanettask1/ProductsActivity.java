package com.example.apexplanettask1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {

    private static final String TAG = "ProductsActivity";
    private RecyclerView rvProducts;
    private ProductAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private TextView tvError;
    private Button btnRetry;
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvProducts = findViewById(R.id.rvProducts);
        progressBar = findViewById(R.id.progressBar);
        errorLayout = findViewById(R.id.errorLayout);
        tvError = findViewById(R.id.tvError);
        btnRetry = findViewById(R.id.btnRetry);
        FloatingActionButton fabAddProduct = findViewById(R.id.fabAddProduct);

        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(productList);
        rvProducts.setAdapter(adapter);

        fetchProducts();

        fabAddProduct.setOnClickListener(v -> startActivity(new Intent(ProductsActivity.this, AddProductActivity.class)));
        btnRetry.setOnClickListener(v -> fetchProducts());
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchProducts();
    }

    private void fetchProducts() {
        showLoading();
        RetrofitClient.getApiService().getProducts().enqueue(new Callback<Map<String, Product>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Product>> call, @NonNull Response<Map<String, Product>> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    productList.clear();
                    Map<String, Product> productMap = response.body();
                    
                    if (productMap != null && !productMap.isEmpty()) {
                        for (Map.Entry<String, Product> entry : productMap.entrySet()) {
                            Product p = entry.getValue();
                            p.setId(entry.getKey());
                            productList.add(p);
                        }
                        adapter.updateList(productList);
                        showData();
                    } else {
                        // This is an empty state, not a server error
                        showEmptyState("Your store is empty. Add your first product!");
                    }
                } else {
                    showError("Server responded with error: " + response.code() + ". Check your Firebase URL and Rules.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Product>> call, @NonNull Throwable t) {
                hideLoading();
                showError("Unable to connect to server. Ensure your Firebase URL in RetrofitClient is correct.");
                Log.e(TAG, "Network Error: " + t.getMessage());
            }
        });
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rvProducts.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void showData() {
        rvProducts.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
    }

    private void showEmptyState(String message) {
        rvProducts.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        tvError.setText(message);
        btnRetry.setText("Refresh");
    }

    private void showError(String message) {
        rvProducts.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        tvError.setText(message);
        btnRetry.setText("Retry");
    }
}