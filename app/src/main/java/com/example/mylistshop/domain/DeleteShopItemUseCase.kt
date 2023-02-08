package com.example.mylistshop.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun deleteSopItem(item: ShopItem){
        shopListRepository.deleteShopItem(item)

    }
}