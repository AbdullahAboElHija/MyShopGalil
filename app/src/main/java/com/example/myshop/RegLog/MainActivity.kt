package com.example.myshop.RegLog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myshop.Admin.AdminMainActivity
import com.example.myshop.Data.User
import com.example.myshop.MySingleton
import com.example.myshop.R
import com.example.myshop.User.UserMainShopActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var registerBtn:Button;
    lateinit var loginBtn : Button;
    lateinit var etEmail : EditText;
    lateinit var etPassword : EditText
    var realTimeFirebase = FirebaseDatabase.getInstance().reference


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

    fun loginUser(email: String, password: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful
                    val safeEmail = email.replace(".", "-")
                    val userRef = realTimeFirebase.child("Users")
                    val specificUserRef = userRef.child(safeEmail)
                    specificUserRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                // Retrieve the user data
                                val user = snapshot.getValue(User::class.java)

                                // Now you can use the 'user' object as needed
                                if (user != null) {
                                    val userName = user.name
                                    val userEmail = user.email
                                    val userPassword = user.password
                                    val userType = user.userType
                                    Log.w("read1", "user type is ${userType}")

                                    MySingleton.setUser1(user!!)
                                    Log.w("CheckUser", "user is ${MySingleton.getUser1().name}")

                                    if (userType == "Admin") {
                                        // User is an admin
                                        val intent : Intent = Intent(this@MainActivity, AdminMainActivity::class.java).putExtra("userData",user)
                                        startActivity(intent)
                                    } else {
                                        // User is a regular user
                                        startActivity(Intent(this@MainActivity, UserMainShopActivity::class.java).putExtra("userData",user))

                                    }
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w("Read data", "Failed in reading data")
                        }
                    })

                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // Login failed
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                }
            }
    }

}