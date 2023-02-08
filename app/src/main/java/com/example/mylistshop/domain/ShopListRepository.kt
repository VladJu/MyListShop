package com.example.mylistshop.domain

//должен уметь делать все, что требуется нашим UseCase, но как он будет это делать мы не знаем
interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(item: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getByIdShopItem(shopItemId : Int) : ShopItem

    fun getShopList() : List<ShopItem>

}