package com.gv.bookshelf.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "books"
)
data class BookResponseItem(

    @SerializedName("authors")
    var authors: List<String>,
    @SerializedName("categories")
    var categories: String,
    @SerializedName("isbn")
    var isbn: String?,
    @SerializedName("longDescription")
    var longDescription: String?,
    @SerializedName("pageCount")
    var pageCount: Int?,
    @SerializedName("publishedDate")
    var publishedDate: PublishedDate?,
    @SerializedName("shortDescription")
    var shortDescription: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("categoryState")
    var categoryState: Boolean = false,
    @SerializedName("isFavourite")
    var favourite: Boolean = false
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int?=null
}