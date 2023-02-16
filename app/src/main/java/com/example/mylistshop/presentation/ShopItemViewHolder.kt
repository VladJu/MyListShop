package com.example.mylistshop.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylistshop.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.findViewById<TextView>(R.id.tv_name_item)
    val tvCount = view.findViewById<TextView>(R.id.tv_count_add)
}