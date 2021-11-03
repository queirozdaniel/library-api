package com.danielqueiroz.libraryapi.api.controller

import com.danielqueiroz.libraryapi.api.dto.form.NewBookForm
import com.danielqueiroz.libraryapi.api.dto.view.BookView
import com.danielqueiroz.libraryapi.api.mapper.BookViewMapper
import com.danielqueiroz.libraryapi.api.mapper.NewBookFormMapper
import com.danielqueiroz.libraryapi.domain.exception.BusinessException
import com.danielqueiroz.libraryapi.domain.model.Book
import com.danielqueiroz.libraryapi.domain.service.BookService
import com.danielqueiroz.libraryapi.helper.createNewBookForm
import com.danielqueiroz.libraryapi.helper.createValidBook
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
class BookControllerTest {

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

        val form = createNewBookForm()
        val model = Book(id = 1, title = "Meu livro", author = "Autor", isbn = "121212")
        val dto = BookView(id = 1, title = "Meu livro", author = "Autor", isbn = "121212")

        BDDMockito.given(newBookFormMapper.map(form)).willReturn(model)
        BDDMockito.given(bookService.save(anyObject())).willReturn(model)
        BDDMockito.given(bookViewMapper.map(model)).willReturn(dto)

        val json = ObjectMapper().writeValueAsString(dto)

        val request = MockMvcRequestBuilders.post(BOOK_API)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json)

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("title").value(dto.title))
            .andExpect(MockMvcResultMatchers.jsonPath("author").value(dto.author))
            .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(dto.isbn))
    }

    @Test
    fun `returns validation error when there are not enough values in book`() {

        val json = ObjectMapper().writeValueAsString(NewBookForm())

        val request = MockMvcRequestBuilders.post(BOOK_API)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json)

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize<Int>(3)))

    }

    @Test
    fun `returns error when try to new book with duplicated ISBN`(){

        val form = createNewBookForm()
        val json = ObjectMapper().writeValueAsString(form)

        BDDMockito.given(bookService.save(anyObject())).willThrow(BusinessException("ISBN já cadastrado"))

        val request = MockMvcRequestBuilders.post(BOOK_API)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json)

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize<Int>(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("errors[0]").value("ISBN já cadastrado"))
    }

    @Test
    fun `returns informations by book`() {

        val id = 1L
        val book = Book(id = id, title = "Meu livro", author = "Autor", isbn = "121212")
        val dto = BookView(id = 1, title = "Meu livro", author = "Autor", isbn = "121212")

        BDDMockito.given(bookService.getById(id)).willReturn(Optional.of(book))
        BDDMockito.given(bookViewMapper.map(book)).willReturn(dto)

        val request = MockMvcRequestBuilders.get("$BOOK_API/$id")
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
            .andExpect(MockMvcResultMatchers.jsonPath("title").value(book.title))
            .andExpect(MockMvcResultMatchers.jsonPath("author").value(book.author))
            .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(book.isbn))
    }

    @Test
    fun `returns error when no book by entered id is found`(){

        BDDMockito.given(bookService.getById(Mockito.anyLong())).willReturn(Optional.empty())

        val request = MockMvcRequestBuilders.get("$BOOK_API/1")
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `delete book`() {

        val id = 1L

        val request = MockMvcRequestBuilders.delete("$BOOK_API/$id")
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isNoContent)

    }

    @Test
    fun `update book`() {
        val id = 1L
        val form = createNewBookForm()
        val model = Book(id = id, title = "Meu livro", author = "Autor", isbn = "121212")
        val dto = BookView(id = id, title = "Meu livro", author = "Autor", isbn = "121212")

        BDDMockito.given(newBookFormMapper.map(form)).willReturn(model)
        BDDMockito.given(bookService.getById(id)).willReturn(Optional.of(Book(id = id, title = "Meu livro 2", author = "Autor 2", isbn = "99999")))
        BDDMockito.given(bookService.update(model)).willReturn(model)
        BDDMockito.given(bookViewMapper.map(model)).willReturn(dto)

        val json = ObjectMapper().writeValueAsString(form)

        val request = MockMvcRequestBuilders.put("$BOOK_API/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json)

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
            .andExpect(MockMvcResultMatchers.jsonPath("title").value(dto.title))
            .andExpect(MockMvcResultMatchers.jsonPath("author").value(dto.author))
            .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(dto.isbn))
    }

    @Test
    fun `returns error when trying to update the nonexistent book`() {
        val id = 1L
        val form = createNewBookForm()

        BDDMockito.given(bookService.getById(id)).willReturn(Optional.empty())

        val json = ObjectMapper().writeValueAsString(form)

        val request = MockMvcRequestBuilders.put("$BOOK_API/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json)

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `returns filtered books`() {

        val id = 1L
        val book = createValidBook(id)

        BDDMockito.given(bookService.find(anyObject<Book>(), anyObject<Pageable>()))
            .willReturn(PageImpl(listOf(book), PageRequest.of(0, 100), 1))

        val queryParam = "?title=${book.title}&author=${book.author}&isbn=${book.isbn}&page=0&size=100"

        val request = MockMvcRequestBuilders.get("$BOOK_API/$queryParam")
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("content", Matchers.hasSize<Int>(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("totalElements").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("pageable.pageSize").value(100))
            .andExpect(MockMvcResultMatchers.jsonPath("pageable.pageNumber").value(0))

    }

    /**
     *  Function necessary for use Mockito in Kotlin
     */
    private fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

}