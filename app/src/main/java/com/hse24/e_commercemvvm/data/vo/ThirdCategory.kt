package com.hse24.e_commercemvvm.data.vo

data class ThirdCategory (
    val categoryId: Long,
    val children: List<FourthCategory>,
    val displayName: String
)
