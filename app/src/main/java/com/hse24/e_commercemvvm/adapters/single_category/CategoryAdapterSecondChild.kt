package com.hse24.e_commercemvvm.adapters.single_category

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.vo.FourthCategory
import com.hse24.e_commercemvvm.activities.single_product_category.SingleProductCategory
import kotlinx.android.synthetic.main.category_list_item.view.*

class CategoryAdapterSecondChild (val categoryList: List<FourthCategory>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item,parent,false)
        return categoryViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as categoryViewHolder).bind(categoryList,categoryList[position])

    }

    class categoryViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(categoryList: List<FourthCategory>,fourthCategory: FourthCategory){
            itemView.bt_category.text = fourthCategory.displayName
            itemView.bt_category.setOnClickListener{
                /*val intent = Intent(itemView.context, SingleProduct::class.java)
                intent.putExtra("id",432996)
                itemView.context.startActivity(intent)*/
                Log.d("categoryIdKey",categoryList[adapterPosition].categoryId.toString())
                val intent = Intent(itemView.context, SingleProductCategory::class.java)
                intent.putExtra("categoryName",categoryList[adapterPosition].displayName)
                intent.putExtra("categoryId",categoryList[adapterPosition].categoryId)
                itemView.context.startActivity(intent)
            }
        }

        /*button.setOnClickListener{
            val intent = Intent(this, SingleProduct::class.java)
            intent.putExtra("id",432996)
            this.startActivity(intent)
        }*/


    }
}