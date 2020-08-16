package com.hse24.e_commercemvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hse24.e_commercemvvm.data.api.CategoryDBInterface
import com.hse24.e_commercemvvm.data.vo.FirstCategory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class CategoryDetailsNetworkDataSource (private val apiService : CategoryDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState                   //with this get, no need to implement get function to get networkSate

    private val _downloadedCategoryDetailsResponse =  MutableLiveData<FirstCategory>()
    val downloadedCategoryeResponse: LiveData<FirstCategory>
        get() = _downloadedCategoryDetailsResponse

    fun fetchCategoryDetails() {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getCategoryDetails()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedCategoryDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("CategoryDetailsDS", it.message)
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("CategoryDetailsDS",e.message)
        }


    }
}