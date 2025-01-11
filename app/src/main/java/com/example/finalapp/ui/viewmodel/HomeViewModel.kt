package com.example.finalapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalapp.data.entity.Movies
import com.example.finalapp.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    var movieList = MutableLiveData<List<Movies>>()
    var filteredMovieList = MutableLiveData<List<Movies>>()
    var selectedCategory = MutableLiveData<String?>()
    var categoryList = MutableLiveData<List<String>>()

    init {
        getAllMovies()
        selectedCategory.value = "All"
    }

    fun getAllMovies(){
        CoroutineScope(Dispatchers.Main).launch {
            val movies = moviesRepository.getAllMovies()
            Log.d("HomeViewModel", "Movies: $movies")
            movieList.value = movies
            filteredMovieList.value = movies
            extractCategories(movies)
        }}

    private fun extractCategories(movies: List<Movies>) {
        val categories = movies.map { it.category }.distinct() // Extracts unique categories
        categoryList.value = categories // Updates category list
    }

    // Filters movie list based on the selected category and search query
    fun filterMoviesByCategoryAndSearch(category: String?, query: String) {
        val filtered = movieList.value?.filter {
            (category == null || category == "All" || it.category == category) &&
                    it.name.contains(query, ignoreCase = true)
        }
        filteredMovieList.value = filtered
    }

    // Filters movies based on the selected category and empty query
    fun setSelectedCategory(category: String?) {
        selectedCategory.value = category
        filterMoviesByCategoryAndSearch(category, "")
    }
}


