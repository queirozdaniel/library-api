package com.danielqueiroz.libraryapi.helper

import com.danielqueiroz.libraryapi.api.dto.form.NewBookForm
import com.danielqueiroz.libraryapi.api.dto.form.NewLoanForm
import com.danielqueiroz.libraryapi.domain.model.Book
import org.mockito.Mockito

/**
 *  Function necessary for use Mockito in Kotlin
 */
fun <T> anyObject(): T {
    return Mockito.anyObject<T>()
}

fun createNewBookForm() = NewBookForm(title = "Meu livro", author = "Autor", isbn = "121212")

fun createValidBook(id: Long? = null) = Book(id = id, isbn = "123", author = "Daniel", title = "Meu livro")

fun createNewLoanForm() = NewLoanForm( isbn = "123", costumer = "Caio")