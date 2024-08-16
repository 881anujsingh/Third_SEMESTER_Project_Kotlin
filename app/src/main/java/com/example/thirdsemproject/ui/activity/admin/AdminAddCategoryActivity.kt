package com.example.thirdsemproject.ui.activity.admin

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thirdsemproject.R
import com.example.thirdsemproject.adapter.CategoryAdapter
import com.example.thirdsemproject.databinding.ActivityAdminAddCategoryBinding
import com.example.thirdsemproject.model.CategoryModel
import com.example.thirdsemproject.repository.category.CategoryRepoImpl
import com.example.thirdsemproject.utils.ImageUtils
import com.example.thirdsemproject.utils.LoadingUtils
import com.example.thirdsemproject.viewmodel.CategoryViewModel
import com.squareup.picasso.Picasso
import java.util.UUID

class AdminAddCategoryActivity : AppCompatActivity() {
    lateinit var addBinding:ActivityAdminAddCategoryBinding
    lateinit var imageUtils: ImageUtils
    var imageUri: Uri? = null
    lateinit var loadingUtils: LoadingUtils
    lateinit var  categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        addBinding = ActivityAdminAddCategoryBinding.inflate(layoutInflater)
        setContentView(addBinding.root)


        imageUtils = ImageUtils(this)
        loadingUtils = LoadingUtils(this)
        var repo = CategoryRepoImpl()
        categoryViewModel= CategoryViewModel(repo)

        imageUtils.registerActivity { url->
            url.let{
                imageUri =it
                Picasso.get().load(it).into(addBinding.imageView2)
            }

        }
        addBinding.imageViewCategoryBrowse.setOnClickListener {
            imageUtils.launchGallery(this)
        }
        addBinding.btnCategoryAdd.setOnClickListener {
            if(imageUri==null){
                Toast.makeText(applicationContext,"Please Select Image First",Toast.LENGTH_LONG).show()

            }else{
                uploadImage()
            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun uploadImage(){
        loadingUtils.showDialog()
        var imageName = UUID.randomUUID().toString()
        imageUri.let{uri->
            categoryViewModel.uploadImages(imageName,uri!!){
                    success,url,message->
                if(success){
                    addCategory(url,imageName)

                }else{
                    Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()
                }

            }

        }
    }

    private fun addCategory(url: String?,imageName:String?) {
        loadingUtils.showDialog()
        var categoryName : String =addBinding.editTextCategoryName.text.toString()
        var categoryDesc : String =addBinding.editTextCategoryDesc.text.toString()


        var data=CategoryModel("",url.toString(),imageName.toString(),categoryName,categoryDesc)
        categoryViewModel.addCategory(data){
            success,message->
            if(success){
                loadingUtils.dismiss()
                Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
                finish()

            }else{
                loadingUtils.dismiss()
                Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()


            }
        }

    }


}