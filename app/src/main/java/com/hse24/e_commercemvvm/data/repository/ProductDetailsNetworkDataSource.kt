package com.hse24.e_commercemvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hse24.e_commercemvvm.data.api.ProductDBInterface
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProductDetailsNetworkDataSource (private val apiService : ProductDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState                   //with this get, no need to implement get function to get networkSate

    private val _downloadedProductDetailsResponse =  MutableLiveData<ProductDetails>()
    val downloadedProducteResponse: LiveData<ProductDetails>
        get() = _downloadedProductDetailsResponse

    fun fetchProductDetails(productId: Long) {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getProductDetails(productId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedProductDetailsResponse.postValue(it)
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