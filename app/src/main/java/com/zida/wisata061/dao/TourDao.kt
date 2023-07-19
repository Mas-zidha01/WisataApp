package com.zida.wisata061.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zida.wisata061.model.Tour
import kotlinx.coroutines.flow.Flow


@Dao
interface TourDao {
    @Query("SELECT * FROM `Tour_table` ORDER by name ASC")
    fun getAllTour(): Flow<List<Tour>>

    @Insert
    suspend fun insertTour(tour: Tour)

    @Delete
    suspend fun deleteTour(tour: Tour)

    @Update
    suspend fun updateTour(tour: Tour)
}