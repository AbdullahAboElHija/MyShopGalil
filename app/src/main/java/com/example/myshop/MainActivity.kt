package com.example.myshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var registerBtn:Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener {
            moveScreen("Register Screenndsad")
        }


    }

    fun moveScreen(screenName :String) {
        val i = Intent(this@MainActivity, RegisterActivity::class.java)
        startActivity(i)
        println("We Moved to $screenName!")
    }
}