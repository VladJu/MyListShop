package com.example.mylistshop.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mylistshop.domain.ShopItem
import com.example.mylistshop.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD= MutableLiveData<List<ShopItem>>()

    private val shopList = sortedSetOf<ShopItem>(
        { p0, p1 -> p0.id.compareTo(p1.id) }
    )


    private var autoIncrementId = 0


    init {
        for (i in 0 until 100){
            val item=ShopItem("Name $i",i, Random.nextBoolean())
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoIncrementId ++

        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(item: ShopItem) {
        shopList.remove(item)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getByIdShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getByIdShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId } ?: throw RuntimeException(
            "Element with id $shopItemId not found"
        )
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }
    private fun updateList(){
        shopListLD.value= shopList.toList()
    }


}