package com.hinos.abcommerce.di.disk

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RoomModule @Inject constructor(private val mApplication: Application)
{
    @Singleton
    @Provides
    fun provideDB() : AppDatabase
    {
        return AppDatabase.getInstance(mApplication)
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(db : AppDatabase) : DaoFavorite
    {
        return db.favoriteDao()
    }
}