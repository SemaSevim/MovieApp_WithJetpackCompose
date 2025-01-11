package com.example.finalapp.retrofit

import com.example.finalapp.data.entity.CartMoviesResponse
import com.example.finalapp.data.entity.CartResponse
import com.example.finalapp.data.entity.MovieResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MoviesDao {
    @GET("movies/getAllMovies.php")
    suspend fun getAllMovies() : MovieResponse


    @POST("movies/insertMovie.php")
    @FormUrlEncoded
    suspend fun insertMovie(
        @Field("name") name: String,
        @Field("image") image: String,
        @Field("price") price: Int,
        @Field("category") category: String,
        @Field("rating") rating: Double,
        @Field("year") year: Int,
        @Field("director") director: String,
        @Field("description") description: String,
        @Field("orderAmount") orderAmount: Int,
        @Field("userName") userName: String) : CartResponse


    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun getMovieCart(
        @Field("userName") userName: String
    ): CartMoviesResponse

    @POST("movies/deleteMovie.php")
    @FormUrlEncoded
    suspend fun deleteMovie(
        @Field("cartId") cartId: Int,
        @Field("userName") userName: String
    ): CartResponse

}