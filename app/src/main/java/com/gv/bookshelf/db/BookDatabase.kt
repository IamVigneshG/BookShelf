package com.gv.bookshelf.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gv.bookshelf.models.BookResponseItem

@Database(
    entities = [BookResponseItem::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class BookDatabase : RoomDatabase() {

    abstract fun getBookDao() : BookDao

}