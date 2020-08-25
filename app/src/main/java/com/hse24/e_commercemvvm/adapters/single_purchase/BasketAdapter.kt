package com.hse24.e_commercemvvm.adapters.single_purchase

import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.POSTER_BASE_URL
import com.hse24.e_commercemvvm.data.repository.room.Purchase
import com.hse24.e_commercemvvm.data.repository.room.PurchaseViewModel
import kotlinx.android.synthetic.main.activity_single_product.*
import kotlinx.android.synthetic.main.basket_first_row.view.*
import kotlinx.android.synthetic.main.basket_first_row.view.iv_product_basket

import kotlinx.android.synthetic.main.basket_first_row.view.tv_name_basket
import kotlinx.android.synthetic.main.basket_first_row.view.tv_price_basket

class BasketAdapter(val mPurchaseViewModel: PurchaseViewModel) : RecyclerView.Adapter<BasketAdapter.MyViewHolder>() {

    private var purchaseList = emptyList<Purchase>()
    val imageSizeSuffix : String = "pics480.jpg"

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.basket_first_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return purchaseList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = purchaseList[position]
        holder.itemView.tv_name_basket.text = currentItem.shortName
        Log.d("BasketAdapter",currentItem.image)
        val image = POSTER_BASE_URL + currentItem.image + imageSizeSuffix
        Glide.with(holder.itemView.context).load(image).into(holder.itemView.iv_product_basket)
        holder.itemView.tv_price_basket.text = "€ "+currentItem.price.toString()
        if (currentItem.referencePrice.compareTo(0) != 0) {
            holder.itemView.tv_price_basket_ref.text = "€ " + currentItem.referencePrice.toString()
            holder.itemView.tv_price_basket_ref.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        holder.itemView.tv_brand_basket.text = currentItem.brandNameLong
        holder.itemView.tv_price_label.text = currentItem.priceLabel
        holder.itemView.iv_remove_basket_btn.setOnClickListener{
            deleteUser(holder.itemView, currentItem)
        }
        holder.itemView.quantity.setOnClickListener{
            Snackbar.make(holder.itemView.quantity, R.string.design_purpose, Snackbar.LENGTH_LONG)
                .setAction(R.string.alright) {
                    // Responds to click on the action
                }
                .show()
        }
    }

    fun setData(user: List<Purchase>){
        this.purchaseList = user
        notifyDataSetChanged()
    }

    private fun deleteUser(view: View,currentItem:Purchase) {
        val builder = AlertDialog.Builder(view.context)
        builder.setPositiveButton("Yes") { _, _ ->
            mPurchaseViewModel.deleteUser(currentItem)
            Snackbar.make(view, "Successfully removed: ${currentItem.shortName}", Snackbar.LENGTH_LONG)
                .show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${currentItem.shortName}?")
        builder.setMessage("Are you sure you want to delete ${currentItem.shortName}?")
        builder.create().show()
    }
}