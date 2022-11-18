package com.example.mycar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mycar.model.MyCar

@Database(entities = [MyCar::class], version = 1, exportSchema = false)
abstract class MyCarDatabase : RoomDatabase() {
    abstract fun myCarDao(): MyCarDao

    companion object {
        @Volatile
        private var INSTANCE: MyCarDatabase? = null

        fun getDatabase(context: Context): MyCarDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyCarDatabase::class.java,
                    "my_car_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}