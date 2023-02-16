package com.example.mylistshop.presentation

import androidx.lifecycle.ViewModel
import com.example.mylistshop.data.ShopListRepositoryImpl
import com.example.mylistshop.domain.DeleteShopItemUseCase
import com.example.mylistshop.domain.EditShopItemUseCase
import com.example.mylistshop.domain.GetShopListUseCase
import com.example.mylistshop.domain.ShopItem

class MainViewModel :ViewModel() {

    private val repository=ShopListRepositoryImpl

    private val  getShopListUseCase = GetShopListUseCase(repository)
    private val  deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val  editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(item: ShopItem){
        deleteShopItemUseCase.deleteShopItem(item)

    }

    fun  changeEnableState(shopItem: ShopItem){
        val  newItem=shopItem.copy(enabled = !shopItem.enabled )
        editShopItemUseCase.editShopItem(newItem)

    }
}