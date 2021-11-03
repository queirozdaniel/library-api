package com.danielqueiroz.libraryapi.api.controller

import com.danielqueiroz.libraryapi.api.dto.form.NewBookForm
import com.danielqueiroz.libraryapi.api.dto.view.BookView
import com.danielqueiroz.libraryapi.api.mapper.BookViewMapper
import com.danielqueiroz.libraryapi.api.mapper.NewBookFormMapper
import com.danielqueiroz.libraryapi.domain.model.Book
import com.danielqueiroz.libraryapi.domain.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
@RequestMapping("/api/books")
class BookController(
    private val bookService: BookService,
) {

    @Autowired private lateinit var bookViewMapper: BookViewMapper
    @Autowired private lateinit var newBookFormMapper: NewBookFormMapper

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid form: NewBookForm): BookView {

        var entity = newBookFormMapper.map(form)
        entity = bookService.save(entity)

        return bookViewMapper.map(entity)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) : BookView{
        val model = bookService.getById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        return bookViewMapper.map(model)
    }

}