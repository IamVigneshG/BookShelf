package com.gv.bookshelf.listeners

import com.gv.bookshelf.models.BookResponseItem

interface OnItemClickListener {
    fun onClick(bookResponseItem: BookResponseItem)
}