package com.example.thirdsemproject.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thirdsemproject.R
import com.example.thirdsemproject.adapter.user.ProductUserAdapter
import com.example.thirdsemproject.databinding.ActivityCategoryBinding
import com.example.thirdsemproject.model.CategoryModel
import com.example.thirdsemproject.repository.product.ProductRepoImpl
import com.example.thirdsemproject.viewmodel.ProductViewModel

class CategoryActivity : AppCompatActivity() {
    lateinit var categoryBinding : ActivityCategoryBinding
    lateinit var productViewModel: ProductViewModel
    lateinit var productUserAdapter:ProductUserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        categoryBinding =ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(categoryBinding.root)

        setSupportActionBar(categoryBinding.toolBarCategory)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        var repo=ProductRepoImpl()
        productViewModel=ProductViewModel(repo)




        var category:CategoryModel?=intent.getParcelableExtra("category")
        title = category?.categoryName.toString()
        productViewModel.getProductBYCategory(category?.categoryName.toString())

        productUserAdapter = ProductUserAdapter(
            this@CategoryActivity,
            ArrayList()
        )

        productViewModel.productBYCategory.observe(this){product->

            if(product.isNullOrEmpty()){
                categoryBinding.categoryProductCheck.visibility = View.VISIBLE
                categoryBinding.recyclerCategoryProduct.visibility = View.GONE
            }else{
                categoryBinding.categoryProductCheck.visibility = View.GONE
                categoryBinding.recyclerCategoryProduct.visibility = View.VISIBLE
                productUserAdapter.updateData(product)

            }

        }

        categoryBinding.recyclerCategoryProduct.apply{
            layoutManager = GridLayoutManager(this@CategoryActivity,2)
            adapter = productUserAdapter
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home->{
                finish()
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }
}