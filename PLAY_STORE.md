# Google Play Store Submission Preparation

This document contains the necessary information for submitting **Smart-Kirana** to the Google Play Store.

## App Metadata
- **App Name**: Smart-Kirana
- **Short Description**: Modern inventory and store management for local shopkeepers.
- **Full Description**: 
  Smart-Kirana is a premium, data-driven business management tool designed specifically for local Indian shopkeepers. 
  Features include:
  - Secure manager authentication.
  - Real-time inventory tracking with cloud synchronization.
  - Interactive dashboard with key business metrics.
  - Automated low-stock alerts and system notifications.
  - Responsive design for all mobile devices.
- **Category**: Business / Productivity
- **Tags**: Kirana, Retail, Inventory, Management, India

## Store Listing Assets (Required)
- **App Icon**: 512x512 PNG (Alpha transparent)
- **Feature Graphic**: 1024x500 JPG or 24-bit PNG
- **Screenshots**: At least 4 screenshots (Login, Dashboard, Inventory, Add Product).

## Permissions & Privacy
- **Required Permissions**: 
  - `android.permission.INTERNET` (Cloud sync)
  - `android.permission.ACCESS_NETWORK_STATE` (Connection checking)
  - `android.permission.POST_NOTIFICATIONS` (Low stock alerts)
- **Privacy Policy**: Required for submission. Focus on data safety and use of Firebase.

## Release Information
- **Package Name**: `com.example.apexplanettask1`
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Build Format**: Android App Bundle (.aab)

## Security & Data Safety
- **Data Encrypted in Transit**: Yes (HTTPS via Retrofit)
- **User Account Creation**: Yes (Firebase Auth)
- **Data Collection**: Collects account information (Email) and inventory data.
