package com.example.mylistshop.presentation

import androidx.lifecycle.ViewModel
import com.example.mylistshop.data.ShopListRepositoryImpl
import com.example.mylistshop.domain.DeleteShopItemUseCase
import com.example.mylistshop.domain.EditShopItemUseCase
import com.example.mylistshop.domain.GetShopListUseCase
import com.example.mylistshop.domain.ShopItem

class MainViewModel :ViewModel() {

    //Для всех useCase необходимо передать парметр в качестве репозитория с которым они работают
    //И мы сейчас передадим реализацию не правильным образом
    //Не праивльно потому что presentation слой завист от domain слоя, domain слой -это главный слой
    // приложения, presentation слой все знает о domain слое
    // data слой также все знает о domain слое
    // но у viewModel зависит от data слоя т.к передали репозиторий
    //data слой и presentation не должны знать друг о друге ничего
    private val repository=ShopListRepositoryImpl

    private val  getShopListUseCase = GetShopListUseCase(repository)
    private val  deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val  editShopItemUseCase = EditShopItemUseCase(repository)
    val shopList = getShopListUseCase.getShopList()


    fun deleteShopItem(item: ShopItem){
        deleteShopItemUseCase.deleteShopItem(item)

    }

    fun  changeEnableState(shopItem: ShopItem){
        val  newItem=shopItem.copy(enabled = !shopItem.enabled )
        editShopItemUseCase.editShopItem(newItem)

    }
}