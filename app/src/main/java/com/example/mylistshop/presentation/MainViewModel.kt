package com.example.mylistshop.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylistshop.domain.DeleteShopItemUseCase
import com.example.mylistshop.domain.EditShopItemUseCase
import com.example.mylistshop.domain.GetShopListUseCase
import com.example.mylistshop.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(
   private val getShopListUseCase : GetShopListUseCase,
   private val deleteShopItemUseCase : DeleteShopItemUseCase,
   private val editShopItemUseCase : EditShopItemUseCase
) : ViewModel() {


    val shopList = getShopListUseCase.getShopList()


    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val newItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newItem)
        }

    }

}