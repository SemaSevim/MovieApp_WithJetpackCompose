package com.example.finalapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalapp.data.entity.CartMovie
import com.example.finalapp.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartPageViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {
    val cartMovieList = MutableLiveData<List<CartMovie>>()
    val cartMovies: LiveData<List<CartMovie>> get() = cartMovieList

    fun fetchCartMovies(userName: String) {
        viewModelScope.launch {
            try {
                val movies = moviesRepository.getMovieCart(userName)
                //List Control from API
                if (movies == null) {
                    Log.e("CartPageViewModel", "Received null response from API")
                    cartMovieList.postValue(emptyList())
                    return@launch
                }
                if (movies.isEmpty()) {
                    Log.d("CartPageViewModel", "No movies found for user: $userName")
                    cartMovieList.postValue(emptyList())
                    return@launch
                }
                Log.d("CartPageViewModel", "Fetched Movies mn: $movies")

                /// Group movies by cartId
                val groupMovies = movies.groupBy { it.cartId }
                    .mapNotNull { entry ->
                        if (entry.value.isNullOrEmpty()) return@mapNotNull null
                        val totalAmount = entry.value.sumOf { it.orderAmount }
                        entry.value.first().copy(orderAmount = totalAmount)
                    } ?: emptyList()
                cartMovieList.postValue(groupMovies)
            } catch (e: Exception) {
                Log.e("CartPageViewModel", "Error fetching cart movies: ${e.message}")

            }

        }
    }
    fun deleteMovie(cartIds: List<Int>, userName: String) {
        viewModelScope.launch {
            try {
                val currentMovies = cartMovieList.value.orEmpty().toMutableList()

                for (cartId in cartIds) {
                    val response = moviesRepository.deleteMovie(cartId, userName)
                    if (response.success == 1) {
                        // Remove deleted item from current list
                        currentMovies.removeAll { it.cartId == cartId }
                    } else {
                        Log.e("CartPageViewModel", "Error deleting cartId $cartId: ${response.message}")
                        return@launch
                    }
                }
                // Check and post updated list
                if (currentMovies.isEmpty()) {
                    Log.d("CartPageViewModel", "Cart is now empty")
                    cartMovieList.postValue(emptyList()) // Update UI empty cart
                } else {
                    Log.d("CartPageViewModel", "Cart updated after deletion")
                    cartMovieList.postValue(currentMovies) // Update UI with updated list
                }
            } catch (e: Exception) {
                Log.e("CartPageViewModel", "Exception during delete: ${e.message}")
            }
        }
    }
}