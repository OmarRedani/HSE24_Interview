package com.hse24.e_commercemvvm.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hse24.e_commercemvvm.data.repository.category_details.CategoryDetailsRepository
import com.hse24.e_commercemvvm.data.repository.network_data_source.NetworkState
import com.hse24.e_commercemvvm.data.vo.FirstCategory
import io.reactivex.disposables.CompositeDisposable


class CategoryViewModel (private val categoryRepository : CategoryDetailsRepository)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  productDetails : LiveData<FirstCategory> by lazy {
        categoryRepository.fetchSingleCategoryDetails(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        categoryRepository.getCategoryDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}