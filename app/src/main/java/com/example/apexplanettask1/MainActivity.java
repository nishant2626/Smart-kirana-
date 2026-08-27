package com.example.apexplanettask1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load WelcomeFragment into the container
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, welcomeFragment);
        transaction.commit();

        // Initialize Buttons
        Button btnViewProducts = findViewById(R.id.btnViewProducts);
        Button btnAboutStore = findViewById(R.id.btnAboutStore);

        // Handle View Products button click
        btnViewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ProductsActivity using Intent
                Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
                startActivity(intent);
            }
        });

        // Handle About Store button click
        btnAboutStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AboutActivity using Intent
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}