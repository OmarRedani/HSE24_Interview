package com.hse24.e_commercemvvm.ui.single_product_category

import androidx.lifecycle.LiveData
import com.hse24.e_commercemvvm.data.api.ProductDBInterface
import com.hse24.e_commercemvvm.data.repository.NetworkState
import com.hse24.e_commercemvvm.data.repository.ProductCategoryNetworkDataSource
import com.hse24.e_commercemvvm.data.vo.ProductResults
import io.reactivex.disposables.CompositeDisposable

class ProductCategoryRepository (private val apiService : ProductDBInterface) {

    lateinit var productCategoryNetworkDataSource: ProductCategoryNetworkDataSource

    fun fetchSingleProductCategory (compositeDisposable: CompositeDisposable, productId: Long) : LiveData<ProductResults> {

        productCategoryNetworkDataSource = ProductCategoryNetworkDataSource(apiService,compositeDisposable)
        productCategoryNetworkDataSource.fetchProductCategory(productId)

        return productCategoryNetworkDataSource.downloadedProducteResponse

    }

    fun getProductCategoryNetworkState(): LiveData<NetworkState> {
        return productCategoryNetworkDataSource.networkState
    }



}