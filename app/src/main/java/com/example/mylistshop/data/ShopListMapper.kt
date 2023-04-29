package com.example.mylistshop.data

import com.example.mylistshop.domain.ShopItem
import javax.inject.Inject

//4
class ShopListMapper @Inject constructor(){
    //преобразует сущность домайн слоя в модель БД
 fun mapEntityToDbModel(shopItem: ShopItem) : ShopItemDbModel {
     return ShopItemDbModel(
         id = shopItem.id,
         name = shopItem.name,
         count = shopItem.count,
         enabled = shopItem.enabled
     )
 }
    //преобразует модель бд в сущность domain layer
    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) = ShopItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        count = shopItemDbModel.count,
        enabled = shopItemDbModel.enabled
    )
    //преобразоывавает List<ShopItemDbModel> в List<ShopItem>
    fun mapListDbModelToListEntity(list : List<ShopItemDbModel>) : List<ShopItem>{
        return list.map { mapDbModelToEntity(it) }
    }
}