package com.hse24.e_commercemvvm.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



object CategoryDBClient {

    fun getClient(): CategoryDBInterface {

        val requestInterceptor = Interceptor { chain ->

            // i can add the header here throw the interceptor

            val request = chain.request()
                .newBuilder()
                .addHeader("Accept","application/json")
                .addHeader("appDevice","ANDROID_PHONE")
                .addHeader("locale","de_DE")
                .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryDBInterface::class.java)

    }
}