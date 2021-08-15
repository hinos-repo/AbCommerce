package com.hinos.abcommerce.repo.net

import com.hinos.abcommerce.system.MyApp
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetRetrofitModule::class])
@Singleton
interface AppComponent
{
    fun inject(app : MyApp)


}