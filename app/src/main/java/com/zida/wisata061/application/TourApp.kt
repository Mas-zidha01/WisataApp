package com.zida.wisata061.application

import android.app.Application
import com.zida.wisata061.repository.TourRepository

class TourApp:Application() {
    val dataBase by lazy { TourDataBase.getDatabase(this) }
    val repository by lazy { TourRepository(dataBase.tourDao()) }
}