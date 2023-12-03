package com.example.myshop.User

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.Adapter.ShowAdapter
import com.example.myshop.Data.Item
import com.example.myshop.MySingleton
import com.example.myshop.databinding.FragmentCartBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Cart.newInstance] factory method to
 * create an instance of this fragment.
 */
class Cart : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding : FragmentCartBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var price = 0
        // Access views using binding
        val recyclerView: RecyclerView = binding.cartRecyclerView
        val db = Firebase.firestore
        val items = db.collection("ItemsInUserCart").document(MySingleton.getUser1().email!!).collection("items")
        val itemList: MutableList<Item> = mutableListOf()

        items
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var item = document.toObject(Item::class.java)
                    itemList.add(item)
                    Log.w(
                        "Adding Item",
                        "${item.itemName}+ ${item.itemDescription}+ ${item.itemPrice}+ ${item.itemPhoto}"
                    )
                    price += priceToInt(item.itemPrice.toString())
                    Log.w("priceCheck", "${price}")

                }

                var pricetv = binding.priceCartForUser
                pricetv.text= "${price}"
                Log.w("checkCartPrice " ,  pricetv.text.toString())
                // Set up RecyclerView adapter
                val adapter = ShowAdapter(itemList)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Cart.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Cart().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun priceToInt(str :String):Int{
        var stringWithoutDollarSign = ""
        stringWithoutDollarSign = str
        stringWithoutDollarSign = stringWithoutDollarSign.replace("$", "")
        val intValue = stringWithoutDollarSign.toInt()
        return intValue
    }
}