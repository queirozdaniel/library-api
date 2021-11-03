package com.danielqueiroz.libraryapi.domain.service

import com.danielqueiroz.libraryapi.domain.model.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
interface BookService {

    fun save(book: Book): Book
    fun getById(id: Long): Optional<Book>
    fun deleteById(id: Long)
    fun update(book: Book): Book
    fun find(filter: Book, pageRequest: Pageable): Page<Book>
}