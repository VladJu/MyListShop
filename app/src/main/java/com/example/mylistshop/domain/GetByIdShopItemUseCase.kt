package com.example.mylistshop.domain

class GetByIdShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getByIdShopItem(shopItemId : Int) : ShopItem{
        return shopListRepository.getByIdShopItem(shopItemId)
    }
}