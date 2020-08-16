package com.hse24.e_commercemvvm.data.api

import com.hse24.e_commercemvvm.data.vo.ProductDetails
import com.hse24.e_commercemvvm.data.vo.ProductResults
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductDBInterface {



        // https://www.hse24.de/ext-api/app/1/product/432996001
        // https://www.hse24.de/ext-api/app/1/

        @GET("product/{sku}")
        fun getProductDetails(@Path("sku") id: Long): Single<ProductDetails>

        // https://www.hse24.de/ext-api/app/1/c/**/*-99802763
        // https://www.hse24.de/ext-api/app/1/

        @GET("c/**/*-{category}")
        fun getProductCategory(@Path("category") id: Long): Single<ProductResults>


        // i didn't had enough time to implement the pagination

       // @GET("c/**/*-{category}/productResults%3Fpage%3D{num}")
       // fun getProductCategory(@Path("num") page: Int,@Path("category") id: Int): Single<ProductDetails>
}