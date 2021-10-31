package com.danielqueiroz.libraryapi.api.dto.form

data class NewBookForm(
    val title: String,
    val author: String,
    val isbn: String
)
