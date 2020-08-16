package com.hse24.e_commercemvvm.ui.single_product_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hse24.e_commercemvvm.data.repository.NetworkState
import com.hse24.e_commercemvvm.data.vo.ProductResults
import io.reactivex.disposables.CompositeDisposable

class SingleProductCategoryViewModel (private val productRepository : ProductCategoryRepository, productId: Long)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  productCategory : LiveData<ProductResults> by lazy {
        productRepository.fetchSingleProductCategory(compositeDisposable,productId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        productRepository.getProductCategoryNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}