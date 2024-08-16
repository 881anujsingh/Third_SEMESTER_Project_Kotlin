package com.example.thirdsemproject.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thirdsemproject.R
import com.example.thirdsemproject.databinding.ActivityLoginBinding
import com.example.thirdsemproject.repository.auth.AuthRepoImpl
import com.example.thirdsemproject.utils.LoadingUtils
import com.example.thirdsemproject.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var loginBinding: ActivityLoginBinding
    lateinit var authViewModel: AuthViewModel
    lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        var repo = AuthRepoImpl()
        authViewModel = AuthViewModel(repo)
        loadingUtils = LoadingUtils(this)

        loginBinding.btnlogin.setOnClickListener {
            loadingUtils.showDialog()
            var email: String =loginBinding.emaillogin.text.toString()
            var password: String =loginBinding.passwordlogin.text.toString()

            authViewModel.login(email,password){
                success,message->
                if(success){
                    Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()
                    var intent = Intent(this@LoginActivity,NavigationActivity::class.java)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()

                }
            }
        }
        loginBinding.createAccount.setOnClickListener {
            var intent = Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(intent)
        }

        loginBinding.forgetPassword.setOnClickListener {
            var intent = Intent(this@LoginActivity,ForgetPasswordActivity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}