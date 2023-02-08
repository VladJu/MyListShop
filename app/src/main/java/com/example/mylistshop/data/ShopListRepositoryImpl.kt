package com.example.mylistshop.data

import com.example.mylistshop.domain.ShopItem
import com.example.mylistshop.domain.ShopListRepository

// это надо чтобы мы работали с 1 репозиторием на всех экранах
object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    //будет хранить id элементов

    private var autoIncrementId = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoIncrementId ++

        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(item: ShopItem) {
        shopList.remove(item)
    }

    override fun editShopItem(shopItem: ShopItem) {
        //найдем старый элемент
        val oldElement = getByIdShopItem(shopItem.id)
        //удалием его
        shopList.remove(oldElement)
        //вставим новый
        addShopItem(shopItem)
    }

    override fun getByIdShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId } ?: throw RuntimeException(
            "Element with id $shopItemId not found"
        )// принмает predicate- которая возвращает тру или фолс
    }

    override fun getShopList(): List<ShopItem> {
        //чтобы создать копию листа и возвращать копию shopList
        return shopList.toList()
    }
}