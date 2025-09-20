package com.example.appstp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoTravel {
    @Insert
    fun insertTravel (travel: Travel)

    @Query("SELECT * FROM travel")
    fun getAllTravels (): LiveData<List<Travel>>

    @Query("SELECT * FROM travel WHERE id = :id")
    fun getOneTravel(id: Int): LiveData<List<Travel>>

    @Delete
    fun deleteTravel(travel: Travel)
}

