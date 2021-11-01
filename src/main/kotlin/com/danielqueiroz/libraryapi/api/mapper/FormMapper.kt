package com.danielqueiroz.libraryapi.api.mapper

import com.danielqueiroz.libraryapi.api.dto.form.NewBookForm
import com.danielqueiroz.libraryapi.core.mapper.Mapper
import com.danielqueiroz.libraryapi.domain.model.Book
import org.springframework.stereotype.Component

@Component
class NewBookFormMapper : Mapper<NewBookForm, Book> {

    override fun map(input: NewBookForm): Book {
        return Book(
            id = 1,
            title = input.title,
            isbn = input.isbn,
            author = input.author
        )
    }

}