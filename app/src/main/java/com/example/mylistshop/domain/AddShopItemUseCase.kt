package com.example.mylistshop.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    //2
    suspend fun addShopItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}