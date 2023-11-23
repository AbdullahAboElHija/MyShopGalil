package com.example.myshop

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.databinding.ActivityShowingItemsBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ShowingItemsActivity : AppCompatActivity() {
    private lateinit var binding :ActivityShowingItemsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showing_items)
        binding = ActivityShowingItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerButton()
        val db = Firebase.firestore
        val items = db.collection("images")
        val itemList: MutableList<Item> = mutableListOf()
        db.collection("images")
            .get()
            .addOnSuccessListener { result ->runOnUiThread {
                for (document in result) {
                    var item = document.toObject(Item::class.java)
                    itemList.add(item)
                    Log.w(
                        "Adding Item",
                        "${item.itemName}+ ${item.itemDescription}+ ${item.itemPrice}+ ${item.itemPhoto}"
                    )
                }
            }
                val adapter = ShowAdapter(itemList)
                binding.itemsToShow.adapter = adapter
                binding.itemsToShow.layoutManager= LinearLayoutManager(this)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
//        itemsToShowList[0].itemName?.let { Log.w("ListChecker " , it) }

    }

    private fun registerButton() {
        binding.backToAddItemsScreenButton.setOnClickListener(){
            startActivity(Intent(this@ShowingItemsActivity ,AddItemActivity::class.java))
        }
    }
}