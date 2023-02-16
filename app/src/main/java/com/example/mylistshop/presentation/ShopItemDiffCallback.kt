package com.example.mylistshop.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.mylistshop.domain.ShopItem

//1)
//будет сравнивать элементы
class ShopItemDiffCallback :DiffUtil.ItemCallback<ShopItem>() {

    // принимают позиции элементов
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
       return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return  oldItem==newItem
    }
}