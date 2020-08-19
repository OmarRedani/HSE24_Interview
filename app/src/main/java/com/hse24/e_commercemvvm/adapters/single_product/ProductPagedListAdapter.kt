package com.hse24.e_commercemvvm.adapters.single_product

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.network_state_item.view.*
import android.widget.Toast
import androidx.paging.PagedList
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.activities.single_product.SingleProduct
import com.hse24.e_commercemvvm.data.repository.network_data_source.NetworkState
import com.hse24.e_commercemvvm.data.repository.network_data_source.api.POSTER_BASE_URL
import com.hse24.e_commercemvvm.data.vo.Paging
import com.hse24.e_commercemvvm.data.vo.ProductDetails
import com.hse24.e_commercemvvm.data.vo.ProductResults
import kotlinx.android.synthetic.main.product_list_item.view.*

val imageSizeSuffix : String = "pics480.jpg"
class ProductPagedListAdapter(public val context: Context) : PagedListAdapter<ProductDetails, RecyclerView.ViewHolder>(ProductDiffCallback()) {

    val PRODUCT_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2


    private var networkState: NetworkState? = null
    lateinit var categoryList: PagedList<ProductDetails>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == PRODUCT_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.product_list_item, parent, false)
            return ProductItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == PRODUCT_VIEW_TYPE) {
            (holder as ProductItemViewHolder).bind(getItem(position),categoryList)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            PRODUCT_VIEW_TYPE
        }
    }




    class ProductDiffCallback : DiffUtil.ItemCallback<ProductDetails>() {
        override fun areItemsTheSame(oldItem: ProductDetails, newItem: ProductDetails): Boolean {
            return oldItem.sku == newItem.sku
        }

        override fun areContentsTheSame(oldItem: ProductDetails, newItem: ProductDetails): Boolean {
            return oldItem == newItem
        }

    }


    class ProductItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(productDetails: ProductDetails?, categoryList : PagedList<ProductDetails>){
            itemView.cv_product_title.text = productDetails?.nameShort
            itemView.cv_product_price.text = "${productDetails?.productPrice?.price.toString() + "EUR"}"
            if (productDetails != null){
                val image = POSTER_BASE_URL + productDetails.imageUris[0] + imageSizeSuffix
                Log.d("image",image)
                Glide.with(itemView.context).load(image).into(itemView.cv_iv_product_category)
            }
            itemView.card_view.setOnClickListener{

                val intent = Intent(itemView.context, SingleProduct::class.java)
                intent.putExtra("id",categoryList[adapterPosition]?.sku)
                intent.putExtra("name",categoryList[adapterPosition]?.nameShort)
                itemView.context.startActivity(intent)
            }
        }

    }

    fun setData (category : PagedList<ProductDetails>){
        categoryList = category
       // notifyDataSetChanged()
    }

    class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE
            }
            else  {
                itemView.progress_bar_item.visibility = View.GONE
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = networkState.msg
            }
            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = networkState.msg
            }
            else {
                itemView.error_msg_item.visibility = View.GONE
            }
        }
    }


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }




}