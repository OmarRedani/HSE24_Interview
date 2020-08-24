package com.hse24.e_commercemvvm.activities.single_category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.vo.SecondCategory
import com.hse24.e_commercemvvm.activities.MainActivity.Companion.dataCategory
import com.hse24.e_commercemvvm.adapters.single_category.CategoryAdapterFirstChild
import kotlinx.android.synthetic.main.activity_category_first_child.*

class CategoryFirstChild : AppCompatActivity() {

    companion object{
        lateinit var dataCategoryFirst : SecondCategory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_first_child)

        val position : Int = intent.getIntExtra("position",0)
        first_category_name.text = dataCategory.children[position].displayName

        first_category_back.setOnClickListener { finish() }

        dataCategoryFirst = dataCategory.children[position]
        val categoryAdapter =
            CategoryAdapterFirstChild(
                dataCategory.children[position].children
            )

        rv_category_first.layoutManager = LinearLayoutManager(this)
        rv_category_first.setHasFixedSize(true)
        rv_category_first.adapter = categoryAdapter

    }
}
