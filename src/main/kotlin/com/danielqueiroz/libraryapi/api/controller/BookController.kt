package com.danielqueiroz.libraryapi.api.controller

import com.danielqueiroz.libraryapi.api.dto.form.NewBookForm
import com.danielqueiroz.libraryapi.api.dto.view.BookView
import com.danielqueiroz.libraryapi.domain.model.Book
import com.danielqueiroz.libraryapi.domain.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books")
class BookController(
    private val bookService: BookService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody form: NewBookForm): BookView {
        val entity = Book(id = 1, title = "Meu livro", author = "Autor", isbn = "121212")
        bookService.save(entity)

        return BookView(id = 1, title = "Meu livro", author = "Autor", isbn = "121212")
    }

}