package com.danielqueiroz.libraryapi.helper

import com.danielqueiroz.libraryapi.api.dto.form.NewBookForm

fun createNewBookForm() = NewBookForm(title = "Meu livro", author = "Autor", isbn = "121212")