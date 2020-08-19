package com.hse24.e_commercemvvm.adapters.single_category

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.vo.SecondCategory
import com.hse24.e_commercemvvm.activities.single_category.CategoryFirstChild
import kotlinx.android.synthetic.main.category_list_item.view.*

class CategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var categoryList = emptyList<SecondCategory>()

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

        (holder as categoryViewHolder).bind(categoryList[position])

    }
    fun setData(category : List<SecondCategory>){
        categoryList = category
        notifyDataSetChanged()
    }

    class categoryViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(secondCategory: SecondCategory){
            itemView.bt_category.text = secondCategory.displayName
            itemView.bt_category.setOnClickListener{
                val intent = Intent(itemView.context, CategoryFirstChild::class.java)
                intent.putExtra("position",adapterPosition)
                itemView.context.startActivity(intent)
            }
        }


    }
}