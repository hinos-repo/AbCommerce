package com.hinos.abcommerce.di.disk

import android.app.Application
import com.hinos.abcommerce.system.MyApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule
{
    @Singleton
    @Provides
    fun provideDB(app : MyApp) : AppDatabase {
        return AppDatabase.getInstance(app)
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(db : AppDatabase) : DaoFavorite
    {
        return db.favoriteDao()
    }
}