package com.example.myshop.User

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myshop.MySingleton
import com.example.myshop.R
import com.example.myshop.databinding.ActivityUserMainShopBinding

class UserMainShopActivity : AppCompatActivity() {

    lateinit var binding :ActivityUserMainShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        welcome()

        replaceFragment(Home())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> replaceFragment(Home())
                R.id.navigation_cart ->  replaceFragment(Cart())
                else->{

                }
            }
        true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()


        fragmentTransaction.replace(R.id.fremaLayoutOfUser,fragment)
        fragmentTransaction.commit()
    }

    private fun welcome() {
        var welcomeText = findViewById<TextView>(R.id.WelcomeMsgUser)
        Log.w("Check"," ${MySingleton.getUser1().name}")


    }
}