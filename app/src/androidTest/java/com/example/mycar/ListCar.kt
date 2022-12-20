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

val ListCarDao = listOf<MyCar>(
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
    ),
    MyCar(
        id = 2,
        name = "Panda",
        brand = "Fiat",
        power = 88,
        fuel = "Gasoline",
        secondFuel = "",
        numberDoors = 5,
        productionYear = 2020,
        image = R.id.ic_brand_car.toString().encodeToByteArray(),
        places = 5,
        color = "Red",
        kM = 3000
    ),
    MyCar(
        id = 3,
        name = "Corsa",
        brand = "OPEL",
        power = 55,
        fuel = "Gasoline",
        secondFuel = "",
        numberDoors = 5,
        productionYear = 2020,
        image = R.id.ic_brand_car.toString().encodeToByteArray(),
        places = 5,
        color = "Black",
        kM = 2580
    )
)

val ListBrand = listOf<String>(
    "ACURA",
    "AUDI",
    "FIAT"
)