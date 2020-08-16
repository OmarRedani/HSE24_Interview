package com.hse24.e_commercemvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hse24.e_commercemvvm.data.api.ProductDBInterface
import com.hse24.e_commercemvvm.data.vo.ProductResults
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProductCategoryNetworkDataSource (private val apiService : ProductDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState                   //with this get, no need to implement get function to get networkSate

    private val _downloadedProductCategoryResponse =  MutableLiveData<ProductResults>()
    val downloadedProducteResponse: LiveData<ProductResults>
        get() = _downloadedProductCategoryResponse

    fun fetchProductCategory(productId: Long) {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getProductCategory(productId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedProductCategoryResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("ProductDetailsDS", it.message)
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("ProductDetailsDS",e.message)
        }


    }
}