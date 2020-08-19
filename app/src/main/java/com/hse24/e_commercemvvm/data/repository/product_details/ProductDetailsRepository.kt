package com.hse24.e_commercemvvm.data.repository.product_details

import androidx.lifecycle.LiveData
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.ProductInterface
import com.hse24.e_commercemvvm.data.repository.network_data_source.NetworkState
import com.hse24.e_commercemvvm.data.repository.network_data_source.ProductDetailsNetworkDataSource
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import io.reactivex.disposables.CompositeDisposable

class ProductDetailsRepository (private val apiService : ProductInterface) {

    lateinit var productDetailsNetworkDataSource: ProductDetailsNetworkDataSource

    fun fetchSingleProductDetails (compositeDisposable: CompositeDisposable, productId: Long) : LiveData<ProductDetails> {

        productDetailsNetworkDataSource =
            ProductDetailsNetworkDataSource(
                apiService,
                compositeDisposable
            )
        productDetailsNetworkDataSource.fetchProductDetails(productId)

        return productDetailsNetworkDataSource.downloadedProducteResponse

    }

    fun getProductDetailsNetworkState(): LiveData<NetworkState> {
        return productDetailsNetworkDataSource.networkState
    }



}