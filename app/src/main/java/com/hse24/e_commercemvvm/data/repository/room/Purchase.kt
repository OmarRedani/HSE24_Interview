package com.hse24.e_commercemvvm.data.repository.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase_table")
data class Purchase(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    val shortName: String,
    val price: Double,
    val currency: String,
    val referencePrice: Double,
    val brandNameLong: String,
    val priceLabel: String

)
