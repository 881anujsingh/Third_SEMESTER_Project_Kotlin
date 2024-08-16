package com.example.thirdsemproject.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thirdsemproject.R
import com.example.thirdsemproject.databinding.ActivitySplashScreenBinding
import com.example.thirdsemproject.repository.auth.AuthRepoImpl
import com.example.thirdsemproject.viewmodel.AuthViewModel

class SplashScreen : AppCompatActivity() {
    lateinit var splashScreenBinding: ActivitySplashScreenBinding
    lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding.root)

        var repo = AuthRepoImpl()
        authViewModel=AuthViewModel(repo)

        var currentUser = authViewModel.getCurrentUser()

        Handler(Looper.getMainLooper()).postDelayed({
            if(currentUser==null){
                var intent = Intent(this@SplashScreen,LoginActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                var intent = Intent(this@SplashScreen,NavigationActivity::class.java)
                startActivity(intent)
                finish()

            }



        }, 3000)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}