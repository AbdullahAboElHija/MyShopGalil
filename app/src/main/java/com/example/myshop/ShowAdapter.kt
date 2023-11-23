package com.example.myshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ShowAdapter(
    var items : List<Item>
):RecyclerView.Adapter<ShowAdapter.ToShowViewHolder>(){

    inner class ToShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_toshow,parent,false)
        return ToShowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ToShowViewHolder, position: Int) {
        holder.itemView.apply {
            val tvItemName = findViewById<TextView>(R.id.tvItemName)
            val tvItemDescription = findViewById<TextView>(R.id.tvItemDescription)
            val TvItemPrice = findViewById<TextView>(R.id.TvItemPrice)
            val addToCartButton = findViewById<Button>(R.id.addToCartButton)
            val imageViewItemView = findViewById<ImageView>(R.id.imageViewItemView)
            tvItemName.text = items[position].itemName
            tvItemDescription.text = items[position].itemDescription
            TvItemPrice.text = items[position].itemPrice
            val uriString = items[position].itemPhoto
            if (uriString != null && uriString.isNotEmpty()) {
                // Use Picasso to load the image into the ImageView
                Picasso.get()
                    .load(uriString)
                    .into(imageViewItemView)
            }
        }
    }


}
