package com.hse24.e_commercemvvm.ui.single_product_details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.api.POSTER_BASE_URL
import com.hse24.e_commercemvvm.data.api.ProductDBClient
import com.hse24.e_commercemvvm.data.api.ProductDBInterface
import com.hse24.e_commercemvvm.data.repository.NetworkState
import com.hse24.e_commercemvvm.data.room.Purchase
import com.hse24.e_commercemvvm.data.room.PurchaseViewModel
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import com.hse24.e_commercemvvm.ui.single_purchase_details.BasketActivity
import kotlinx.android.synthetic.main.activity_single_product.*

class SingleProduct : AppCompatActivity() {

    private lateinit var viewModel: SingleProductViewModel
    private lateinit var productRepository: ProductDetailsRepository

    val imageSizeSuffix : String = "pics480.jpg"

    private lateinit var mPurchaseViewModel: PurchaseViewModel
    lateinit var productDetails : ProductDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_product)

        val productId: String = intent.getStringExtra("id")
        Log.d("productId",productId)

        mPurchaseViewModel = ViewModelProvider(this).get(PurchaseViewModel::class.java)



        val apiService : ProductDBInterface = ProductDBClient.getClient()
        productRepository = ProductDetailsRepository(apiService)

        viewModel = getViewModel(productId.toLong())

        viewModel.productDetails.observe(this, Observer {
            bindUI(it)
            productDetails = it
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

        purchase.setOnClickListener{

            val purchase = Purchase(0,productDetails.imageUris[0],productDetails.nameShort,productDetails.productPrice.price,productDetails.productPrice.currency)
            mPurchaseViewModel.addPurchase(purchase)
            Toast.makeText(this,R.string.added_to_basket,Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.purchase_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.purchase_btn_menu -> {
                newPurchaseActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun newPurchaseActivity() {
        val intent = Intent(this,BasketActivity::class.java)
        startActivity(intent)
    }

    fun bindUI( it: ProductDetails){

        tv_product_name.text = it.title
        tv_product_description.text = Html.fromHtml(it.longDescription)

        @Suppress()
        tv_product_price.text = it.productPrice.price.toString() + " " + it.productPrice.currency

        // i just want to take only one image but in the real world i will take them all and use viewPager or something similar
        val productPosterURL = POSTER_BASE_URL + it.imageUris[0] + imageSizeSuffix

        Log.d("SingleProduct","uri : $productPosterURL")

        Glide.with(this)
            .load(productPosterURL)
            .into(iv_product_poster)


    }


    private fun getViewModel(productId:Long): SingleProductViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleProductViewModel(productRepository,productId) as T
            }
        })[SingleProductViewModel::class.java]
    }
}