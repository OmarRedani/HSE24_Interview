package com.hse24.e_commercemvvm.ui.single_product_details

import androidx.lifecycle.LiveData
import com.hse24.e_commercemvvm.data.api.ProductDBInterface
import com.hse24.e_commercemvvm.data.repository.NetworkState
import com.hse24.e_commercemvvm.data.repository.ProductDetailsNetworkDataSource
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import io.reactivex.disposables.CompositeDisposable

class ProductDetailsRepository (private val apiService : ProductDBInterface) {

    lateinit var productDetailsNetworkDataSource: ProductDetailsNetworkDataSource

    fun fetchSingleProductDetails (compositeDisposable: CompositeDisposable, productId: Long) : LiveData<ProductDetails> {

        productDetailsNetworkDataSource = ProductDetailsNetworkDataSource(apiService,compositeDisposable)
        productDetailsNetworkDataSource.fetchProductDetails(productId)

        return productDetailsNetworkDataSource.downloadedProducteResponse

    }

    fun getProductDetailsNetworkState(): LiveData<NetworkState> {
        return productDetailsNetworkDataSource.networkState
    }



}