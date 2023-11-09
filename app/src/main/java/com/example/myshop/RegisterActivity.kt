package com.example.myshop

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
//define pars
    var database = FirebaseDatabase.getInstance().reference
    lateinit var backBtn :Button
    lateinit var regBtn :Button
    lateinit var etName :EditText
    lateinit var etEmail :EditText
    lateinit var etPass1 :EditText
    lateinit var etPass2 :EditText

//    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
         backBtn = findViewById<Button>(R.id.backBtnRegisterScreen)
         regBtn = findViewById<Button>(R.id.registerBtnScreen)
         etName = findViewById<EditText>(R.id.editTextNameReg)
         etEmail = findViewById<EditText>(R.id.editTextTextEmailAddressReg)
         etPass1 = findViewById<EditText>(R.id.editTextTextPasswordReg)
         etPass2 = findViewById<EditText>(R.id.editTextTextPassword2Reg)

        regBtn.setOnClickListener(){
            saveUserData()
        }








        backBtn.setOnClickListener(){
            val i = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun saveUserData(){
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val password = etPass1.text.toString()
        val password1 = etPass2.text.toString()

        print("$password : $password1")
        if (password == password1) {
            val user = User(name, email, password)
            val userRef = database.child("users").push()
            userRef.setValue(user)
            Toast.makeText(this, "Registerd Successfuly", Toast.LENGTH_SHORT).show()
            Handler().postDelayed(Runnable {
                startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                finish()
            }, 2500)

        } else {
            Toast.makeText(this, "The passwords are not the same", Toast.LENGTH_SHORT).show()

        }

    }
}