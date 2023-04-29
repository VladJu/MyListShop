package com.example.mylistshop.domain

import javax.inject.Inject

class GetByIdShopItemUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository) {

    suspend fun getByIdShopItem(shopItemId : Int) : ShopItem{
        return shopListRepository.getByIdShopItem(shopItemId)
    }
}