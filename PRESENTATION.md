# Smart-Kirana: Final Project Presentation

## 1. Title
**Smart-Kirana**
*Modern Store Management for Local Shopkeepers*

## 2. Problem Statement
Local Indian shopkeepers (Kirana stores) often struggle with manual inventory tracking, lack of real-time sales insights, and late awareness of out-of-stock items, leading to lost revenue and customer dissatisfaction.

## 3. Project Objective
To build a premium, simple-to-use Android application that digitizes store management, providing secure access, real-time inventory synchronization, and automated stock alerts.

## 4. Solution
A Java-based Android mobile solution integrated with Firebase Cloud services, offering a professional dashboard, dynamic inventory lists, and system-level notifications.

## 5. Internship Roadmap
### Task 1 — Android Fundamentals
- Established the core app structure.
- Implemented Activity-based navigation and Fragment-based modular UI.

### Task 2 — UI/UX Implementation
- Applied Material 3 design principles.
- Created a modern, executive dashboard and responsive layouts for all screen sizes.

### Task 3 — Backend/API Integration
- Integrated Firebase Realtime Database.
- Implemented REST API communication using Retrofit for cloud data synchronization.

### Task 4 — Advanced Features & Testing
- Integrated Firebase Authentication.
- Developed a "Low Stock Alert" system with Android notifications.
- Implemented Unit and Integration testing suites.

## 6. Key Features
- **Cloud Sync**: Instant inventory updates across devices.
- **Smart Alerts**: Real-time notifications for items with stock ≤ 5 units.
- **Secure Access**: Email/Password authentication for store managers.
- **Searchable Inventory**: Rapid item lookup with visual status indicators.

## 7. Technology Stack
- **Languages**: Java
- **Networking**: Retrofit, OkHttp, Gson
- **Cloud**: Firebase Auth, Firebase Realtime DB
- **UI**: XML, Material Components

## 8. Architecture
- **Layered Pattern**: UI (Activities/Fragments) → Service (Retrofit) → Cloud (Firebase).
- **Refactored Logic**: Centralized validation and helper classes for maintainability.

## 9. Challenges & Solutions
- **Challenge**: App crashing without Firebase config during development.
- **Solution**: Implemented a "Startup Safety Shield" with dummy keys and a stable "Demo Mode" fallback.
- **Challenge**: UI inconsistencies across different Android versions.
- **Solution**: Migrated to a stable SDK 34 baseline with responsive ConstraintLayouts.

## 10. Testing Result
- **Unit Tests**: 100% pass on core business logic.
- **Integration Tests**: Verified Login and Navigation flows.
- **Manual Testing**: End-to-end verified for all scenarios including offline states.

## 11. Final Demo
- *Walkthrough of Login/Register flow.*
- *Dashboard overview with live metrics.*
- *Adding a low-stock product and receiving a system notification.*

## 12. GitHub Repository
[https://github.com/nishant2626/Smart-kirana-](https://github.com/nishant2626/Smart-kirana-)

## 13. Thank You
Special thanks to **ApexPlanet Software Pvt. Ltd.** for this internship opportunity.
