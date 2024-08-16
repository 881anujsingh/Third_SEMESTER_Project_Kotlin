package com.example.thirdsemproject.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thirdsemproject.model.CategoryModel
import com.example.thirdsemproject.model.ProductModel
import com.example.thirdsemproject.repository.product.ProductRepo

class ProductViewModel(var repo : ProductRepo): ViewModel() {
    fun uploadImages(imageName: String, imageUri: Uri, callback:(Boolean, String?, String) -> Unit){
        repo.uploadImages(imageName, imageUri,callback)

    }
    fun addProduct(productModel: ProductModel, callback: (Boolean, String?) -> Unit){
        repo.addProduct(productModel,callback)

    }
    fun updateProduct(productId:String,data:MutableMap<String,Any?>,callback: (Boolean, String?) -> Unit){
        repo.updateProduct(productId,data,callback)
    }

    fun deleteProduct(productId:String,callback: (Boolean, String?) -> Unit){
        repo.deleteProduct(productId,callback)
    }

    fun deleteImage(imageName: String,callback: (Boolean, String?) -> Unit){
        repo.deleteImage(imageName,callback)

    }

    private var _productData = MutableLiveData<List<ProductModel>?>()
    var productData = MutableLiveData<List<ProductModel>?>()
        get() = _productData


    private var _loadingState = MutableLiveData<Boolean>()
    var loadingState = MutableLiveData<Boolean>()
        get() = _loadingState
    fun getAllProduct(){
        _loadingState.value = true
        repo.getAllProduct{
                productList,success,message->
            if(productList!=null){
                _loadingState.value = false
                _productData.value = productList
            }
        }
    }

    private var _productBYCategory = MutableLiveData<List<ProductModel>?>()
    var productBYCategory = MutableLiveData<List<ProductModel>?>()
        get() = _productBYCategory


    private var _loadingState2 = MutableLiveData<Boolean>()
    var loadingState2 = MutableLiveData<Boolean>()
        get() = _loadingState2
    fun getProductBYCategory(categoryName:String){
        _loadingState2.value = true
        repo.getProductBYCategory(categoryName){
                productList,success,message->
            if(productList!=null){
                _loadingState2.value = false
                _productBYCategory.value = productList
            }
        }
    }







}