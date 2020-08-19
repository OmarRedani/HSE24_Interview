package com.hse24.e_commercemvvm.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.hse24.e_commercemvvm.data.repository.network_data_source.NetworkState
import com.hse24.e_commercemvvm.data.repository.product_category.ProductPagedListRepository
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import io.reactivex.disposables.CompositeDisposable

class SingleProductCategoryViewModel (private val productRepository : ProductPagedListRepository, category: Long)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  productPagedList : LiveData<PagedList<ProductDetails>> by lazy {
        productRepository.fetchLiveProductPagedList(compositeDisposable,category)
    }

    val networkState : LiveData<NetworkState> by lazy {
        productRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return productPagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}