package com.danielqueiroz.libraryapi.api.controller

import com.danielqueiroz.libraryapi.api.dto.view.BookView
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/books")
class BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(): BookView {
        return BookView(id = 1, title = "Meu livro", author = "Autor", isbn = "121212")
    }

}