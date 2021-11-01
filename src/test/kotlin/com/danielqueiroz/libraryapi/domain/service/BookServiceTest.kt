package com.danielqueiroz.libraryapi.domain.service

import com.danielqueiroz.libraryapi.domain.model.Book
import com.danielqueiroz.libraryapi.domain.repository.BookRepository
import com.danielqueiroz.libraryapi.domain.service.impl.BookServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
class BookServiceTest {

    private lateinit var bookService: BookService

    @MockBean
    private lateinit var bookRepository: BookRepository

    @BeforeEach
    fun setup(){
        bookService = BookServiceImpl(bookRepository)
    }

    @Test
    fun `save new book`() {

        val book = Book(isbn = "123", author = "Daniel", title = "Meu livro")
        val newBook = Book(id = 1, isbn = book.isbn, author = book.author, title = book.title)

        Mockito.`when`(bookRepository.save(book)).thenReturn(newBook)

        val savedBook = bookService.save(book)

        assertNotNull(savedBook.id)
        assertEquals(savedBook.author, book.author)
        assertEquals(savedBook.title, book.title)
        assertEquals(savedBook.isbn, book.isbn)
    }

}