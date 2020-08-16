package com.hse24.e_commercemvvm.data.room

import androidx.lifecycle.LiveData

class PurchaseRepository(private val purchaseDao: PurchaseDao){

    val readAllData : LiveData<List<Purchase>> = purchaseDao.readAllData()

    suspend fun addPurchase(purchase: Purchase){
        purchaseDao.addPurchase(purchase)
    }

    suspend fun deleteUser(purchase: Purchase){
        purchaseDao.deleteUser(purchase)
    }

}