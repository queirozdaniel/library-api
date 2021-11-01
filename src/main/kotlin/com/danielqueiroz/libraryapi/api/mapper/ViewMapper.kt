package com.danielqueiroz.libraryapi.api.mapper

import com.danielqueiroz.libraryapi.api.dto.view.BookView
import com.danielqueiroz.libraryapi.core.mapper.Mapper
import com.danielqueiroz.libraryapi.domain.model.Book
import org.springframework.stereotype.Component

@Component
class BookViewMapper : Mapper<Book, BookView> {

    override fun map(input: Book): BookView {
        return BookView(
            id = input.id,
            title = input.title,
            isbn = input.isbn,
            author = input.author
        )
    }

}