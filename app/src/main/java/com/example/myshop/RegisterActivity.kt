package com.example.myshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
//define pars
    var database = FirebaseDatabase.getInstance().reference
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    //
    //private lateinit var binding: ActivitySignUpBinding
    //

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
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPass1.text.toString()
            val password1 = etPass2.text.toString()
            if(checkIfPassword(password1,password)){
                registerUser(email, password,name)
            }

        }
        backBtn.setOnClickListener(){
            val i = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun saveUserData(name: String, email: String, password: String) {
        val user = User(name, email, password)
        val safeEmail = email.replace(".", "-")
        val userRef = database.child("Users").child(safeEmail).setValue(user)

    }

    private fun registerUser(email: String, password: String, name: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    val user = auth.currentUser
                    Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()
                    saveUserData(name,email,password)
                    Handler().postDelayed(Runnable {
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    }, 2500)

                    Log.d("SignUpActivity", "User registered: ${user?.email}")
                } else {
                    // If registration fails, handle the exception
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        // Handle weak password
                        Toast.makeText(this, "weak password (less than 6 chars)", Toast.LENGTH_SHORT).show()
                        Log.e("SignUpActivity", "Weak password")
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        // Handle invalid email
                        Toast.makeText(this, "invalid email", Toast.LENGTH_SHORT).show()
                        Log.e("SignUpActivity", "Invalid email")
                    } catch (e: FirebaseAuthUserCollisionException) {
                        // Handle existing user
                        Toast.makeText(this, "instance couldn't be completed due to a conflict with another existing user.", Toast.LENGTH_SHORT).show()
                        Log.e("SignUpActivity", "User already exists")
                    } catch (e: FirebaseAuthException) {
                        Log.e("SignUpActivity", "Registration failed: ${e.message}")
                    }
                }
            }
    }

    private fun checkIfPassword(password1 : String,password2 : String): Boolean {
        val isMatch = if (password1.equals(password2)) true else false ;
        if(!isMatch) Toast.makeText(this, "passwords does not match!", Toast.LENGTH_SHORT).show()
        return isMatch

    }



}




//        checkIfEmail(email) { userDoesNotExist ->
//            if (userDoesNotExist) {
//                // User doesn't exist, proceed with checking the password and registration
//                if (checkIfPassword(password, password1)) {
//                    val user = User(name, email, password)
//                    val safeEmail = email.replace(".", "-")
//
//                    // Assuming 'database' is a DatabaseReference
//                    val userRef = database.child("Users").child(safeEmail).setValue(user)
//

//                }
//            } else {
//                // User already exists, handle accordingly
//                // You might want to show an error message or take other actions
//                Log.w("Registration","the user exist")
//                Toast.makeText(this, "User with email $email already exists", Toast.LENGTH_SHORT).show()
//            }
//        }