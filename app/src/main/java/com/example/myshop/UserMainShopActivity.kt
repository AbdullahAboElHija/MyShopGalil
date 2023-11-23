package com.example.myshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myshop.databinding.ActivityUserMainShopBinding

class UserMainShopActivity : AppCompatActivity() {

    lateinit var binding :ActivityUserMainShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main_shop)
        binding = ActivityUserMainShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> replaceFragment(Home())
                R.id.navigation_cart->  replaceFragment(Cart())
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
}