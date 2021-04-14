package com.gv.bookshelf.api

import com.gv.bookshelf.models.ApiResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface BookApi {
    @GET("/books/")
    suspend fun getAllBooks() : Response<List<ApiResponseItem>>
}