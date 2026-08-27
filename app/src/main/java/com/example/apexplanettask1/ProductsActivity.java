package com.example.apexplanettask1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        
        // Setting title for the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Product List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}