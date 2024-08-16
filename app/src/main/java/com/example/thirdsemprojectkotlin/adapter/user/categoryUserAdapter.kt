package com.example.thirdsemproject.adapter.user


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassignment.R
import com.example.mobileassignment.adapter.user.CategoryUserAdapter
import com.example.mobileassignment.model.CategoryModel
import com.example.mobileassignment.ui.activity.CategoryActivity
import com.example.mobileassignment.ui.activity.admin.UpdateCategoryActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class CategoryUserAdapter(var context: Context,var data:ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryUserAdapter.CategoryUserViewHolder>() {

    class CategoryUserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var categoryName: TextView = view.findViewById(R.id.categoryTitleUser)
        var progressBar: ProgressBar = view.findViewById(R.id.progressBarEachCategory)
        var imageView: CircleImageView = view.findViewById(R.id.categoryImageUser)
        var layout: ConstraintLayout = view.findViewById(R.id.categoryCircular)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryUserViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sample_category_user, parent, false)
        return CategoryUserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CategoryUserViewHolder, position: Int) {
        holder.categoryName.text = data[position].categoryName

        var image = data[position].categoryImageUrl
        Picasso.get().load(image).into(holder.imageView,object:Callback{
            override fun onSuccess() {
                holder.progressBar.visibility=View.GONE
            }

            override fun onError(e: Exception?) {
                Toast.makeText(context,e?.message.toString(),Toast.LENGTH_LONG).show()
            }
        })

        holder.layout.setOnClickListener {
            var intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category",data[position])
            context.startActivity(intent)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(category: List<CategoryModel>) {
        data.clear()
        data.addAll(category)
        notifyDataSetChanged()

    }

}
