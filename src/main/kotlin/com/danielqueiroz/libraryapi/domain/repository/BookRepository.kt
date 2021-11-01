package com.danielqueiroz.libraryapi.domain.repository

import com.danielqueiroz.libraryapi.domain.model.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long> {
}