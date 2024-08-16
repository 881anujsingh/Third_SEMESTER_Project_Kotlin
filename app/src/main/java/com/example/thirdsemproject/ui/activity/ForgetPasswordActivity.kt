package com.example.thirdsemproject.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thirdsemproject.R
import com.example.thirdsemproject.databinding.ActivityForgetPasswordBinding
import com.example.thirdsemproject.repository.auth.AuthRepoImpl
import com.example.thirdsemproject.utils.LoadingUtils
import com.example.thirdsemproject.viewmodel.AuthViewModel

class ForgetPasswordActivity : AppCompatActivity() {
    lateinit var forgetPasswordBinding : ActivityForgetPasswordBinding
    lateinit var authViewModel: AuthViewModel
    lateinit var loadingUtils:LoadingUtils


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        forgetPasswordBinding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(forgetPasswordBinding.root)
        var repo = AuthRepoImpl()
        authViewModel = AuthViewModel(repo)
        loadingUtils = LoadingUtils(this)

        forgetPasswordBinding.btnForget.setOnClickListener {
            loadingUtils.showDialog()
            var email :String = forgetPasswordBinding.editEmailForget.text.toString()
            authViewModel.forgetPassword(email){
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}