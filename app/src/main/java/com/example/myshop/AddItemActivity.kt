package com.example.myshop

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myshop.databinding.ActivityAddItemBinding
import com.example.myshop.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    private lateinit var storageRef :StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var imageUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initVars()
        registerClickEvents()

    }

    private fun registerClickEvents(){
        binding.btnUploadImage.setOnClickListener {
            resultLauncher.launch("image/*")
            Log.w("openImages", "opening images")
        }

        binding.btnAddItem.setOnClickListener(){
            uploadImage()
        }

        binding.btnBack.setOnClickListener(){
            startActivity(Intent(this@AddItemActivity,AdminMainActivity::class.java))
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imageUri = it
        binding.imageView.setImageURI(it)
        binding.imageView.visibility = View.VISIBLE
    }


    private fun initVars(){
        binding.uploadingTextView.visibility = View.GONE
        binding.uploadingTextView.visibility = View.GONE
        storageRef = FirebaseStorage.getInstance().reference.child("Images")
        firebaseFirestore = FirebaseFirestore.getInstance()


    }

    private fun uploadImage(){
        binding.uploadingTextView.visibility = View.VISIBLE
        val storageRref = storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let{it ->
            storageRref.putFile(it).addOnCompleteListener{task->
                if(task.isSuccessful){
                    storageRref.downloadUrl.addOnSuccessListener { uri->
                        val map = HashMap<String,Any>()
                        map["pic"] = uri.toString()
                        firebaseFirestore.collection("images").add(map).addOnCompleteListener(){fireStoreTask->
                            if(fireStoreTask.isSuccessful){
                                Toast.makeText(this,"Uploaded Successfully",Toast.LENGTH_SHORT)
                            }else{
                                Toast.makeText(this,fireStoreTask.exception?.message,Toast.LENGTH_SHORT)
                            }
                            binding.imageView.visibility = View.GONE
                            binding.uploadingTextView.visibility = View.GONE

                        }
                    }
                }else{
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT)
                }
            }
        }
    }
}