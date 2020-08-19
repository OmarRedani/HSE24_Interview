package com.hse24.e_commercemvvm.adapters.single_purchase

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.POSTER_BASE_URL
import com.hse24.e_commercemvvm.data.repository.room.Purchase
import com.hse24.e_commercemvvm.data.repository.room.PurchaseViewModel
import kotlinx.android.synthetic.main.basket_row.view.*

class BasketAdapter(val mPurchaseViewModel: PurchaseViewModel) : RecyclerView.Adapter<BasketAdapter.MyViewHolder>() {

    private var purchaseList = emptyList<Purchase>()
    val imageSizeSuffix : String = "pics480.jpg"

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.basket_row, parent, false)
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
        holder.itemView.tv_price_basket.text = currentItem.price.toString() + "EUR"
        holder.itemView.iv_remove_basket.setOnClickListener{
            deleteUser(holder.itemView.context, currentItem)
        }
    }

    fun setData(user: List<Purchase>){
        this.purchaseList = user
        notifyDataSetChanged()
    }

    private fun deleteUser(context: Context,currentItem:Purchase) {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes") { _, _ ->
            mPurchaseViewModel.deleteUser(currentItem)
            Toast.makeText(
                context,
                "Successfully removed: ${currentItem.shortName}",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${currentItem.shortName}?")
        builder.setMessage("Are you sure you want to delete ${currentItem.shortName}?")
        builder.create().show()
    }
}