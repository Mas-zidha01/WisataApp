package com.zida.wisata061.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zida.wisata061.model.Tour
import com.zida.wisata061.repository.TourRepository
import kotlinx.coroutines.launch

class TourViewModel(private val repository: TourRepository): ViewModel() {
    val AllTours: LiveData<List<Tour>> = repository.AllTours.asLiveData()

    fun insert(tour: Tour) = viewModelScope.launch {
        repository.insertTour(tour)
    }
    fun delete(tour: Tour) = viewModelScope.launch {
        repository.deleteTour(tour)
    }
    fun update(tour: Tour) = viewModelScope.launch {
        repository.updateTour(tour)
    }
}

class TourViewModelFactory(private val repository: TourRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((TourViewModel::class.java))){
            return TourViewModel(repository) as T

        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}