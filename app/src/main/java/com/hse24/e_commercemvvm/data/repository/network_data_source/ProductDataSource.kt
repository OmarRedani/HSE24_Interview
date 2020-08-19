package com.hse24.e_commercemvvm.data.repository.network_data_source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.FIRST_PAGE
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.ProductInterface
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProductDataSource (private val apiService : ProductInterface, private val compositeDisposable: CompositeDisposable,private val category : Long)
    : PageKeyedDataSource<Int, ProductDetails>(){

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ProductDetails>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getProductPaging(page,category)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.productResults, null, page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ProductDetails>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getProductPaging(params.key,category)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.paging.numPages >= params.key) {
                            callback.onResult(it.productResults, params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }
                        else{
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ProductDetails>) {

    }
}