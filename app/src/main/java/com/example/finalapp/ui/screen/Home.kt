package com.example.finalapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align



//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.finalapp.data.entity.Movies
import com.example.finalapp.ui.viewmodel.HomeViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, homeViewModel: HomeViewModel){

    val movieList = homeViewModel.movieList.observeAsState(initial =  emptyList())
    val filteredMovies = homeViewModel.filteredMovieList.observeAsState(initial = movieList.value)
    val selectedCategory = homeViewModel.selectedCategory.observeAsState(initial = "All")
    val categories = homeViewModel.categoryList.observeAsState(initial = emptyList())
    val searchQuery = remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            AppTopBar(
                title = "MovieApp",
                onCartClick = { navController.navigate("cartPage") }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(5.dp))

            //Search Bar
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it
                                homeViewModel.filterMoviesByCategoryAndSearch(selectedCategory.value, it)},
                placeholder = { Text(
                    text = "Search Movies...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                ) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .border(2.dp, Color(0xFF4AA2D9), CircleShape)
                    .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(24.dp))
                    .padding(horizontal = 5.dp),
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search Icon",
                                tint = Color.Gray,
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(24.dp))


            //Category Buttons
            CategoryButtons(
                categories = listOf("All") + categories.value,
                selectedCategory = selectedCategory.value,
                onCategorySelected = { category ->
                    homeViewModel.setSelectedCategory(category)
                }
            )
            //Movie List
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(count = 2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(filteredMovies.value) { movie ->
                    MovieCard(movie = movie, navController = navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String, onCartClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        actions = {
            IconButton(onClick = onCartClick) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF034C8C ),
            titleContentColor = Color.White
        )
    )
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val colors = NavigationBarItemDefaults.colors(
        indicatorColor = Color.Transparent
    )
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            icon = {
                Box(
                    modifier = Modifier
                        .background(
                            if (currentRoute == "home") Color(0xFF2B96D9) else Color.Transparent,
                            shape = RoundedCornerShape(15.dp)
                        ).padding(8.dp)
                ) { Icon(
                        Icons.Default.Home,
                        contentDescription = "Home",
                        tint = if (currentRoute == "home") Color.White else Color.Gray) } },
            label = {
                Text(
                    "Home",
                    color = if (currentRoute == "home") Color(0xFF034C8C) else Color.Gray
                )
            },
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") },
            colors = colors
        )
        NavigationBarItem(
            icon = {Box(
                modifier = Modifier.background(
                        if (currentRoute == "cartPage") Color(0xFF2B96D9) else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    ).padding(8.dp)
            ) { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") }},
            label = { Text("Cart", color = if (currentRoute == "cartPage") Color(0xFF034C8C) else Color.Gray) },
            selected = currentRoute == "cartPage",
            onClick = { navController.navigate("cartPage") },
            colors = colors
        )

        NavigationBarItem(
            icon = {Box(
                modifier = Modifier.background(
                        if (currentRoute == "profile") Color(0xFF2B96D9) else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    ).padding(8.dp)
            ) { Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = "profile",
                    tint = if (currentRoute == "profile") Color.White else Color.Gray)
            } },
            label = { Text("Profile", color = if (currentRoute == "profile") Color(0xFF034C8C) else Color.Gray) },
            selected = currentRoute == "profile",
            onClick = { navController.navigate("profile") },
            colors = colors

        )
    }
}
@Composable
fun MovieCard(movie: Movies, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                val movieJson = Gson().toJson(movie)
                navController.navigate("movieDetail/$movieJson")
            },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
                )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                imageModel = "http://kasimadalan.pe.hu/movies/images/${movie.image}",
                contentDescription = movie.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )
            Text(
                text = movie.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Price: \$ ${movie.price} ",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CategoryButtons(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            Button(
                onClick = { onCategorySelected(if (selectedCategory == category) null else category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedCategory == category) Color(0xFF2B96D9) else Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = category,
                    color = if (selectedCategory == category) Color.White else Color.Black
                )
            }
        }
    }
}
