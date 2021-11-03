package com.danielqueiroz.libraryapi.domain.service.impl

import com.danielqueiroz.libraryapi.domain.exception.BusinessException
import com.danielqueiroz.libraryapi.domain.model.Book
import com.danielqueiroz.libraryapi.domain.repository.BookRepository
import com.danielqueiroz.libraryapi.domain.service.BookService
import java.util.*

class BookServiceImpl(
    private val bookRepository: BookRepository
) : BookService {

    override fun save(book: Book): Book {

        if(bookRepository.existsByIsbn(book.isbn)) throw BusinessException("ISBN já cadastrado")

        return bookRepository.save(book)
    }

    override fun getById(id: Long): Optional<Book> {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun update(book: Book): Book {
        TODO("Not yet implemented")
    }

}
