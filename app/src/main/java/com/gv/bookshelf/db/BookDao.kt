package com.gv.bookshelf.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gv.bookshelf.models.BookResponseItem
import com.gv.bookshelf.models.CategoryCount

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookResponseItem : BookResponseItem)

    @Query("SELECT  DISTINCT categories , COUNT(*) , categoryState  FROM books GROUP BY categories ")
    fun getDistinctCategoryCount():List<CategoryCount>

    @Query("SELECT * FROM books")
    fun getAllBooks() : List<BookResponseItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavouriteBooks(bookResponseItem: BookResponseItem)

    @Query("SELECT * FROM books WHERE categories = :key")
    fun getBookByCategory(key :String) : List<BookResponseItem>

    @Query("SELECT * FROM books WHERE favourite = '1'")
    fun getFavouriteBooks() : List<BookResponseItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun deleteBook(bookResponseItem: BookResponseItem)

    @Query("UPDATE books SET categoryState = :state WHERE categories = :showStatecategoryName")
    fun updateCategoryShowState(showStatecategoryName : String,state:Boolean)

    @Query("SELECT DISTINCT categories FROM books WHERE categoryState = '1'")
    fun getVisibleCategory(): List<String>



}
