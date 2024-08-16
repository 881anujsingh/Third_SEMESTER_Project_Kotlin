package com.example.thirdsemproject.ui.activity.admin

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thirdsemproject.R
import com.example.thirdsemproject.databinding.ActivityAddProductBinding
import com.example.thirdsemproject.model.CategoryModel
import com.example.thirdsemproject.model.ProductModel
import com.example.thirdsemproject.repository.category.CategoryRepoImpl
import com.example.thirdsemproject.repository.product.ProductRepoImpl
import com.example.thirdsemproject.utils.ImageUtils
import com.example.thirdsemproject.utils.LoadingUtils
import com.example.thirdsemproject.viewmodel.CategoryViewModel
import com.example.thirdsemproject.viewmodel.ProductViewModel
import com.squareup.picasso.Picasso
import java.util.UUID

class AddProductActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var addProductBinding: ActivityAddProductBinding
    lateinit var categoryViewModel: CategoryViewModel
    lateinit var productViewModel: ProductViewModel
    var category: String=""

    lateinit var imageUtils: ImageUtils
    var imageUri: Uri? = null
    lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        addProductBinding=ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(addProductBinding.root)

        var repo = CategoryRepoImpl()
        categoryViewModel = CategoryViewModel(repo)
        categoryViewModel.getAllCategory()

        var productrepo = ProductRepoImpl()
        productViewModel = ProductViewModel(productrepo)


        imageUtils=ImageUtils(this)
        loadingUtils=LoadingUtils(this)

        addProductBinding.spinnerCategory.onItemSelectedListener = this

        imageUtils.registerActivity { url->
            url.let{
                imageUri =it
                Picasso.get().load(it).into(addProductBinding.imageViewProductAdd)
            }

        }
        addProductBinding.imageViewProductBrowse.setOnClickListener {
            imageUtils.launchGallery(this)
        }




        categoryViewModel.categoryData.observe(this){data->
            var categoryName=data?.map{ category->
                category.categoryName
            } ?: emptyList()

            var arrayAdapter = ArrayAdapter(
                this@AddProductActivity,android.R.layout.simple_spinner_item,categoryName
            )

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            addProductBinding.spinnerCategory.adapter=arrayAdapter

        }

        addProductBinding.btnProductAdd.setOnClickListener {
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
            productViewModel.uploadImages(imageName,uri!!){
                    success,url,message->
                if(success){
                    addProduct(url,imageName)

                }else{
                    Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()
                }

            }

        }
    }

    private fun addProduct(url: String?,imageName:String?) {
        loadingUtils.showDialog()
        var productName : String =addProductBinding.editTextProductName.text.toString()
        var productPrice : Int =addProductBinding.editTextProductPrice.text.toString().toInt()
        var productDesc : String =addProductBinding.editTextProductDesc.text.toString()


        var data= ProductModel("",productName,productPrice,productDesc,category,imageName.toString(),url.toString())
        productViewModel.addProduct(data){
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(parent !=null){
            category = parent.getItemAtPosition(position).toString()
        }
        Log.d("category selected",parent?.getItemAtPosition(position).toString())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}