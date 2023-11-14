package com.example.myshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var registerBtn:Button;
    lateinit var loginBtn : Button;
    lateinit var etEmail : EditText;
    lateinit var etPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginBtn = findViewById(R.id.signInMainScreen)
        etEmail = findViewById(R.id.editTextTextEmailAddressMain)
        etPassword = findViewById(R.id.editTextTextPasswordMain)
        loginBtn.setOnClickListener(){
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            loginUser(email,password)

        }
        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }


    }
 ///
    fun loginUser(email: String, password: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful
                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity, EnterActivity::class.java))
                } else {
                    // Login failed
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                }
            }
    }

}