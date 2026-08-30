package com.example.apexplanettask1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
                        showEmptyState(getString(R.string.no_products));
                    }
                } else {
                    showError(getString(R.string.auth_failed) + ": " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Product>> call, @NonNull Throwable t) {
                hideLoading();
                showError(getString(R.string.retry) + ": " + t.getLocalizedMessage());
                Log.e(TAG, "Network Error: " + t.getMessage());
            }
        });
    }

    private void showLoading() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (rvProducts != null) rvProducts.setVisibility(View.GONE);
        if (errorLayout != null) errorLayout.setVisibility(View.GONE);
    }

    private void hideLoading() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
    }

    private void showData() {
        if (rvProducts != null) rvProducts.setVisibility(View.VISIBLE);
        if (errorLayout != null) errorLayout.setVisibility(View.GONE);
    }

    private void showEmptyState(String message) {
        if (rvProducts != null) rvProducts.setVisibility(View.GONE);
        if (errorLayout != null) errorLayout.setVisibility(View.VISIBLE);
        if (tvError != null) tvError.setText(message);
        if (btnRetry != null) btnRetry.setText(getString(R.string.retry));
    }

    private void showError(String message) {
        if (rvProducts != null) rvProducts.setVisibility(View.GONE);
        if (errorLayout != null) errorLayout.setVisibility(View.VISIBLE);
        if (tvError != null) tvError.setText(message);
        if (btnRetry != null) btnRetry.setText(getString(R.string.retry));
    }
}