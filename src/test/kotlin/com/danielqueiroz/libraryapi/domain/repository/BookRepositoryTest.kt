package com.danielqueiroz.libraryapi.domain.repository

import com.danielqueiroz.libraryapi.helper.createValidBook
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.junit.jupiter.api.Assertions.*

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Test
    fun `return true if contains books with informed ISBN`(){

        val book = createValidBook()
        val isbn = book.isbn

        entityManager.persist(book)

        val exists = bookRepository.existsByIsbn(isbn)

        assertTrue(exists)
    }

    @Test
    fun `return false if not contains books with informed ISBN`(){

        val exists = bookRepository.existsByIsbn("123")

        assertFalse(exists)
    }

    @Test
    fun `returns book by id`() {

        val book = createValidBook()
        entityManager.persist(book)

        val foundBook = bookRepository.findById(book.id!!)

        assertTrue(foundBook.isPresent)
    }

}