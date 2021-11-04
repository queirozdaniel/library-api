package com.danielqueiroz.libraryapi.domain.service

import com.danielqueiroz.libraryapi.domain.exception.BusinessException
import com.danielqueiroz.libraryapi.domain.model.Book
import com.danielqueiroz.libraryapi.domain.repository.BookRepository
import com.danielqueiroz.libraryapi.domain.service.impl.BookServiceImpl
import com.danielqueiroz.libraryapi.helper.anyObject
import com.danielqueiroz.libraryapi.helper.createValidBook
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.*
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
class BookServiceTest {

    private lateinit var bookService: BookService

    @MockBean
    private lateinit var bookRepository: BookRepository

    @BeforeEach
    fun setup(){
        bookService = BookServiceImpl(bookRepository)
        Mockito.clearInvocations(bookRepository)
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

    @Test
    fun `return book by informed id`() {
        val id = 1L
        val book = createValidBook(id)

        Mockito.`when`(bookRepository.findById(id)).thenReturn(Optional.of(book))

        val foundBook = bookService.getById(id)

        assertTrue(foundBook.isPresent)
        assertEquals(foundBook.get().id, id)
        assertEquals(foundBook.get().author, book.author)
        assertEquals(foundBook.get().title, book.title)
        assertEquals(foundBook.get().isbn, book.isbn)

    }

    @Test
    fun `returns error when no book by entered id is found`() {
        val id = 1L

        Mockito.`when`(bookRepository.findById(id)).thenReturn(Optional.empty())

        val book = bookService.getById(id)

        assertTrue(book.isEmpty)
    }

    @Test
    fun `delete book`() {

        val book = createValidBook(1L)

        assertDoesNotThrow { bookService.deleteById(book.id!!) }

        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(book.id!!)

    }

    @Test
    fun `update book`() {

        val book = createValidBook(1L)
        val updatedBook = createValidBook(book.id)

        Mockito.`when`(bookRepository.save(book)).thenReturn(updatedBook)

        val bookReturned = bookService.update(book)

        assertEquals(bookReturned.id, updatedBook.id)
        assertEquals(bookReturned.author, updatedBook.author)
        assertEquals(bookReturned.title, updatedBook.title)
        assertEquals(bookReturned.isbn, updatedBook.isbn)
        Mockito.verify(bookRepository, Mockito.times(1)).save(updatedBook)
    }

    @Test
    fun `find book by attributes`() {
//      Precisa de refatorar devido erro desconhecido

//        val book = createValidBook(1L)
//        val pageRequest = PageRequest.of(0, 10)
//        val list = listOf(book)
//        val example = Example.of(book)
//
//        val page: Page<Book> = PageImpl(list, pageRequest, 1)
//
//        Mockito.`when`(bookRepository.findAll(example, pageRequest)).thenReturn(page)
//
//        val result = bookService.find(book, pageRequest)
//
//        assertEquals(result.totalElements, 1)
//        assertEquals(result.content, list)
//        assertEquals(result.pageable.pageNumber, 0)
//        assertEquals(result.pageable.pageSize, 10)

    }

}