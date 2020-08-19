package com.hse24.e_commercemvvm.activities.single_product_category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.ProductCategoryClient
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.ProductInterface
import com.hse24.e_commercemvvm.data.repository.network_data_source.NetworkState
import com.hse24.e_commercemvvm.data.viewmodels.SingleProductCategoryViewModel
import com.hse24.e_commercemvvm.adapters.single_product.ProductPagedListAdapter
import com.hse24.e_commercemvvm.data.repository.product_category.ProductPagedListRepository
import kotlinx.android.synthetic.main.activity_single_product_category.*

class SingleProductCategory : AppCompatActivity() {

    private lateinit var viewModel: SingleProductCategoryViewModel
    private lateinit var productRepository: ProductPagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_product_category)



        val apiService : ProductInterface = ProductCategoryClient.getClient()
        productRepository =
            ProductPagedListRepository(
                apiService
            )

        val categoryId: Long = intent.getLongExtra("categoryId",1)
        val categoryName: String = intent.getStringExtra("categoryName")

        // add name and back button to the support action bar
        supportActionBar?.title = categoryName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        viewModel = getViewModel(categoryId)


        val adapter =
            ProductPagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this,2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                if (viewType == adapter.PRODUCT_VIEW_TYPE) return  1    // PRODUCT_VIEW_TYPE will occupy 1 out of 2 span
                else return 2                                              // NETWORK_VIEW_TYPE will occupy all 2 span
            }
        }

        rv_category_category.layoutManager = gridLayoutManager
        rv_category_category.setHasFixedSize(true)
        rv_category_category.adapter = adapter

        viewModel.productPagedList.observe(this, Observer {

            adapter.setData(it)
            adapter.submitList(it)



        })

        viewModel.networkState.observe(this, Observer {

            Log.d("observer","call ${viewModel.listIsEmpty()}")
            progress_bar_category.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_category.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE


            if (!viewModel.listIsEmpty()) {
                adapter.setNetworkState(it)
            }

        })


    }

    private fun getViewModel(categoryId:Long): SingleProductCategoryViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleProductCategoryViewModel(
                    productRepository,
                    categoryId
                ) as T
            }
        })[SingleProductCategoryViewModel::class.java]
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)

    }
}
