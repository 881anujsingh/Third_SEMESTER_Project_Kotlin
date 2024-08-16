package com.example.thirdsemproject.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.thirdsemproject.R
import com.example.thirdsemproject.databinding.ActivityNavigationBinding
import com.example.thirdsemproject.ui.fragment.CartFragment
import com.example.thirdsemproject.ui.fragment.CategoryFragment
import com.example.thirdsemproject.ui.fragment.HomeFragment
import com.example.thirdsemproject.ui.fragment.ProfileFragment

class NavigationActivity : AppCompatActivity() {
    lateinit var navigationBinding: ActivityNavigationBinding
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager : FragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        navigationBinding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(navigationBinding.root)

        replaceFragment(HomeFragment())
        navigationBinding.buttonNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuHome ->replaceFragment(HomeFragment())
                R.id.menuCart ->replaceFragment(CartFragment())
                R.id.menuProfile ->replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,0)
            insets
        }
    }


}