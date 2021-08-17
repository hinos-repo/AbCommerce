package com.hinos.abcommerce.di

import com.hinos.abcommerce.di.disk.RoomModule
import com.hinos.abcommerce.di.net.NetRetrofitModule
import com.hinos.abcommerce.system.MyApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetRetrofitModule::class, RoomModule::class])
interface AppComponent
{
    fun inject(app : MyApp)
}