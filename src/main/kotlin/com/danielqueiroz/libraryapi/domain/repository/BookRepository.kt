package com.danielqueiroz.libraryapi.domain.repository

import com.danielqueiroz.libraryapi.domain.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookRepository : JpaRepository<Book, Long> {

    fun existsByIsbn(isbn: String): Boolean
    fun findByIsbn(isbn: String): Optional<Book>

}