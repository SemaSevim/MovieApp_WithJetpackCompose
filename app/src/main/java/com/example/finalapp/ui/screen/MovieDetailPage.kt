package com.example.finalapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalapp.R
import com.example.finalapp.data.entity.Movies
import com.example.finalapp.ui.viewmodel.CartPageViewModel
import com.example.finalapp.ui.viewmodel.MovieDetailViewModel
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalMaterial3Api
@Composable
fun MovieDetailPage(movie: Movies ,movieDetailViewModel: MovieDetailViewModel, cartPageViewModel: CartPageViewModel, onNavigateToCart: () -> Unit, navController: NavController ) {

    val orderAmount = remember { mutableStateOf(1) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Details", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {navController.navigate("cartPage")}) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color.White
                        )
                    }

                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF034C8C))
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
            Card( modifier = Modifier
                .width(280.dp)
                .height(300.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)  ) {

            GlideImage(
                imageModel = "http://kasimadalan.pe.hu/movies/images/${movie.image}",
                contentDescription = movie.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )}
            }
            Spacer(modifier = Modifier.height(16.dp))
                //Movie Details
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
            Text(
                text = movie.name,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF034C8C),
            )
            Text(
                text = "Directed by ${movie.director}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "\$ ${movie.price} ",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                            color = Color(0xFF0367A6)
                        )
                        Text(
                            text = "⭐⭐⭐ ${movie.rating}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFFFFA000))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Category: ${movie.category}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF004D40)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = movie.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    //Decrease Button
                    IconButton(
                        onClick = { if (orderAmount.value > 1) orderAmount.value-- },
                        modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF034C8C)),
                    ) { Icon(painter = painterResource(R.drawable.remove), contentDescription = "Decrease", tint = Color.White) }

                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = orderAmount.value.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    //Increase Button
                    IconButton(
                        onClick = { orderAmount.value++ },
                        modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF034C8C)),
                        ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase", tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
                // Add to Cart Button
                Button(
                    onClick = {
                        movieDetailViewModel.insertMovie(movie, orderAmount.value, "SemaSevim")
                        cartPageViewModel.fetchCartMovies("SemaSevim")
                        onNavigateToCart()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF034C8C)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add to cart", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }

