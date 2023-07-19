package com.zida.wisata061.application

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zida.wisata061.dao.TourDao
import com.zida.wisata061.model.Tour

@Database(entities = [Tour::class], version = 2, exportSchema = false)
abstract class TourDataBase:RoomDatabase(){
    abstract fun tourDao():TourDao

    companion object{
        private var INSTANCE: TourDataBase? = null

        private val migration1to2: Migration = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Tour_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE Tour_table ADD COLUMN longitude Double DEFAULT 0.0")
            }

        }

        fun getDatabase(context:Context):TourDataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TourDataBase::class.java,
                    "tour_database"
                )
                    .addMigrations(migration1to2)
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }

        }
    }
}