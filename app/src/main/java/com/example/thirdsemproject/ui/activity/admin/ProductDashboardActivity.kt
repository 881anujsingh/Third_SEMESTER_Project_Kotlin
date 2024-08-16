package com.example.thirdsemproject.ui.activity.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thirdsemproject.R
import com.example.thirdsemproject.adapter.CategoryAdapter
import com.example.thirdsemproject.adapter.ProductAdapter
import com.example.thirdsemproject.databinding.ActivityProductDashboardBinding
import com.example.thirdsemproject.repository.category.CategoryRepoImpl
import com.example.thirdsemproject.repository.product.ProductRepoImpl
import com.example.thirdsemproject.viewmodel.CategoryViewModel
import com.example.thirdsemproject.viewmodel.ProductViewModel

class ProductDashboardActivity : AppCompatActivity() {
    lateinit var productDashboardBinding : ActivityProductDashboardBinding
    lateinit var productAdapter : ProductAdapter
    lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        productDashboardBinding =ActivityProductDashboardBinding.inflate(layoutInflater)
        setContentView(productDashboardBinding.root)


        var repo = ProductRepoImpl()
        productViewModel = ProductViewModel(repo)

        productViewModel.getAllProduct()

        productAdapter = ProductAdapter(this@ProductDashboardActivity,
            ArrayList()

        )

        productViewModel.productData.observe(this){product->
            product?.let{
                productAdapter.updateData(it)
            }
        }

        productViewModel.loadingState.observe(this){loading->
            if(loading){
                productDashboardBinding.progressBarDashProduct.visibility= View.VISIBLE

            }else{
                productDashboardBinding.progressBarDashProduct.visibility= View.GONE

            }
        }
        productDashboardBinding.productRecyclerView.apply{
            layoutManager = LinearLayoutManager(this@ProductDashboardActivity)
            adapter = productAdapter
        }

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var id = productAdapter.getProductId(viewHolder.adapterPosition)
                var imageName = productAdapter.getImageName(viewHolder.adapterPosition)

                productViewModel.deleteProduct(id){
                        success,message->
                    if(success){
                        productViewModel.deleteImage(imageName){
                                s,m->
                            if(s){
                                Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }else{
                        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }).attachToRecyclerView(productDashboardBinding.productRecyclerView)



        productDashboardBinding.productFloating.setOnClickListener {
            var intent = Intent(this@ProductDashboardActivity,AddProductActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}