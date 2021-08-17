package com.hinos.abcommerce.di

import com.hinos.abcommerce.di.disk.RoomModule
import com.hinos.abcommerce.di.net.NetRetrofitModule
import com.hinos.abcommerce.factory.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetRetrofitModule::class, RoomModule::class])
interface ViewModelComponent
{
    fun inject(viewModelFactory: ViewModelFactory)
}