package com.example.myshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set

class AdminMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        var etWelcomeAdmin = findViewById<TextView>(R.id.WelcomeMsg)
        var btnAddItems = findViewById<Button>(R.id.additemsButtomAdmin).setOnClickListener(){
            startActivity(Intent(this@AdminMainActivity,AddItemActivity::class.java))

        }

        val receivedUser = intent.getParcelableExtra<User>("userData")
        if (receivedUser != null) {
            // Now you can use the receivedUser object in AdminMainActivity
            // For example, access its properties
            val userName = receivedUser.name
            etWelcomeAdmin.setText("Nice to see you again ${userName}")

        }



    }
}