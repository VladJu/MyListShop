package com.example.mylistshop.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log

class ShopListProvider : ContentProvider() {

    //1)
    //позволят обрабатывать запросы которые приходят в данный провайдер
    //ему будем передавать uri и он будет возвращать тип Int- код(значение) этого запроса
    //код(значение) указвыаем сами для кажого запроса свой
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.mylistshop","shop_items", GET_SHOP_ITEMS_QUERY)
        addURI("com.example.mylistshop","shop_items/#", GET_SHOP_ITEM_BY_ID_QUERY)
    }

    override fun onCreate(): Boolean {
        return true
    }
    //вызывается когда пользователи отправляют запрос к наешму провайдреру и в него прилетят
    //данные, которые ему нужны
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        //2)
        //передаем uri matcher и получаем код ответа
        val code =uriMatcher.match(uri)
        when(code){
            GET_SHOP_ITEMS_QUERY ->{
                TODO("return all shop_items")
            }
        }
        Log.d("ShopListProvider", "query $uri code: $code")
        return null
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
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

