# MovieApp 📽️

The Movie App is a modern Android application that allows users to explore and purchase a wide selection of movies. Designed with a user-friendly interface and robust features, you can view movie details, add items to your cart, and effortlessly complete your purchases.

# Technologies Used

- **Jetpack Compose:** A modern and efficient way to build declarative UIs.
- **MVVM (Model-View-ViewModel):** Ensures scalability, maintainability, and testability of the application.
- **Material Design:** Design standards for creating user-friendly and modern interfaces.
- **Retrofit:** For handling API calls and parsing JSON responses.
- **Glide:** Used for fast and efficient loading of movie posters.
- **Hilt:** Simplifies dependency injection and improves app architecture.
- **Navigation:** Used for seamless in-app page transitions.
- **LiveData and Coroutines:** Efficiently manage data streams and asynchronous tasks.

# Features

## Home Page

- Displays a comprehensive list of movies fetched from the following web service: Get All Movies.
- Includes search functionality to quickly find movies by title.
- Filters movies by categories for easier browsing.
- Allows users to select a movie to view its details.

## Movie Detail Page

- Displays detailed information about the selected movie.
- Provides the ability to adjust the quantity of the selected movie.
- Users can add movies to their cart using the "Add to Cart" button.

## Cart Page

- Lists all the movies added to the cart.
- Shows a detailed summary of items, including their total cost.
- Calculates and displays a delivery fee if there are items in the cart.
- Allows users to remove movies from the cart.

## Profile Page

- Displays user membership information and profile details.


# Screens 

<table>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/8389e0da-601d-45c6-aa99-af7378caf815" alt="Home Page" width="300"/>
      <p><strong>Home Page</strong></p>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/1865fc08-9712-484e-8249-a8b3ae04daa9" alt="Movie Detail Page" width="300"/>
      <p><strong>Movie Detail Page</strong></p>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/15d157b0-13b4-403e-9dfd-53d0c35e9053" alt="Cart Page" width="300"/>
      <p><strong>Cart Page</strong></p>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/5020e235-8aeb-469a-bb36-1d4d2ca1d092" alt="Profile Page" width="300"/>
      <p><strong>Profile Page</strong></p>
    </td>
  </tr>
</table>


# API Endpoints

- **Get All Movies:** http://kasimadalan.pe.hu/movies/getAllMovies.php
- **Get Movies in Cart:** http://kasimadalan.pe.hu/movies/getMovieCart.php
- **Add Movie to Cart:** http://kasimadalan.pe.hu/movies/insertMovie.php
- **Delete Movie from Cart:** http://kasimadalan.pe.hu/movies/deleteMovie.php
- **Fetch Movie Images:** http://kasimadalan.pe.hu/movies/images/dune.png


# Getting Started

## Prerequisites

- Android Studio must be installed on your computer.
- Minimum SDK version: 24
- Recommended version: Android 11 or higher.

## Installation

- Clone this repository:
- git clone ,(https://github.com/SemaSevim/MovieApp_WithJetpackCompose.git)
- Open the project in Android Studio.
- Sync the project to download dependencies.
- Run the project on an emulator or a physical device.




