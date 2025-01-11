package com.example.finalapp.data.datasource

import android.util.Log
import com.example.finalapp.data.entity.CartMovie
import com.example.finalapp.data.entity.CartResponse
import com.example.finalapp.data.entity.Movies
import com.example.finalapp.retrofit.MoviesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesDataSource(var moviesDao: MoviesDao) {

    suspend fun getAllMovies() : List<Movies> = withContext(Dispatchers.IO){
        return@withContext moviesDao.getAllMovies().movies
    }

    suspend fun insertMovie(movie: Movies, orderAmount: Int , userName: String): CartResponse =
        withContext(Dispatchers.IO){
            Log.d("AddToCart", "Adding movie: ${movie.name}, Amount: $orderAmount, User: $userName")
            return@withContext moviesDao.insertMovie(
                name = movie.name,
                image = movie.image,
                price = movie.price,
                category = movie.category,
                rating = movie.rating,
                year = movie.year,
                director = movie.director,
                description = movie.description,
                orderAmount = orderAmount,
                userName = userName
            )
        }
    suspend fun getMovieCart(userName: String): List<CartMovie> = withContext(Dispatchers.IO){
        val response = moviesDao.getMovieCart(userName)
        Log.d("MoviesRepository", "API Responsee: $response")
        return@withContext moviesDao.getMovieCart(userName).movie_cart
    }

    suspend fun deleteMovie(cartId: Int, userName: String): CartResponse = withContext(Dispatchers.IO){
        return@withContext moviesDao.deleteMovie(cartId, userName)
    }

}