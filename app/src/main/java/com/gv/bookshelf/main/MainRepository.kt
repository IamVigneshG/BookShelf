package com.gv.bookshelf.main

import com.gv.bookshelf.models.ApiResponseItem
import com.gv.bookshelf.models.BookResponseItem
import com.gv.bookshelf.models.CategoryCount
import com.gv.bookshelf.utils.Resource

interface MainRepository {
    suspend fun getAllBooks() : Resource<List<ApiResponseItem>>

    fun getDistinctCategory():Resource<List<CategoryCount>>

    fun getAllBooksInDb() : Resource<List<BookResponseItem>>

    suspend fun saveFavouriteBooks(bookResponseItem: BookResponseItem, state:Boolean)

    fun getBookByCategory(categoryName : String) : Resource<List<BookResponseItem>>

    fun getFavouriteBooks() : Resource<List<BookResponseItem>>

    suspend fun deleteBook(bookResponseItem: BookResponseItem)

    fun updateCategoryShowState(showStatecategoryName : String,state:Boolean)

    fun getVisibleCategory():Resource<List<String>>
}