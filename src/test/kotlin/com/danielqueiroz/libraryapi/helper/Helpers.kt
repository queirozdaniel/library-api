package com.danielqueiroz.libraryapi.helper

import com.danielqueiroz.libraryapi.api.dto.form.NewBookForm
import com.danielqueiroz.libraryapi.domain.model.Book

fun createNewBookForm() = NewBookForm(title = "Meu livro", author = "Autor", isbn = "121212")

fun createValidBook(id: Long? = null) = Book(id = id, isbn = "123", author = "Daniel", title = "Meu livro")