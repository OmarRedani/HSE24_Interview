package com.hse24.e_commercemvvm.data.vo

import com.google.gson.annotations.SerializedName

data class SecondCategory (
    val categoryId: Long,
    val children: List<ThirdCategory>,
    val displayName: String
)