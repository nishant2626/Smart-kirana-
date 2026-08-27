package com.example.apexplanettask1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    private static final String TAG = "AddProductActivity";
    private TextInputEditText etProductName, etPrice, etStock, etCategory;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Views
        etProductName = findViewById(R.id.etProductName);
        etPrice = findViewById(R.id.etPrice);
        etStock = findViewById(R.id.etStock);
        etCategory = findViewById(R.id.etCategory);
        Button btnSaveProduct = findViewById(R.id.btnSaveProduct);
        Button btnCancel = findViewById(R.id.btnCancel);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving product to cloud...");
        progressDialog.setCancelable(false);

        // Save Button Click
        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });

        // Cancel Button Click
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        // Toolbar back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveProduct() {
        if (etProductName.getText() == null || etPrice.getText() == null || 
            etStock.getText() == null || etCategory.getText() == null) return;

        String name = etProductName.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String stockStr = etStock.getText().toString().trim();
        String category = etCategory.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        int stock;
        try {
            price = Double.parseDouble(priceStr);
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price or stock format", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product(name, price, stock, category, "Smart Kirana Product");

        // Task 3: Backend POST Integration
        progressDialog.show();
        Log.d(TAG, "Starting POST request to save product: " + name);
        
        RetrofitClient.getApiService().addProduct(product).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Product saved successfully. Firebase ID: " + response.body().get("name"));
                    Toast.makeText(AddProductActivity.this, "Product saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e(TAG, "Server error code: " + response.code());
                    Toast.makeText(AddProductActivity.this, "Server error. Could not save.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "Network failure: " + t.getMessage());
                Toast.makeText(AddProductActivity.this, "Network error! Check your connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}