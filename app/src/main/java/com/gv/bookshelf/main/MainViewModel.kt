package com.gv.bookshelf.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gv.bookshelf.db.BookDatabase
import com.gv.bookshelf.models.ApiResponseItem
import com.gv.bookshelf.models.BookResponseItem
import com.gv.bookshelf.models.CategoryCount
import com.gv.bookshelf.utils.DispatcherProvider
import com.gv.bookshelf.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers : DispatcherProvider,
    private val db : BookDatabase
) : ViewModel() {

    val TAG = "MAINVIEWMODEL"


    sealed class ApiEvent {
        class Success(val result: List<ApiResponseItem>) : ApiEvent()
        class Failure(val error: String) : ApiEvent()
        object Loading : ApiEvent()
        object Empty : ApiEvent()
    }

    sealed class dbEvent {
        class Success(val result: List<BookResponseItem>) : dbEvent()
        class Failure(val error: String) : dbEvent()
        object Loading : dbEvent()
        object Empty : dbEvent()
    }


    sealed class InsertEvent {
        class Success(val result: List<String>) : InsertEvent()
        class Failure(val error: String) : InsertEvent()
        object Loading : InsertEvent()
        object Empty : InsertEvent()
    }

    sealed class BookListingEvent {
        class Success(val result: List<BookResponseItem>) : BookListingEvent()
        class Failure(val error: String) : BookListingEvent()
        object Loading : BookListingEvent()
        object Empty : BookListingEvent()
    }

    sealed class CategoryCountEvent {
        class Success(val result: List<CategoryCount>) : CategoryCountEvent()
        class Failure(val error: String) : CategoryCountEvent()
        object Loading : CategoryCountEvent()
        object Empty : CategoryCountEvent()
    }

    sealed class FavouriteEvent {
        class Success(val result: List<BookResponseItem>) : FavouriteEvent()
        class Failure(val error: String) : FavouriteEvent()
        object Loading : FavouriteEvent()
        object Empty : FavouriteEvent()
    }


    private val _apibooks = MutableStateFlow<ApiEvent>(ApiEvent.Empty)
    val apibooks: StateFlow<ApiEvent> = _apibooks

    private val _dbbooks = MutableStateFlow<dbEvent>(dbEvent.Empty)
    val dbbooks: StateFlow<dbEvent> = _dbbooks
    private val _categoryCount = MutableStateFlow<CategoryCountEvent>(CategoryCountEvent.Empty)
    val categoryCount: StateFlow<CategoryCountEvent> = _categoryCount
    private val _booklisting = MutableStateFlow<BookListingEvent>(BookListingEvent.Empty)
    val booklisting: StateFlow<BookListingEvent> = _booklisting


    private val _insert = MutableStateFlow<InsertEvent>(InsertEvent.Empty)
    val insert: StateFlow<InsertEvent> = _insert

    private val _favorite = MutableStateFlow<FavouriteEvent>(FavouriteEvent.Empty)
    val favourite: StateFlow<FavouriteEvent> = _favorite


    fun getBooks() {
        viewModelScope.launch(dispatchers.io) {
            _apibooks.value = ApiEvent.Loading
            _dbbooks.value = dbEvent.Loading
            val checkDbSize = repository.getAllBooksInDb()
            if (checkDbSize.data?.size == 0) {

                when (val bookResponse = repository.getAllBooks()) {
                    is Resource.Error -> {
                        _apibooks.value = ApiEvent.Failure(bookResponse.message!!)
                        Log.d(TAG, "${bookResponse.message}")
                        Log.d(TAG, "Failure")
                    }
                    is Resource.Success -> {
                        val data = bookResponse.data
                        if (data != null) {

                            for (i in data) {
                                Log.d("MAINVIEWMODEL", "i " + i)
                                Log.d("MAINVIEWMODEL", "i" + i.authors)

                                var categoryNameAsString = ""
                                for (j in i.categories) {
                                    categoryNameAsString += j + " "
                                }

                                var bookResponseItem = BookResponseItem(
                                    i.authors,
                                    categoryNameAsString,
                                    i.isbn,
                                    i.longDescription,
                                    i.pageCount,
                                    i.publishedDate,
                                    i.shortDescription,
                                    i.status,
                                    i.thumbnailUrl,
                                    i.title,
                                    i.categoryState,
                                    i.favourite
                                )
                                db.getBookDao().insert(bookResponseItem)

                            }

                        }
                        if (data == null) {
                            _apibooks.value = ApiEvent.Failure("UnExpected Error")
                            Log.d(TAG, "$data")
                        } else {
                            _apibooks.value = ApiEvent.Success(data)
                            Log.d(TAG, "$data")
                        }
                    }
                }
            }


        }
    }

    fun visibleCategoryList() {
        viewModelScope.launch(dispatchers.io) {
            _insert.value = InsertEvent.Loading
            when (val visiblecategory = repository.getVisibleCategory()) {
                is Resource.Error -> {
                    _insert.value = InsertEvent.Failure(visiblecategory.message!!)
                }
                is Resource.Success -> {
                    val data = visiblecategory.data
                    val result: List<String>
                    if (data == null) {
                        _insert.value = InsertEvent.Failure("Unexpected Error ")
                    } else {
                        _insert.value = InsertEvent.Success(data)
                        Log.d(TAG, "$data")
                    }
                }
            }
        }

    }

    fun getCategoryCount() {
        viewModelScope.launch(dispatchers.io) {
            _categoryCount.value = CategoryCountEvent.Loading
            when (val categoryCountData = repository.getDistinctCategory()) {
                is Resource.Error -> {
                    _categoryCount.value = CategoryCountEvent.Failure(categoryCountData.message!!)
                }
                is Resource.Success -> {
                    val data = categoryCountData.data
                    if (data == null) {
                        _categoryCount.value = CategoryCountEvent.Failure("Unexpected Error ")
                    } else {
                        _categoryCount.value = CategoryCountEvent.Success(data)
                        Log.d(TAG, "$data")
                    }
                }
            }
        }

    }

    fun getBookByCategory(categoryName: String) {

        viewModelScope.launch(dispatchers.io) {
            _booklisting.value = BookListingEvent.Loading
            when (val booklistingdata = repository.getBookByCategory(categoryName)) {
                is Resource.Error -> {
                    _booklisting.value = BookListingEvent.Failure(booklistingdata.message!!)
                }
                is Resource.Success -> {
                    val data = booklistingdata.data
                    if (data == null) {
                        _booklisting.value = BookListingEvent.Failure("Unexpected Error ")
                    } else {
                        _booklisting.value = BookListingEvent.Success(data)
                        Log.d(TAG, "$data")
                    }
                }
            }
        }
    }

    fun clearDatabase() {

        viewModelScope.launch(dispatchers.io) {
            val size = db.getBookDao().getAllBooks().size
            if (size != 0) {
                db.clearAllTables()
            }

        }
    }

    fun saveFavouriteBooks(bookResponseItem: BookResponseItem, state: Boolean) {
        viewModelScope.launch(dispatchers.io) {
            repository.saveFavouriteBooks(bookResponseItem, state)
        }
    }


    fun updateCategoryState(name: String, state: Boolean) {
        viewModelScope.launch(dispatchers.io) {
            repository.updateCategoryShowState(name, state)
        }
    }

    fun getFavouriteBooks() {

        viewModelScope.launch(dispatchers.io) {
            _favorite.value = FavouriteEvent.Loading
            when (val favouriteData = repository.getFavouriteBooks()) {
                is Resource.Error -> {
                    _favorite.value = FavouriteEvent.Failure(favouriteData.message!!)
                }
                is Resource.Success -> {
                    val data = favouriteData.data
                    if (data == null) {
                        _favorite.value = FavouriteEvent.Failure("Unexpected Error ")
                    } else {
                        _favorite.value = FavouriteEvent.Success(data)
                        Log.d("getFavouriteBooks", "$data")
                    }
                }
            }
        }
    }

}