package com.hse24.e_commercemvvm.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hse24.e_commercemvvm.data.repository.network_data_source.NetworkState
import com.hse24.e_commercemvvm.data.repository.product_details.ProductDetailsRepository
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import io.reactivex.disposables.CompositeDisposable

class SingleProductViewModel (private val productRepository : ProductDetailsRepository, productId: Long)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  productDetails : LiveData<ProductDetails> by lazy {
        productRepository.fetchSingleProductDetails(compositeDisposable,productId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        productRepository.getProductDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}