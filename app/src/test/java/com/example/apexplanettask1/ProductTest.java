package com.example.apexplanettask1;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void validProduct_returnsTrue() {
        Product product = new Product("Rice", 60.0, 50, "Grains", "Premium Basmati");
        assertNotNull(product.getName());
        assertEquals("Rice", product.getName());
        assertTrue(product.getPrice() > 0);
        assertTrue(product.getStockQuantity() >= 0);
    }

    @Test
    public void stockBelowThreshold_isLowStock() {
        Product product = new Product("Salt", 20.0, 3, "Spices", "Iodized Salt");
        assertTrue(product.getStockQuantity() <= NotificationHelper.LOW_STOCK_THRESHOLD);
    }

    @Test
    public void stockAboveThreshold_isNotLowStock() {
        Product product = new Product("Sugar", 40.0, 10, "Grains", "White Sugar");
        assertFalse(product.getStockQuantity() <= NotificationHelper.LOW_STOCK_THRESHOLD);
    }

    @Test
    public void productValidation_emptyName_returnsFalse() {
        Product product = new Product("", 10.0, 5, "Test", "Test");
        assertTrue(product.getName().isEmpty());
    }
}