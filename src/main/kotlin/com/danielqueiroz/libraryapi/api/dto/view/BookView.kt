package com.danielqueiroz.libraryapi.api.dto.view

data class BookView(
     val id: Long? ,
     val title: String,
     val author: String,
     val isbn: String
)
