package com.example.mylistshop.di

import android.app.Application
import com.example.mylistshop.presentation.MainActivity
import com.example.mylistshop.presentation.ShopItemActivity
import com.example.mylistshop.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}