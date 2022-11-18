package com.example.mycar

import android.app.Application
import com.example.mycar.data.MyCarDatabase

class BaseApplication : Application() {
    val database: MyCarDatabase by lazy { MyCarDatabase.getDatabase(this) }
}