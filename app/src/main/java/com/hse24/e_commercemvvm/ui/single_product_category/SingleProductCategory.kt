package com.hse24.e_commercemvvm.ui.single_product_category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.api.ProductCategoryDBClient
import com.hse24.e_commercemvvm.data.api.ProductDBClient
import com.hse24.e_commercemvvm.data.api.ProductDBInterface
import com.hse24.e_commercemvvm.data.repository.NetworkState
import com.hse24.e_commercemvvm.data.room.Purchase
import com.hse24.e_commercemvvm.data.room.PurchaseViewModel
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import com.hse24.e_commercemvvm.data.vo.ProductResults
import com.hse24.e_commercemvvm.ui.single_product_details.ProductDetailsRepository
import com.hse24.e_commercemvvm.ui.single_product_details.SingleProductViewModel
import kotlinx.android.synthetic.main.activity_single_product.*
import kotlinx.android.synthetic.main.activity_single_product_category.*

class SingleProductCategory : AppCompatActivity() {

    private lateinit var viewModel: SingleProductCategoryViewModel
    private lateinit var productRepository: ProductCategoryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_product_category)

        val categoryId: Long = intent.getLongExtra("categoryId",1)

        Log.d("CategoryIdProblem",categoryId.toString())

        val apiService : ProductDBInterface = ProductCategoryDBClient.getClient()
        productRepository = ProductCategoryRepository(apiService)

        viewModel = getViewModel(categoryId)

        rv_category_category.layoutManager = GridLayoutManager(this,2) as RecyclerView.LayoutManager?
        rv_category_category.setHasFixedSize(true)


        viewModel.productCategory.observe(this, Observer {
            val adapter = ProductCategoryAdapter(it)
            rv_category_category.adapter = adapter


        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_category.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_category.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })


    }

    private fun getViewModel(categoryId:Long): SingleProductCategoryViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleProductCategoryViewModel(productRepository,categoryId) as T
            }
        })[SingleProductCategoryViewModel::class.java]
    }
}
