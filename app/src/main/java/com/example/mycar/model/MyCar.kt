package com.example.mycar.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_car_database")
data class MyCar(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "brand")
    val brand: String,
    @ColumnInfo(name = "power")
    val power: Int,
    @ColumnInfo(name = "number_doors")
    val numberDoors: Int,
    @ColumnInfo(name = "fuel")
    val fuel: String,
    @ColumnInfo(name = "second_fuel")
    val secondFuel: String?,
    @ColumnInfo(name = "production_year")
    val productionYear: Int,
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray,
    @ColumnInfo(name = "places")
    val places: Int,
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "kilometres")
    val kM: Int
)