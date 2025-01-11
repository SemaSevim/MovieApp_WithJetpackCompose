package com.example.finalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.finalapp.ui.screen.PageTransitions
import com.example.finalapp.ui.theme.FinalAppTheme
import com.example.finalapp.ui.viewmodel.CartPageViewModel
import com.example.finalapp.ui.viewmodel.HomeViewModel
import com.example.finalapp.ui.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val homeViewModel:HomeViewModel by viewModels()
    val movieDetailViewModel:MovieDetailViewModel by viewModels()
    val cartPageViewModel:CartPageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalAppTheme {
                PageTransitions(
                    homeViewModel = homeViewModel,
                    movieDetailViewModel = movieDetailViewModel,
                    cartPageViewModel = cartPageViewModel)
                }
        }
    }
}

