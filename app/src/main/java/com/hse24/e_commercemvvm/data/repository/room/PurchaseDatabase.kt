package com.hse24.e_commercemvvm.data.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Purchase::class],version = 1,exportSchema = false)
abstract class PurchaseDatabase : RoomDatabase() {

    abstract fun purchaseDao() : PurchaseDao

    companion object{
        @Volatile
        private var INSTANCE : PurchaseDatabase? = null

        fun getDatabase(context : Context) : PurchaseDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PurchaseDatabase::class.java,
                    "purchase_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}