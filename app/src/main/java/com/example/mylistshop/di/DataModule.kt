package com.example.mylistshop.di

import android.app.Application
import com.example.mylistshop.data.AppDatabase
import com.example.mylistshop.data.ShopListDao
import com.example.mylistshop.data.ShopListRepositoryImpl
import com.example.mylistshop.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepositoryImpl(impl: ShopListRepositoryImpl): ShopListRepository


    companion object {

        @ApplicationScope
        @Provides
        fun provideShopListDao(
            application: Application
        ): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}