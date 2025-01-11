package com.example.finalapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalapp.data.entity.Movies
import com.example.finalapp.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    fun insertMovie(movie: Movies, orderAmount: Int, userName: String){
        viewModelScope.launch {
            try {
                var response = moviesRepository.insertMovie(movie, orderAmount, userName)
                if (response.success == 1){
                    Log.d("MovieDetailViewModel", "Added to cart successfully" )
                } else{
                    Log.e("MovieDetailViewModel", "Error adding to cart:${response.message} ")
                }
            }catch (e:Exception){
                Log.e("MovieDetailViewModel", "Exception: ${e.message}")
            }
        }
    }
}