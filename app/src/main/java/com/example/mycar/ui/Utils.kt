package com.example.mycar.ui

import android.widget.ImageView
import com.example.mycar.R
import com.example.mycar.network.logo.MyCarLogo
import com.squareup.picasso.Picasso

/**
 * fun setAndGetUriByBrandParsingListOfLogoAndImageView(logoDataApi: List<MyCarLogo>?, brand: String, logo: ImageView)
 */
fun setAndGetUriByBrandParsingListOfLogoAndImageView(
    logoDataApi: List<MyCarLogo>?,
    brand: String,
    logo: ImageView
) {
    logoDataApi?.forEach {
        if (it.name.equals(brand, ignoreCase = true)) {
            Picasso.get().load(it.logo).into(logo)
            return
        }
    }
    logo.setImageResource(R.drawable.ic_car)
}