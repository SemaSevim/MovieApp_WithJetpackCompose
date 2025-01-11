package com.example.finalapp.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalapp.data.entity.Movies
import com.example.finalapp.ui.viewmodel.CartPageViewModel
import com.example.finalapp.ui.viewmodel.HomeViewModel
import com.example.finalapp.ui.viewmodel.MovieDetailViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageTransitions(homeViewModel: HomeViewModel,
                    movieDetailViewModel: MovieDetailViewModel,
                    cartPageViewModel: CartPageViewModel
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home"){
            Home(navController = navController, homeViewModel= homeViewModel)
        }
        composable("movieDetail/{movieJson}",
            arguments = listOf(navArgument("movieJson"){type = NavType.StringType})
        ){
            val movieJson = it.arguments?.getString("movieJson")
            val movie = Gson().fromJson(movieJson, Movies::class.java)
            MovieDetailPage(movie = movie , movieDetailViewModel = movieDetailViewModel,cartPageViewModel = cartPageViewModel, onNavigateToCart = { navController.navigate("cartPage")}, navController= navController )
        }
        composable("cartPage"){
            CartPage(cartPageViewModel = cartPageViewModel, navController= navController)
        }
        composable("profile"){
            ProfilePage(navController = navController)
        }

    }

}