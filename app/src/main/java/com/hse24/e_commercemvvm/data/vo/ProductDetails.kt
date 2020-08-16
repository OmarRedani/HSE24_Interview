package com.hse24.e_commercemvvm.data.vo


import com.google.gson.annotations.SerializedName

data class ProductDetails(
    val imageUris: List<String>,
    val longDescription: String,
    val nameShort: String,
    val picCount: Int,
    val productPrice: ProductPrice,
    val sku: String,
    val status: String,
    val title: String
)