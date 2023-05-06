package com.example.mylistshop.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.mylistshop.domain.ShopItem
import com.example.mylistshop.presentation.AppShopList
import javax.inject.Inject

class ShopListProvider : ContentProvider() {


    @Inject
    lateinit var shopListDao: ShopListDao

    @Inject
    lateinit var mapper: ShopListMapper


    private val component by lazy {
        (context as AppShopList).component
    }

    //позволят обрабатывать запросы которые приходят в данный провайдер
    //ему будем передавать uri и он будет возвращать тип Int- код(значение) этого запроса
    //код(значение) указываем сами для каждого запроса свой
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.mylistshop", "shop_items", GET_SHOP_ITEMS_QUERY)
        addURI("com.example.mylistshop", "shop_items/#", GET_SHOP_ITEM_BY_ID_QUERY)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    //вызывается когда пользователи отправляют запрос к нашему провайдеру и в него прилетят
    //данные, которые ему нужны
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        //передаем uri в matcher проверяем запрос и получаем код ответа
        return when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                shopListDao.getShopListCursor()
            }
            else -> {
                null
            }
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        //1)Проверка, что приходит корректный uri по которому вставляем данные и вставляем их в БД
        when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                //2) получаем данные из ContentValues
                if (values == null) return null
                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val count = values.getAsInteger("count")
                val enabled = values.getAsBoolean("enabled")
                val shopItem=ShopItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )
                //4)
                shopListDao.addShopItemSync(mapper.mapEntityToDbModel(shopItem))
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object {
        private const val GET_SHOP_ITEMS_QUERY = 100
        private const val GET_SHOP_ITEM_BY_ID_QUERY = 101
    }
}

