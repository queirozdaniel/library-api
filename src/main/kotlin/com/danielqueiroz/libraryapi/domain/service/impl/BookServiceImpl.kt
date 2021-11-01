package com.danielqueiroz.libraryapi.domain.service.impl

import com.danielqueiroz.libraryapi.domain.model.Book
import com.danielqueiroz.libraryapi.domain.repository.BookRepository
import com.danielqueiroz.libraryapi.domain.service.BookService

class BookServiceImpl(
    private val bookRepository: BookRepository
) : BookService {

    override fun save(book: Book): Book {
        return bookRepository.save(book)
    }

}
