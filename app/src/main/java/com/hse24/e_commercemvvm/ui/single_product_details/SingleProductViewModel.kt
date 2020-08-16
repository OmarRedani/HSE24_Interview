package com.hse24.e_commercemvvm.ui.single_product_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hse24.e_commercemvvm.data.repository.NetworkState
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