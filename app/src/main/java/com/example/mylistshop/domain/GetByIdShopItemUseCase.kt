package com.example.mylistshop.domain

class GetByIdShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun getByIdShopItem(shopItemId : Int) : ShopItem{
        return shopListRepository.getByIdShopItem(shopItemId)
    }
}