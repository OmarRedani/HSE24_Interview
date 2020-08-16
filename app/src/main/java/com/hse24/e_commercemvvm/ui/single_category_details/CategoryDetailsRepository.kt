package com.hse24.e_commercemvvm.ui.single_category_details

import androidx.lifecycle.LiveData
import com.hse24.e_commercemvvm.data.api.CategoryDBInterface
import com.hse24.e_commercemvvm.data.repository.NetworkState
import com.hse24.e_commercemvvm.data.repository.CategoryDetailsNetworkDataSource
import com.hse24.e_commercemvvm.data.vo.FirstCategory
import io.reactivex.disposables.CompositeDisposable

class CategoryDetailsRepository (private val apiService : CategoryDBInterface) {

    lateinit var categoryDetailsNetworkDataSource: CategoryDetailsNetworkDataSource

    fun fetchSingleCategoryDetails (compositeDisposable: CompositeDisposable) : LiveData<FirstCategory> {

        categoryDetailsNetworkDataSource = CategoryDetailsNetworkDataSource(apiService,compositeDisposable)
        categoryDetailsNetworkDataSource.fetchCategoryDetails()

        return categoryDetailsNetworkDataSource.downloadedCategoryeResponse

    }

    fun getCategoryDetailsNetworkState(): LiveData<NetworkState> {
        return categoryDetailsNetworkDataSource.networkState
    }



}