package com.example.apexplanettask1;

import com.google.gson.annotations.SerializedName;

public class Product {
    private String id;
    private String name;
    private double price;
    private int stockQuantity;
    private String category;
    private String description;

    public Product() {
        // Required for Firebase/Gson
    }

    public Product(String name, double price, int stockQuantity, String category, String description) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.description = description;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}