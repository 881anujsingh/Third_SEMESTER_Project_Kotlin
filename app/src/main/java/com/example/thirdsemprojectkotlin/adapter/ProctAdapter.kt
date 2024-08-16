package com.example.thirdsemproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thirdsemproject.R
import com.example.thirdsemproject.model.CategoryModel
import com.example.thirdsemproject.model.ProductModel
import com.example.thirdsemproject.ui.activity.admin.UpdateCategoryActivity
import com.example.thirdsemproject.ui.activity.admin.UpdateProductActivity
import com.squareup.picasso.Picasso

class ProductAdapter(var context: Context, var data:ArrayList<ProductModel>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var productName : TextView =view.findViewById(R.id.categoryName)
        var productDesc : TextView =view.findViewById(R.id.CategoryDescription)
        var editLabel : TextView =view.findViewById(R.id.CategoryEditLabel)
        var imageView : ImageView = view.findViewById(R.id.imageCategory)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.sample_category_admin,parent,false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text=data[position].productName
        holder.productDesc.text=data[position].description
        var image=data[position].imageUrl
        Picasso.get().load(image).into(holder.imageView)

        holder.editLabel.setOnClickListener {
            var intent= Intent(context, UpdateProductActivity::class.java)
            intent.putExtra("products",data[position])
            context.startActivity(intent)
       }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateData(product: List<ProductModel>){
        data.clear()
        data.addAll(product)
        notifyDataSetChanged()

    }

    fun getProductId(position: Int) : String{
        return data[position].id
    }
    fun getImageName(position: Int) : String{
        return data[position].productName

    }
}