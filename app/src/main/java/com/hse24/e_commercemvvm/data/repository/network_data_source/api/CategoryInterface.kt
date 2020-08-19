package com.hse24.e_commercemvvm.data.repository.network_data_source.api

import com.hse24.e_commercemvvm.data.vo.FirstCategory
import io.reactivex.Single
import retrofit2.http.GET

interface CategoryInterface {

    // https://www.hse24.de/ext-api/app/1/

    @GET("category/tree")
    fun getCategoryDetails(): Single<FirstCategory>
}