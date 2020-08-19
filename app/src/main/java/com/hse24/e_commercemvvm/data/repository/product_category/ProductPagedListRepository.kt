package com.hse24.e_commercemvvm.data.repository.product_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hse24.e_commercemvvm.data.repository.network_data_source.NetworkState
import com.hse24.e_commercemvvm.data.repository.network_data_source.ProductDataSource
import com.hse24.e_commercemvvm.data.repository.network_data_source.ProductDataSourceFactory
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.POST_PER_PAGE
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.ProductInterface
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import io.reactivex.disposables.CompositeDisposable

class ProductPagedListRepository (private val apiService : ProductInterface) {

    lateinit var productPagedList: LiveData<PagedList<ProductDetails>>
    lateinit var productsDataSourceFactory: ProductDataSourceFactory

    fun fetchLiveProductPagedList (compositeDisposable: CompositeDisposable,category : Long) : LiveData<PagedList<ProductDetails>> {
        productsDataSourceFactory = ProductDataSourceFactory(apiService, compositeDisposable,category)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        productPagedList = LivePagedListBuilder(productsDataSourceFactory, config).build()

        return productPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<ProductDataSource, NetworkState>(
            productsDataSourceFactory.productsLiveDataSource, ProductDataSource::networkState)
    }
}