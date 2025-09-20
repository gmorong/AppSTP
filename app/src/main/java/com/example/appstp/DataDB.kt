package com.example.appstp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Item::class, Travel::class], version = 1)
abstract class DataDB : RoomDatabase() {
    abstract fun travelDao(): DaoTravel
    abstract fun itemDao(): DaoItem

    companion object {
        @Volatile
        private var INSTANCE: DataDB? = null

        fun getDB(context: Context): DataDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataDB::class.java,
                    "travelcheck.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}