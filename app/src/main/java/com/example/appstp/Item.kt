package com.example.appstp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "items")
data class Item (
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo(name = "name")
    var name : String,
    @ColumnInfo(name = "check")
    var check : Boolean,
    @ColumnInfo(name = "id_travel")
    var id_travel : String
)