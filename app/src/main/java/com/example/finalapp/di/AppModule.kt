package com.example.finalapp.di

import com.example.finalapp.data.datasource.MoviesDataSource
import com.example.finalapp.data.repo.MoviesRepository
import com.example.finalapp.retrofit.ApiUtils
import com.example.finalapp.retrofit.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(moviesDataSource: MoviesDataSource) : MoviesRepository{
        return MoviesRepository(moviesDataSource)
    }

    @Provides
    @Singleton
    fun provideMoviesDataSource(moviesDao: MoviesDao) : MoviesDataSource{
        return MoviesDataSource(moviesDao)
    }

    @Provides
    @Singleton
    fun provideMoviesDao() : MoviesDao {
        return ApiUtils.getMoviesDao()
    }
}