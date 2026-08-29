package com.example.apexplanettask1;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }

    public static boolean isValidProduct(String name, String price, String stock) {
        if (TextUtils.isEmpty(name)) return false;
        try {
            double p = Double.parseDouble(price);
            int s = Integer.parseInt(stock);
            return p >= 0 && s >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}