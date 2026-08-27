# ApexPlanet-Task-1-Android: My Kirana Store

## Project Description
"My Kirana Store" is a simple Android application developed as part of **Task 1 of the 45-Day Android App Development Internship at ApexPlanet Software Pvt. Ltd.** The app serves as a basic digital storefront for a local grocery shop, demonstrating fundamental Android development concepts using Java.

## Internship Details
- **Company:** ApexPlanet Software Pvt. Ltd.
- **Task:** Task 1 — Android Development Fundamentals

## Technologies Used
- **Language:** Java
- **UI Design:** XML (Layouts)
- **Build Tool:** Gradle
- **IDE:** Android Studio

## Features
- **Home Screen:** A welcoming landing page with navigation options.
- **Product List:** Displays a variety of grocery items with their prices.
- **About Store:** Provides details about the store, including contact information.
- **Dynamic Welcome:** Uses a Fragment to display a welcome message on the Home Screen.

## Android Concepts Demonstrated
- **Activities:** Multiple screens (Main, Products, About) managed via Activity classes.
- **Intents:** Navigation between different activities.
- **Fragments:** Modular UI component (`WelcomeFragment`) used within the MainActivity.
- **Layouts:** Use of `ConstraintLayout` and `LinearLayout` for responsive design.
- **UI Components:** `TextView`, `Button`, `ImageView`, and `ScrollView`.
- **Event Handling:** Button click listeners to trigger navigation.

## How to Run the Project
1. Open the project in **Android Studio**.
2. Ensure you have the **Android SDK** and an **Emulator** (or physical device) configured.
3. Sync the project with Gradle files.
4. Click the **'Run'** button (green play icon) in Android Studio.
5. The app will launch on your device/emulator.

## Project Structure
- `MainActivity.java`: The entry point of the app, hosting the Welcome Fragment and navigation buttons.
- `ProductsActivity.java`: Displays the list of products.
- `AboutActivity.java`: Displays store information.
- `WelcomeFragment.java`: A simple fragment displayed on the home screen.
- `res/layout/`: Contains XML layout files for all activities and fragments.

## Future Improvements
- Implement a `RecyclerView` for a more dynamic product list.
- Add a "Cart" functionality to simulate shopping.
- Integrate a database (SQLite/Room) to store product data locally.
