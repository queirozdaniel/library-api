package com.danielqueiroz.libraryapi.domain.service.impl

import com.danielqueiroz.libraryapi.domain.exception.BusinessException
import com.danielqueiroz.libraryapi.domain.model.Book
import com.danielqueiroz.libraryapi.domain.repository.BookRepository
import com.danielqueiroz.libraryapi.domain.service.BookService
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

class BookServiceImpl(
    private val bookRepository: BookRepository
) : BookService {

    override fun save(book: Book): Book {

        if(bookRepository.existsByIsbn(book.isbn)) throw BusinessException("ISBN já cadastrado")

        return bookRepository.save(book)
    }

    override fun getById(id: Long): Optional<Book> {
        return bookRepository.findById(id)
    }

    override fun deleteById(id: Long) {
        bookRepository.deleteById(id)
    }

    override fun update(book: Book): Book {
        return bookRepository.save(book)
    }

    override fun find(filter: Book, pageRequest: Pageable): Page<Book> {
        val example = Example.of(filter, ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withIgnoreNullValues()
            .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING )
        )

        return bookRepository.findAll(example, pageRequest)
    }

    override fun getBookByIsbn(isbn: String): Optional<Book> {
        return bookRepository.findByIsbn(isbn)
    }

}
