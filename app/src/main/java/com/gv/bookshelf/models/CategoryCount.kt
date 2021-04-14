package com.gv.bookshelf.models

import androidx.room.ColumnInfo

data class CategoryCount(
    @ColumnInfo(name="categories")
    var categories:String,
    @ColumnInfo(name="COUNT(*)")
    var num:Int,
    @ColumnInfo(name="categoryState")
    var categoryState : Boolean = false
)