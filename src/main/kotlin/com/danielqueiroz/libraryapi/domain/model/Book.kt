package com.danielqueiroz.libraryapi.domain.model

data class Book(
    val id: Long? = null,
    val title: String,
    val author: String,
    val isbn: String
)