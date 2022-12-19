package com.example.mycar

import com.example.mycar.model.MyCar

val ListCar = listOf<MyCar>(
    MyCar(
        id = 1,
        name = "CL",
        brand = "ACURA",
        power = 88,
        fuel = "Diesel",
        secondFuel = "",
        numberDoors = 5,
        productionYear = 2020,
        image = R.id.ic_brand_car.toString().encodeToByteArray(),
        places = 5,
        color = "Green",
        kM = 2580
    )
)