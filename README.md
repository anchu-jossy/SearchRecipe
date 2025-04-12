# 🍲 Search Recipe - Simple Multi-Module Android Application

Welcome to the **Search Recipe** app – a clean, modular Android project designed to demonstrate best practices in **unit testing**, **Jetpack Compose UI**, **Room**, **MVVM**, and **Dagger Hilt**.

This repository is ideal for learning or teaching modern Android development with a strong emphasis on testing.

---

## 🧪 Introduction to Unit Testing

Unit testing is a critical part of Android development. It helps ensure that your business logic works as expected, improves code quality, and prevents regressions during refactoring.

This project walks you through:
- Writing unit tests for different layers of your app.
- Structuring testable code using MVVM and dependency injection.
- Verifying database operations.
- Testing UI components in Jetpack Compose.

---

## ⚙️ Setting Up Your Environment

To run and write tests in this project, make sure you have the following set up:

### ✅ Dependencies:
- Kotlin
- Jetpack Compose
- Room
- Dagger Hilt
- JUnit4 / JUnit5
- Mockito / Mockk
- AndroidX Test libraries
- Espresso (for UI testing)

> Add the following in your module's `build.gradle` (if not already added):

```groovy
testImplementation 'junit:junit:4.13.2'
testImplementation "org.mockito:mockito-core:4.8.0"
androidTestImplementation 'androidx.test.ext:junit:1.1.5'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
