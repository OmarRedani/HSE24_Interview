package com.hse24.e_commercemvvm.ui.single_category_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.hse24.e_commercemvvm.R
import com.hse24.e_commercemvvm.data.vo.FirstCategory
import com.hse24.e_commercemvvm.data.vo.SecondCategory
import com.hse24.e_commercemvvm.data.vo.ThirdCategory
import com.hse24.e_commercemvvm.ui.MainActivity.Companion.dataCategory
import kotlinx.android.synthetic.main.activity_category_first_child.*

class CategoryFirstChild : AppCompatActivity() {

    companion object{
        lateinit var dataCategoryFirst : SecondCategory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_first_child)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val position : Int = intent.getIntExtra("position",0)
        supportActionBar?.title = dataCategory.children[position].displayName

        dataCategoryFirst = dataCategory.children[position]
        val categoryAdapter = CategoryAdapterFirstChild(dataCategory.children[position].children)

        rv_category_first.layoutManager = LinearLayoutManager(this)
        rv_category_first.setHasFixedSize(true)
        rv_category_first.adapter = categoryAdapter

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)

    }
}
