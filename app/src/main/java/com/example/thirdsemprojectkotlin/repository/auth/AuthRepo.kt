package com.example.thirdsemproject.repository.auth

import android.net.Uri
import com.example.thirdsemproject.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface AuthRepo {


    fun login(email:String, password:String,callback:(Boolean,String?)-> Unit)
    fun register(email:String, password:String,callback:(Boolean,String?,String?)-> Unit)
    fun addUserTODatabase(userId:String, userModel: UserModel,callback: (Boolean, String?) -> Unit)
    fun forgetPassword(email: String,callback: (Boolean, String?) -> Unit)
    fun getCurrentUser(): FirebaseUser?
    fun getUserFromFirebase(userId:String,callback: (UserModel?, Boolean,String?) -> Unit)
    fun logout(callback: (Boolean, String?) -> Unit)
    fun uploadImages(imageName: String,imageUri: Uri,callback:(Boolean, String?,String) -> Unit)
    fun updateUser(userID:String,data:MutableMap<String,Any?>,callback: (Boolean, String?) -> Unit)
}