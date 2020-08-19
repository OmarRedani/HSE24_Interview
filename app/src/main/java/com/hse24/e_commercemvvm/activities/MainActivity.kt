package com.hse24.e_commercemvvm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.CategoryClient
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.CategoryInterface
import com.hse24.e_commercemvvm.data.repository.network_data_source.NetworkState
import com.hse24.e_commercemvvm.data.vo.FirstCategory
import com.hse24.e_commercemvvm.adapters.single_category.CategoryAdapter
import com.hse24.e_commercemvvm.data.repository.category_details.CategoryDetailsRepository
import com.hse24.e_commercemvvm.data.viewmodels.CategoryViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CategoryViewModel
    private lateinit var categoryRepository: CategoryDetailsRepository

    companion object{
        lateinit var dataCategory : FirstCategory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : CategoryInterface = CategoryClient.getClient()
        categoryRepository =
            CategoryDetailsRepository(
                apiService
            )

        viewModel = getViewModel()

        val linearLayoutManager = LinearLayoutManager(this)
        val categoryAdapter = CategoryAdapter()
        rv_category_main.layoutManager = linearLayoutManager
        rv_category_main.setHasFixedSize(true)
        rv_category_main.adapter = categoryAdapter

        viewModel.productDetails.observe(this, Observer {

            dataCategory = it
            categoryAdapter.setData(it.children)

        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_main.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_main.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    private fun getViewModel(): CategoryViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CategoryViewModel(categoryRepository) as T
            }
        })[CategoryViewModel::class.java]
    }
}
