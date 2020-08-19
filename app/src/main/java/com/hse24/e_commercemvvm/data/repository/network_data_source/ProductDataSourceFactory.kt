package com.hse24.e_commercemvvm.data.repository.network_data_source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.ProductInterface
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import io.reactivex.disposables.CompositeDisposable

class ProductDataSourceFactory (private val apiService : ProductInterface, private val compositeDisposable: CompositeDisposable, private val category : Long)
    : DataSource.Factory<Int, ProductDetails>() {

    val productsLiveDataSource =  MutableLiveData<ProductDataSource>()

    override fun create(): DataSource<Int, ProductDetails> {
        val productDataSource = ProductDataSource(apiService,compositeDisposable,category)

        productsLiveDataSource.postValue(productDataSource)
        return productDataSource
    }
}