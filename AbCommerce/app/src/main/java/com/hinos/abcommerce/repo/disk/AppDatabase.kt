package com.hinos.abcommerce.repo.disk

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hinos.abcommerce.repo.data.GoodsItem
import dagger.Module

@Database(entities = [GoodsItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun goodsDao() : DaoGoods

    companion object
    {
        private const val DB_NAME = "db_goods"

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
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
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