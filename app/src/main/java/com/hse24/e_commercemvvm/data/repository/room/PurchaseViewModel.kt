package com.hse24.e_commercemvvm.data.repository.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PurchaseViewModel(application: Application) : AndroidViewModel(application){

    val readAllData : LiveData<List<Purchase>>
    private val repository : PurchaseRepository

    init {
        val purchaseDao = PurchaseDatabase.getDatabase(application).purchaseDao()
        repository = PurchaseRepository(purchaseDao)
        readAllData = repository.readAllData
    }
    fun addPurchase(purchase: Purchase){
        viewModelScope.launch(Dispatchers.IO){
            repository.addPurchase(purchase)
        }

    }

    fun deleteUser(purchase: Purchase){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(purchase)
        }
    }

}