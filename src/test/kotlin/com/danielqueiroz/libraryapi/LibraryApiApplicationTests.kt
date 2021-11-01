package com.danielqueiroz.libraryapi

import com.danielqueiroz.libraryapi.api.dto.form.NewBookForm
import com.danielqueiroz.libraryapi.api.dto.view.BookView
import com.danielqueiroz.libraryapi.api.mapper.BookViewMapper
import com.danielqueiroz.libraryapi.api.mapper.NewBookFormMapper
import com.danielqueiroz.libraryapi.domain.model.Book
import com.danielqueiroz.libraryapi.domain.service.BookService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
class LibraryApiApplicationTests {

    private final val BOOK_API = "/api/books"

    @MockBean
    private lateinit var newBookFormMapper: NewBookFormMapper

    @MockBean
    private lateinit var bookViewMapper: BookViewMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bookService: BookService

    @Test
    fun `create a new book`() {

        val form = NewBookForm(title = "Meu livro", author = "Autor", isbn = "121212")
        val model = Book(id = 1, title = "Meu livro", author = "Autor", isbn = "121212")
        val dto = BookView(id = 1, title = "Meu livro", author = "Autor", isbn = "121212")

        BDDMockito.given(newBookFormMapper.map(form)).willReturn(model)
        BDDMockito.given(bookService.save(anyObject())).willReturn(model)
        BDDMockito.given(bookViewMapper.map(model)).willReturn(dto)

        val json = ObjectMapper().writeValueAsString(dto)

        val request = post(BOOK_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)

        mockMvc.perform(request)
            .andExpect(status().isCreated)
            .andExpect(jsonPath("id").isNotEmpty)
            .andExpect(jsonPath("title").value(dto.title))
            .andExpect(jsonPath("author").value(dto.author))
            .andExpect(jsonPath("isbn").value(dto.isbn))
    }


    private fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

}
