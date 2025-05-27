# KMP RickAndMortyApp

KMP RickAndMortyApp is a cross-platform mobile application built with **Kotlin Multiplatform** and **Compose Multiplatform**. It allows users to explore characters, episodes, and locations from the **Rick and Morty** universe in a clean and responsive UI — all with a shared codebase for Android and iOS.

## ✨ Features

- 🔁 **Pagination Support**: Fetches Rick and Morty characters in a paginated list on the main screen for optimal performance.
- ⭐ **Favorites**: Users can mark characters as favorites and view them in a dedicated **Favorites** tab. These are stored locally using **Room database** on Android.
- 🔐 **Secure Preferences**: Lightweight and sensitive data is stored securely using **Encrypted SharedPreferences** on Android and **Keychain** on iOS.
- 🔍 **Character Details**: Displays detailed information about selected characters including species, status, origin, and location.
- 📱 **Cross-Platform**: Developed using Kotlin Multiplatform for shared business logic across Android and iOS.

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest)
- Xcode (for iOS)
- Kotlin 1.9+
- CocoaPods (for iOS dependencies)
- Kotlin Multiplatform plugin enabled

### Run the Project

#### Android

1. Open the project in Android Studio.
2. Select the `composeApp` module.
3. Choose an emulator or device and click **Run**.

#### iOS

1. Navigate to the `iosApp/` directory and run `pod install`.
2. Open `iosApp.xcworkspace` in Xcode.
3. Choose a simulator or device and run the app.

## 🧠 Architecture & Tech Stack

This app is built with a modern, multiplatform architecture and technologies:

- **Kotlin Multiplatform** – shared business logic
- **Compose Multiplatform** – UI framework for Android and iOS
- **Ktor** – HTTP client for networking
- **Kotlinx Serialization** – for parsing JSON data
- **Coroutines & Flow** – for managing asynchronous operations
- **Koin** – dependency injection
- **Room** – local database for storing favorites on Android
- **Encrypted SharedPreferences / Keychain** – for securely storing preferences

## 📦 Dependencies

- [Ktor](https://ktor.io/)
- [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Koin](https://insert-koin.io/)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)

## 📸 Screenshots

## Android

<p align="center">
  <img src="https://github.com/user-attachments/assets/41a68b36-eabe-43f3-8a57-c86416a7067c" alt="Home Screen" width="30%" />
  <img src="https://github.com/user-attachments/assets/b4897eec-7504-4e41-807c-7092c5894395" alt="Favorites Screen" width="30%" />
  <img src="https://github.com/user-attachments/assets/3433c3ee-6920-4f70-aa15-9fc78776f9c9" alt="Detail Screen" width="30%" />
</p>

## iOS

<p align="center">
  <img src="https://github.com/user-attachments/assets/1abe999e-28ab-48ab-b67f-e48177ad77f6" alt="Home Screen" width="30%" />
  <img src="https://github.com/user-attachments/assets/415ac0c7-c45e-4f2c-891f-8c02deb32d32" alt="Favorites Screen" width="30%" />
  <img src="https://github.com/user-attachments/assets/f56849ba-13ce-4f93-abab-f6dce740df54" alt="Detail Screen" width="30%" />
</p>

