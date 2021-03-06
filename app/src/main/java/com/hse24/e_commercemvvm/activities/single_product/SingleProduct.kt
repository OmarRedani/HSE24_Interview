package com.hse24.e_commercemvvm.activities.single_product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.POSTER_BASE_URL
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.ProductDBClient
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.ProductInterface
import com.hse24.e_commercemvvm.data.repository.network_data_source.NetworkState
import com.hse24.e_commercemvvm.data.repository.product_details.ProductDetailsRepository
import com.hse24.e_commercemvvm.data.repository.room.Purchase
import com.hse24.e_commercemvvm.data.repository.room.PurchaseViewModel
import com.hse24.e_commercemvvm.data.viewmodels.SingleProductViewModel
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import com.hse24.e_commercemvvm.activities.single_purchase.BasketActivity
import kotlinx.android.synthetic.main.activity_single_product.*
import kotlinx.android.synthetic.main.basket_first_row.view.*

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

        mPurchaseViewModel = ViewModelProvider(this).get(PurchaseViewModel::class.java)



        val apiService : ProductInterface = ProductDBClient.getClient()
        productRepository =
            ProductDetailsRepository(
                apiService
            )

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

            val purchase = Purchase(0,productDetails.imageUris[0],productDetails.nameShort,productDetails.productPrice.price,productDetails.productPrice.currency,productDetails.productPrice.referencePrice,productDetails.brandNameLong,productDetails.productPrice.priceLabel)
            mPurchaseViewModel.addPurchase(purchase)
            Snackbar.make(quantity, R.string.added_to_basket, Snackbar.LENGTH_LONG)
                .show()
        }

        single_product_back.setOnClickListener { finish() }
        single_product_purchase.setOnClickListener { newPurchaseActivity() }
        quantity.setOnClickListener {
            Snackbar.make(quantity, R.string.design_purpose, Snackbar.LENGTH_LONG)
                .setAction(R.string.alright) {
                    // Responds to click on the action
                }
                .show()
        }

    }


    private fun newPurchaseActivity() {
        val intent = Intent(this,BasketActivity::class.java)
        startActivity(intent)
    }

    fun bindUI( it: ProductDetails){

        tv_brand_name.text = it.brandNameLong
        tv_product_name.text = it.title
        tv_product_description.text = Html.fromHtml(it.longDescription)
        rating_bar.rating = it.averageStars.toFloat()
        rating_text.text = "(" + it.averageStars.toString() + ")"
        price_label.text = it.productPrice.referencePriceLabel
        label_price.text = it.productPrice.priceLabel
        Log.d("refPrice"," : "+it.productPrice.referencePriceLabel)
        price.text = it.productPrice.price.toString() + " €"


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
                return SingleProductViewModel(
                    productRepository,
                    productId
                ) as T
            }
        })[SingleProductViewModel::class.java]
    }
}