package com.zida.wisata061.repository

import com.zida.wisata061.dao.TourDao
import com.zida.wisata061.model.Tour
import kotlinx.coroutines.flow.Flow

class TourRepository(private val tourDao: TourDao) {
    val AllTours: Flow<List<Tour>> = tourDao.getAllTour()

    suspend fun insertTour(tour: Tour) {
        tourDao.insertTour(tour)
    }

    suspend fun deleteTour(tour: Tour) {
        tourDao.deleteTour(tour)
    }

    suspend fun updateTour(tour: Tour) {
        tourDao.updateTour(tour)
    }
}
