package com.hse24.e_commercemvvm.ui.single_product_category

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.api.BASE_URL
import com.hse24.e_commercemvvm.data.api.POSTER_BASE_URL
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import com.hse24.e_commercemvvm.data.vo.ProductResults
import com.hse24.e_commercemvvm.data.vo.SecondCategory
import com.hse24.e_commercemvvm.ui.single_category_details.CategoryFirstChild
import com.hse24.e_commercemvvm.ui.single_product_details.SingleProduct
import kotlinx.android.synthetic.main.category_list_item.view.*
import kotlinx.android.synthetic.main.product_list_item.view.*

val imageSizeSuffix : String = "pics480.jpg"

class ProductCategoryAdapter(val categoryList: ProductResults) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item,parent,false)
        return categoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.productResults.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as categoryViewHolder).bind(categoryList,categoryList.productResults[position])

    }

    class categoryViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(categoryList: ProductResults,productDetails: ProductDetails){
            itemView.cv_product_title.text = productDetails.nameShort
            itemView.cv_product_price.text = "${productDetails.productPrice.price.toString() + "EUR"}"
            val image = POSTER_BASE_URL + productDetails.imageUris[0] + imageSizeSuffix
            Log.d("image",image)
            Glide.with(itemView.context).load(image).into(itemView.cv_iv_product_category)
            itemView.card_view.setOnClickListener{

                val intent = Intent(itemView.context, SingleProduct::class.java)
                intent.putExtra("id",categoryList.productResults[adapterPosition].sku)
                itemView.context.startActivity(intent)
            }
        }


    }
}