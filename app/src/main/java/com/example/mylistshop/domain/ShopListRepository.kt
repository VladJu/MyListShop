package com.example.mylistshop.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(item: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getByIdShopItem(shopItemId : Int) : ShopItem

    fun getShopList() : LiveData<List<ShopItem>>

}