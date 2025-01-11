package com.example.finalapp.ui.screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalapp.ui.viewmodel.CartPageViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(cartPageViewModel: CartPageViewModel, navController: NavController) {
    val cartMovies = cartPageViewModel.cartMovies.observeAsState(initial = emptyList())
    val userName = "SemaSevim"

    LaunchedEffect(Unit) {
        cartPageViewModel.fetchCartMovies(userName)
    }

    val mergedCartMovies = cartMovies.value.groupBy { it.name }.map { entry ->
        entry.value.first().copy(orderAmount = entry.value.sumOf { it.orderAmount })
    }
    val totalPrice = mergedCartMovies.sumOf { it.price * it.orderAmount }
    val deliveryFee = if (mergedCartMovies.isNotEmpty()) 30 else 0

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("My Cart", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )}
                    },
                    actions = {
                        IconButton(onClick = {navController.navigate("home")}) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Cart",
                                tint = Color.White
                            )
                        }
        }, colors =  TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF034C8C))
            )},
        bottomBar = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Delivery Fee", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "\$ $deliveryFee", style = MaterialTheme.typography.bodyMedium)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Price:", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(text = "$ ${totalPrice + deliveryFee}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF034C8C))
                ) {
                    Text(text = "CONFIRM", color = Color.White)
                }
                BottomNavigationBar(navController)
            }
        }
    )
     { paddingValues ->

            if (mergedCartMovies.isNotEmpty()) {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(mergedCartMovies) { movie ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(disabledElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    GlideImage(
                                        imageModel = "http://kasimadalan.pe.hu/movies/images/${movie.image}",
                                        contentDescription = movie.name,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Fit
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = movie.name,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Price: \$ ${movie.price}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Text(
                                            text = "Quantity: ${movie.orderAmount}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "\$ ${movie.price * movie.orderAmount}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    IconButton(onClick = {


                                        val relatedCartIds = cartPageViewModel.cartMovies.value
                                            ?.filter { it.name == movie.name } // Find all movies with the same name
                                            ?.map { it.cartId } // Get cartId values these movies
                                            .orEmpty() // If null returns empty list
                                        // Start deletion in ViewModel
                                        cartPageViewModel.deleteMovie(relatedCartIds, userName)

                                    }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Sil",
                                            tint = Color.Black
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            } else{
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Empty Cart",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }
