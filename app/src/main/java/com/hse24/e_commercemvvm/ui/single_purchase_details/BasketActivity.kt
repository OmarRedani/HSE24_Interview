package com.hse24.e_commercemvvm.ui.single_purchase_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.room.PurchaseViewModel
import kotlinx.android.synthetic.main.activity_basket.*

class BasketActivity : AppCompatActivity() {

    private lateinit var mPurchaseViewModel: PurchaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        supportActionBar?.title = resources.getString(R.string.basket_activity_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mPurchaseViewModel = ViewModelProvider(this).get(PurchaseViewModel::class.java)


        val adapter = BasketAdapter(mPurchaseViewModel)
        val recyclerView = rv_basket
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // UserViewModel
        mPurchaseViewModel.readAllData.observe(this, Observer { user ->
            adapter.setData(user)
        })


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)

    }
}
