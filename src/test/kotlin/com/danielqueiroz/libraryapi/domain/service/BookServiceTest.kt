package com.danielqueiroz.libraryapi.domain.service

import com.danielqueiroz.libraryapi.domain.exception.BusinessException
import com.danielqueiroz.libraryapi.domain.model.Book
import com.danielqueiroz.libraryapi.domain.repository.BookRepository
import com.danielqueiroz.libraryapi.domain.service.impl.BookServiceImpl
import com.danielqueiroz.libraryapi.helper.createValidBook
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
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

        val book = createValidBook()
        val newBook = Book(id = 1, isbn = book.isbn, author = book.author, title = book.title)

        Mockito.`when`(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(false)
        Mockito.`when`(bookRepository.save(book)).thenReturn(newBook)

        val savedBook = bookService.save(book)

        assertNotNull(savedBook.id)
        assertEquals(savedBook.author, book.author)
        assertEquals(savedBook.title, book.title)
        assertEquals(savedBook.isbn, book.isbn)
    }

    @Test
    fun `returns error when try to new book with duplicated ISBN`() {

        val book = createValidBook()
        val message = "ISBN já cadastrado"

        Mockito.`when`(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(true)

        val exception = assertThrows( BusinessException::class.java, { bookService.save(book) }, message)

        assertTrue(exception is BusinessException)
        assertEquals(message, exception.message)

        Mockito.verify(bookRepository, Mockito.never()).save(book)

    }

}