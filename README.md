# Smart-Kirana

## Overview
**Smart-Kirana** is a professional, cloud-integrated Android application designed to empower local Indian shopkeepers. It provides a digital command center for managing inventory, tracking stock levels in real-time, and receiving automated alerts for low-stock items. Developed as the final project for the **ApexPlanet Software Pvt. Ltd. Android App Development Internship**, this app bridges the gap between traditional retail and modern digital efficiency.

## Features
- **Secure Authentication**: Manager-only access using Firebase Authentication (Login & Registration).
- **Executive Dashboard**: Real-time visualization of store health (Total Items, Low Stock counts).
- **Live Inventory**: Dynamic product listing with search functionality and real-time cloud sync.
- **Automated Alerts**: System-level Android notifications triggered when stock falls below 5 units.
- **Modern UI/UX**: Premium Material 3 design system with responsive layouts for all device sizes.
- **Offline Reliability**: Graceful error handling and a "Demo Mode" fallback for connectivity issues.

## Technology Stack
- **Language**: Java
- **Networking**: Retrofit 2, OkHttp 3 (with Logging Interceptor), Gson
- **Backend**: Firebase Authentication, Firebase Realtime Database (via REST API)
- **UI Architecture**: Material Design 3, Fragments, RecyclerView
- **Testing**: JUnit 4, Espresso
- **CI/CD**: GitHub Actions

## Architecture
The project utilizes a **Layered Architecture**:
- **Presentation Layer**: Activities/Fragments for UI logic.
- **Domain Layer**: `Product` model and `ValidationUtils` for business rules.
- **Data Layer**: `RetrofitClient` and `ApiService` for external communication.
- **Helper Layer**: `NotificationHelper` for OS integration.

## Project Structure
- `com.example.apexplanettask1`: Root package containing all Java logic.
- `res/layout`: XML definitions for Activities, Fragments, and List Items.
- `res/values`: Design tokens including Colors, Strings, and Dimensions.
- `androidTest` & `test`: Comprehensive testing suites.

## Backend & Authentication Setup
1. **Firebase Integration**: Requires `google-services.json` in the `app/` folder.
2. **REST API**: Configure the `BASE_URL` in `RetrofitClient.java` to point to your Firebase RTDB.
3. **Auth**: Enable **Email/Password** sign-in method in the Firebase Console.

## Running the Project
1. Clone the repo: `git clone https://github.com/nishant2626/Smart-kirana-.git`
2. Open in **Android Studio**.
3. Sync Gradle and run on an emulator or physical device.
4. **Demo Access**: Use `admin@store.com` / `admin123` for instant entry.

## Testing
- **Automated**: Run `./gradlew test` and `./gradlew connectedAndroidTest`.
- **Manual**: Refer to `TESTING.md` for the full QA checklist.

## Release
- **Target SDK**: 34 (Android 14)
- **Build**: Generate signed APK/AAB via the `Build` menu in Android Studio.

## Future Improvements
- Barcode scanning integration.
- PDF generation for sales reports.
- Multi-user roles (Owner vs. Staff).

## Author
**Nishant**
GitHub: [nishant2626](https://github.com/nishant2626)
Project: [Smart-Kirana](https://github.com/nishant2626/Smart-kirana-)
