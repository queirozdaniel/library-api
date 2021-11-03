package com.danielqueiroz.libraryapi.domain.service

import com.danielqueiroz.libraryapi.domain.model.Book
import org.springframework.stereotype.Service
import java.util.*

@Service
interface BookService {

    fun save(book: Book): Book
    fun getById(id: Long): Optional<Book>

}