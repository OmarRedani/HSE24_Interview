package com.hse24.e_commercemvvm.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PurchaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPurchase(purchase : Purchase)

    @Delete
    suspend fun deleteUser(purchase: Purchase)

    @Query("SELECT * FROM purchase_table ORDER BY id ASC")
    fun readAllData():LiveData<List<Purchase>>

}