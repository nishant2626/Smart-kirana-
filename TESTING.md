# Smart Kirana — Testing Report (Task 4)

## 1. Testing Overview
This document summarizes the automated and manual testing performed for **Task 4: Advanced Features and Testing** of the ApexPlanet Internship.

## 2. Automated Testing

### Unit Tests (`ProductTest.java`)
- **`validProduct_returnsTrue`**: Verifies that a correctly initialized Product object holds expected values. (PASSED)
- **`stockBelowThreshold_isLowStock`**: Confirms that stock <= 5 is correctly identified as low stock. (PASSED)
- **`stockAboveThreshold_isNotLowStock`**: Confirms that stock > 5 is not flagged as low stock. (PASSED)
- **`productValidation_emptyName_returnsFalse`**: Verifies handling of empty product names. (PASSED)

### Integration/UI Tests (`NavigationTest.java`)
- **`loginScreen_isDisplayed`**: Uses Espresso to verify that the Login screen and its components (Email, Password, Login Button) are visible upon app launch. (PASSED)

## 3. Manual Test Cases

| Feature | Scenario | Expected Result | Status |
| :--- | :--- | :--- | :--- |
| **Authentication** | Empty Login | Show error "Email is required" | PASSED |
| **Authentication** | Valid Login | Navigate to Dashboard | PASSED |
| **Authentication** | Logout | Return to Login screen, session cleared | PASSED |
| **Notifications** | Stock <= 5 | Android system notification appears | PASSED |
| **Notifications** | Stock > 5 | No notification sent | PASSED |
| **Permissions** | Android 13+ | System prompts for notification permission | PASSED |
| **Inventory** | Low Stock UI | Text color changes to red for low-stock items | PASSED |

## 4. Bugs Found & Fixed
- **Bug**: `MainActivity` crashed when database was empty.
  - **Fix**: Added null checks for the Firebase response body.
- **Bug**: Low-stock alert was hardcoded in multiple places.
  - **Fix**: Refactored to a central `LOW_STOCK_THRESHOLD` constant in `NotificationHelper`.
- **Bug**: Build error due to `compileSdk` mismatch with Activity 1.13.0.
  - **Fix**: Updated `compileSdk` and `targetSdk` to 36 in `build.gradle.kts`.

## 5. Performance Improvements
- **Optimized UI Updates**: Metrics on the dashboard are now refreshed only when returning to the screen (`onResume`).
- **Efficient Logging**: Integrated `HttpLoggingInterceptor` for debugging without leaking data in production.

## 6. Final Test Result
The application successfully integrates Tasks 1, 2, 3, and 4. All core features—Authentication, Inventory Management, Backend Integration, and Notifications—are functional and verified through testing.
