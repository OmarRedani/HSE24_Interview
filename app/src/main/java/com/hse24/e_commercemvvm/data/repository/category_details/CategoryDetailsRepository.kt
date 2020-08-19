package com.hse24.e_commercemvvm.data.repository.category_details

import androidx.lifecycle.LiveData
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.CategoryInterface
import com.hse24.e_commercemvvm.data.repository.network_data_source.NetworkState
import com.hse24.e_commercemvvm.data.repository.network_data_source.CategoryDetailsNetworkDataSource
import com.hse24.e_commercemvvm.data.vo.FirstCategory
import io.reactivex.disposables.CompositeDisposable

class CategoryDetailsRepository (private val apiService : CategoryInterface) {

    lateinit var categoryDetailsNetworkDataSource: CategoryDetailsNetworkDataSource

    fun fetchSingleCategoryDetails (compositeDisposable: CompositeDisposable) : LiveData<FirstCategory> {

        categoryDetailsNetworkDataSource =
            CategoryDetailsNetworkDataSource(
                apiService,
                compositeDisposable
            )
        categoryDetailsNetworkDataSource.fetchCategoryDetails()

        return categoryDetailsNetworkDataSource.downloadedCategoryeResponse

    }

    fun getCategoryDetailsNetworkState(): LiveData<NetworkState> {
        return categoryDetailsNetworkDataSource.networkState
    }



}