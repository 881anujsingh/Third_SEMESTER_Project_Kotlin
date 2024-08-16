package com.example.thirdsemproject.ui.activity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thirdsemproject.R
import com.example.thirdsemproject.databinding.ActivityEditProfileBinding
import com.example.thirdsemproject.model.UserModel
import com.example.thirdsemproject.repository.auth.AuthRepoImpl
import com.example.thirdsemproject.utils.ImageUtils
import com.example.thirdsemproject.utils.LoadingUtils
import com.example.thirdsemproject.viewmodel.AuthViewModel
import com.squareup.picasso.Picasso
import java.util.UUID

class EditProfileActivity : AppCompatActivity() {
    lateinit var editProfileBinding: ActivityEditProfileBinding
    lateinit var imageUtils: ImageUtils
    var imageUri: Uri? = null
    lateinit var authViewModel: AuthViewModel
    lateinit var loadingUtils:LoadingUtils
    var imageName=""
    var userId=" "
    var oldImageUrl=" "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        editProfileBinding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(editProfileBinding.root)
        imageUtils = ImageUtils(this)
        loadingUtils = LoadingUtils(this)


        var repo = AuthRepoImpl()
        authViewModel =AuthViewModel(repo)

        imageUtils.registerActivity { url->
            url.let{
                imageUri =it
                Picasso.get().load(it).into(editProfileBinding.profileImageUpdate)
            }

        }

        val userData : UserModel? = intent.getParcelableExtra("userData")
        userData.let{users->
            userId = users?.id.toString()
            imageName = if(imageName == null || imageName!!.isEmpty()){
                UUID.randomUUID().toString()
            }else{
                users?.imageName.toString()
            }

            oldImageUrl = users?.imageUrl.toString()
            editProfileBinding.editNameUpdate.setText(users?.name)
            editProfileBinding.editAddressUpdate.setText(users?.address)
            editProfileBinding.editAgeUpdate.setText(users?.age)
            if(users!!.imageUrl == null || users.imageUrl.isEmpty()){
                editProfileBinding.profileImageUpdate.setImageResource(R.drawable.profile)

            }else{
                Picasso.get().load(users.imageUrl).into(editProfileBinding.profileImageUpdate)
            }
        }

        editProfileBinding.btnUpdate.setOnClickListener {
            if(imageUri == null){
                updateProduct(oldImageUrl)

            }else{

                uploadImage()
            }

        }


        editProfileBinding.galleryBrowse.setOnClickListener {
            imageUtils.launchGallery(this)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }




    fun uploadImage(){
        loadingUtils.showDialog()
        imageUri.let{uri->
            authViewModel.uploadImages(imageName,uri!!){
                success,url,message->
                if(success){
                    updateProduct(url)

                }else{
                    Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()
                }

            }

        }
    }

    private fun updateProduct(url: String?) {
        loadingUtils.showDialog()
        var updatedName: String =editProfileBinding.editNameUpdate.text.toString()
        var updatedAddress: String =editProfileBinding.editAddressUpdate.text.toString()
        var updatedAge: String =editProfileBinding.editAgeUpdate.text.toString()
        var updatedMap = mutableMapOf<String,Any?>()

        updatedMap["address"]=updatedAddress
        updatedMap["name"]=updatedName
        updatedMap["age"]=updatedAge
        updatedMap["imageName"]=imageName.toString()
        updatedMap["imageUrl"]=url.toString()

        authViewModel.updateUser(userId,updatedMap){
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