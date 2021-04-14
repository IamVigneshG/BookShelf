package com.gv.bookshelf.main

import com.gv.bookshelf.api.BookApi
import com.gv.bookshelf.db.BookDatabase
import com.gv.bookshelf.models.ApiResponseItem
import com.gv.bookshelf.models.BookResponseItem
import com.gv.bookshelf.models.CategoryCount
import com.gv.bookshelf.utils.Resource
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: BookApi,
    private val db: BookDatabase
) : MainRepository {

    override suspend fun getAllBooks(): Resource<List<ApiResponseItem>> {
        return try {
            val response = api.getAllBooks()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error Occurred")
        }
    }

    override  fun getDistinctCategory(): Resource<List<CategoryCount>> {

        val categoryCount = db.getBookDao().getDistinctCategoryCount()

        return Resource.Success(categoryCount)
    }

    override fun getAllBooksInDb(): Resource<List<BookResponseItem>> {

        val list =  db.getBookDao().getAllBooks()

        return Resource.Success(list)
    }

    override suspend fun saveFavouriteBooks(bookResponseItem: BookResponseItem, state:Boolean) {

        bookResponseItem.favourite= state

        db.getBookDao().saveFavouriteBooks(bookResponseItem)
    }

    override fun getBookByCategory(categoryName: String) : Resource<List<BookResponseItem>> {

        val list =  db.getBookDao().getBookByCategory(categoryName)

        return Resource.Success(list)
    }

    override fun getFavouriteBooks(): Resource<List<BookResponseItem>> {

        val favourites = db.getBookDao().getFavouriteBooks()

        return Resource.Success(favourites)
    }

    override suspend fun deleteBook(bookResponseItem: BookResponseItem) {

        bookResponseItem.favourite = false

        db.getBookDao().deleteBook(bookResponseItem)
    }

    override  fun updateCategoryShowState(showStatecategoryName: String, state: Boolean) {

        db.getBookDao().updateCategoryShowState(showStatecategoryName,state)
    }

    override fun getVisibleCategory(): Resource<List<String>>{

        var vList=  db.getBookDao().getVisibleCategory()

        return  Resource.Success(vList)
    }


}