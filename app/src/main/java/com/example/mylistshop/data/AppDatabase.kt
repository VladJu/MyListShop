package com.example.mylistshop.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//2 (сущность/версия/экспортсхема)
@Database(entities =[ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    //5 возвращает реализацю интерфейса Dao
    abstract fun shopListDao() : ShopListDao


    //3 дабл чек singleton
    companion object{
        private var INSTANCE : AppDatabase? = null
        private val LOCK=Any()
        private const val DB_NAME="shop_item.db"
        //для получения экземпляра бд нужен контекст
        //application чтобы не утекал контекст активити
        fun getInstance(application: Application) : AppDatabase{
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
                val db=Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }

    }

}