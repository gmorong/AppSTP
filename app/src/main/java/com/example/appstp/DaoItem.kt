package com.example.appstp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoItem {
    @Insert
    fun insertItem (item: Item)

    @Query("SELECT * FROM items")
    fun getAllItems (): LiveData<List<Item>>

    @Query("SELECT * FROM items WHERE id_travel = :id")
    fun getItemsTravel(id: String): LiveData<List<Item>>

    @Delete
    fun deleteItem(item: Item)

    @Update
    fun updateItem(item: Item)
}
