package com.example.myshop

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set

class AdminMainActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        var etWelcomeAdmin = findViewById<TextView>(R.id.WelcomeMsg)
        val receivedUser = intent.getParcelableExtra<User>("userData")
        if (receivedUser != null) {
            // Now you can use the receivedUser object in AdminMainActivity
            // For example, access its properties
            val userName = receivedUser.name
            etWelcomeAdmin.setText("Nice to see you again ${userName}")

        }

    }
}