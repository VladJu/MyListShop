package com.example.mylistshop.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.mylistshop.R
import com.example.mylistshop.databinding.ItemShopDisabledBinding
import com.example.mylistshop.databinding.ItemShopEnabledBinding
import com.example.mylistshop.domain.ShopItem


class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit?)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    //2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw  RuntimeException("Unknown view type : $viewType")
        }

        //Будет создан объект DataBinding если не актив элемент то будет создан item_shop_disabled
        // активный item_shop_enabled
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout, // id макета который будем использовать
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    //3
    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)

        }
        //4 делаем явное приведение чтобы получить доступ к переменным
        when(binding){
            is ItemShopDisabledBinding ->{
             binding.shopItem= shopItem
            }
            is ItemShopEnabledBinding -> {
                binding.shopItem=shopItem
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            VIEW_TYPE_ENABLED

        } else {
            VIEW_TYPE_DISABLED
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 10
    }
}