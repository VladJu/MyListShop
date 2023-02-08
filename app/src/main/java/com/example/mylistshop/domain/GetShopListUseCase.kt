package com.example.mylistshop.domain

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopList() : List<ShopItem>{
        return shopListRepository.getShopList()
    }
}