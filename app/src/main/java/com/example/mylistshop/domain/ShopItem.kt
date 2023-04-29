package com.example.mylistshop.domain

data class ShopItem( //основа бизнес логики
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
) {

    //
    companion object {
        const val UNDEFINED_ID = 0
    }
}
