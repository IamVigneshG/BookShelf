package com.gv.bookshelf.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PublishedDate (
    @SerializedName("\$date")
    val `$date`: String
) : Serializable