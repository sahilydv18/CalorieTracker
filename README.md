# Calorie Tracker App

## Overview

**Calorie Tracker** is an Android application that helps users track their daily nutritional intake, including calories, protein, carbs, and fat. The app offers a user-friendly interface with features like API integration for fetching nutritional data, daily reset functionality, and personalized goal setting.

## Features

- **Nutritional Data Fetching**: Automatically fetch nutritional data for ingredients using an integrated API—no need for manual entry.
- **User Onboarding**: A smooth onboarding experience with Lottie animations to guide users through setting up their profile and goals.
- **Daily Reset**: Uses WorkManager to reset daily intake values at midnight, ensuring a fresh start each day.
- **Data Storage**: Stores user details and nutritional goals using DataStore preferences.
- **Offline Data Management**: Room database stores meal and ingredient information locally for offline use.
- **Modern Tech Stack**: Built with Jetpack Compose and MVVM architecture, featuring dependency injection with Dagger Hilt for maintainability and scalability.

## Tech Stack

- **Kotlin**: Primary programming language.
- **Jetpack Compose**: For building the UI.
- **MVVM Architecture**: Ensures a clean separation of concerns.
- **Dagger Hilt**: Dependency injection for managing app components.
- **Retrofit**: For API integration to fetch nutritional data.
- **Gson Converter**: Converts JSON data into Kotlin objects.
- **Room Database**: Manages local data storage.
- **WorkManager**: Schedules daily reset tasks.
- **DataStore Preferences**: For storing user preferences and goals.
- **Lottie Animations**: Enhances the onboarding experience.

## Installation
- Clone the repository
  ```
  git clone https://github.com/sahilydv18/CalorieTracker.git
  cd CalorieTracker
  ```
- Open the project in Android Studio
- Add API key:
  - Open the `local.properties` file in the project root directory.
  - Add the following line to include your API key:
      ```
      MY_API_KEY = YOUR_API_KEY
      ```
  - Replace `YOUR_API_KEY` with the actual API key provided by the API service.
- Build and run the app on an emulator or physical device.

## API Documentation

### API Overview

The Calorie Tracker app integrates with the [**Calorie Ninjas API**](https://calorieninjas.com/api) to fetch nutritional data for ingredients. This feature allows users to search for ingredients by name and retrieve detailed nutritional information without manual input.

### Endpoints

#### **Get Nutrition Information**

- **Endpoint**: `/v1/nutrition`
- **Method**: `GET`
- **Parameters**:
  - `query` (required): A string containing food or drink items. You can specify quantities directly in the query (e.g., "3 tomatoes" or "1lb beef brisket"). If no quantity is provided, the API assumes a default quantity of 100 grams. The query cannot exceed 1500 characters.
- **Headers**:
  - `X-Api-Key` (required): Your API Key associated with the account.

- **Example Request**:
  ```
  GET https://api.calorieninjas.com/v1/nutrition?query=milk
  X-Api-Key: YOUR_API_KEY
  ```
  - Example Response:
  ```
  {
    "items": [
      {
        "name": "milk",
        "calories": 51.3,
        "serving_size_g": 100,
        "fat_total_g": 1.9,
        "fat_saturated_g": 1.2,
        "protein_g": 3.5,
        "sodium_mg": 52,
        "potassium_mg": 100,
        "cholesterol_mg": 8,
        "carbohydrates_total_g": 4.9,
        "fiber_g": 0,
        "sugar_g": 0
      }
    ]
  }
  ```
  
## Contributing
Contributions are welcome! Please fork the repository and create a pull request for any enhancements, bug fixes, or new features.

## License
This project is licensed under the MIT License—see the [LICENSE](LICENSE.md) file for details.

## Contact
For any queries or suggestions, feel free to reach out:
- [Email](mailto:ydvvsahil09@gmail.com)
- [LinkedIn](https://www.linkedin.com/in/ydvsahil18/)
- [X(Twitter)](https://x.com/sahil_yadvv)