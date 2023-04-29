package com.example.mylistshop.presentation

import android.app.Application
import com.example.mylistshop.di.DaggerApplicationComponent

class AppShopList : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}