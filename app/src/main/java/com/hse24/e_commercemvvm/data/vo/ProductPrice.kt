package com.hse24.e_commercemvvm.data.vo


import com.google.gson.annotations.SerializedName

data class ProductPrice(
    val country: String,
    val currency: String,
    val price: Double
)