package com.example.finalapp.data.repo

import android.util.Log
import com.example.finalapp.data.datasource.MoviesDataSource
import com.example.finalapp.data.entity.CartMovie
import com.example.finalapp.data.entity.CartResponse
import com.example.finalapp.data.entity.Movies

class MoviesRepository(var moviesDataSource: MoviesDataSource) {
    suspend fun getAllMovies(): List<Movies> {
        val movies =  moviesDataSource.getAllMovies()
        Log.d("MoviesRepository", "Movies: $movies")
        return movies
    }

    suspend fun insertMovie(
        movie: Movies,
        orderAmount: Int,
        userName: String
    ) : CartResponse{
        return moviesDataSource.insertMovie(movie, orderAmount ,userName)
    }

    suspend fun getMovieCart(userName: String): List<CartMovie> {
       // Log.d("MoviesRepository", "Calling getMovieCart for user: $userName")
        val response = moviesDataSource.getMovieCart(userName)
       // Log.d("MoviesRepository", "Response from getMovieCart: $response")
        return response
    }

    suspend fun deleteMovie(cartId: Int, userName: String): CartResponse{
        return moviesDataSource.deleteMovie(cartId, userName)
    }
}