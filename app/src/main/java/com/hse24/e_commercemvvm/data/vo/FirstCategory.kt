package com.hse24.e_commercemvvm.data.vo


import com.google.gson.annotations.SerializedName

data class FirstCategory(
    val children: List<SecondCategory>
)