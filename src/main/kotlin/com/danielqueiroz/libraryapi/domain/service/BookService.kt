package com.danielqueiroz.libraryapi.domain.service

import com.danielqueiroz.libraryapi.domain.model.Book
import org.springframework.stereotype.Service

@Service
interface BookService {

    fun save(book: Book): Book

}