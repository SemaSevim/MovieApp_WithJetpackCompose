package com.example.finalapp.retrofit

class ApiUtils {
    companion object{
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getMoviesDao(): MoviesDao{
            return RetrofitClient.getClient(BASE_URL).create(MoviesDao::class.java)
        }
    }
}