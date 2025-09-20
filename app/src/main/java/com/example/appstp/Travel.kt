package com.example.appstp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "travel")
data class Travel (
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo(name = "name")
    var name : String,
    @ColumnInfo(name = "destination")
    var destination : String
)