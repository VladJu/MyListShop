package com.example.mylistshop.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//1
@Entity(tableName ="shop_items")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean,
)

