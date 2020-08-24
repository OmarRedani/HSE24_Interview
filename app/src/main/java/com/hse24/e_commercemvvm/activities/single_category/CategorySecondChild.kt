package com.hse24.e_commercemvvm.activities.single_category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.adapters.single_category.CategoryAdapterSecondChild
import kotlinx.android.synthetic.main.activity_category_second_child.*

class CategorySecondChild : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_second_child)

        val position : Int = intent.getIntExtra("position",0)
        second_category_name.text = CategoryFirstChild.dataCategoryFirst.children[position].displayName
        second_category_back.setOnClickListener {
            finish()
        }

        val categoryAdapter =
            CategoryAdapterSecondChild(
                CategoryFirstChild.dataCategoryFirst.children[position].children
            )

        rv_category_second.layoutManager = LinearLayoutManager(this)
        rv_category_second.setHasFixedSize(true)
        rv_category_second.adapter = categoryAdapter


    }
}
