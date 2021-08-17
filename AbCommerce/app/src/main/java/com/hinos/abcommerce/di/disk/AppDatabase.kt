package com.hinos.abcommerce.di.disk

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hinos.abcommerce.repo.data.GoodsItem
import com.hinos.abcommerce.util.Const

@Database(entities = [GoodsItem::class], version = Const.DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun favoriteDao() : DaoFavorite

    companion object
    {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context) : AppDatabase
        {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, Const.DB_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback()
                {
                    override fun onCreate(db: SupportSQLiteDatabase)
                    {
                        super.onCreate(db)
                    }
                    override fun onOpen(db: SupportSQLiteDatabase)
                    {
                        super.onOpen(db)
                    }
                }).build()
        }
    }

}